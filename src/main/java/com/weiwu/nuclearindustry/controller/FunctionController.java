package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.service.OpSatService;
import com.weiwu.nuclearindustry.service.RaSatService;
import com.weiwu.nuclearindustry.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/function")
public class FunctionController {
    private static final Logger logger = Logger.getLogger(FunctionController.class.getName());
    @Autowired
    private OpSatService opSatService;
    @Autowired
    private RaSatService raSatService;
    @Autowired
    private SystemConfig systemConfig;

    private Set<String> processedFiles = new HashSet<>();  // 新增：用于记录已处理的文件

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initDB() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String[] dataSource = systemConfig.getDATA_SOURCE();
        for (String s : dataSource) {
            File directory = new File(s);
            queryDirectory(executor, directory);
        }
        executor.shutdown();

        // 判断任务是否全部完成
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            boolean allTasksCompleted = executor.isTerminated();
            if (allTasksCompleted) {
                logger.info("所有任务已完成");
            } else {
                logger.info("还有任务在执行");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "all task finished.";
    }

    private void queryDirectory(ExecutorService executor, File directory) {
        File unTarGzDir = new File(systemConfig.getUNTARGZ_PATH());
        String[] filenames = unTarGzDir.list();
        assert filenames != null;
        HashSet<String> filenameSet = new HashSet<>(Arrays.asList(filenames));

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile() && file.exists()) {
                    String fileName = file.getName();
                    int lastDotIndex = fileName.lastIndexOf('.');
                    String baseName = lastDotIndex!= -1? fileName.substring(0, lastDotIndex) : fileName;
//                    if (fileName.endsWith(".tar.gz") || fileName.endsWith(".tar")) {
                    if(file.isFile() && file.exists() && (fileName.endsWith(".tar.gz") || fileName.endsWith(".zip"))&&!fileName.contains(".tar.gz.")&&!fileName.contains(".zip.")&&!filenameSet.contains(baseName)){
                        String prefixName = FileUtil.filePrefixName(fileName);
                        System.out.println(fileName);
                        if (!filenameSet.contains(prefixName)) {
                            doTask(executor, file);
                            processedFiles.add(baseName);  // 记录已处理的文件名
                        }
//                        String filePath = file.getAbsolutePath();
//                        if (!processedFiles.contains(filePath) &&  // 新增：检查是否已处理
//                                !filenameSet.contains(FileUtil.filePrefixName(fileName))) {
//                            processedFiles.add(filePath);  // 新增：将处理过的文件路径加入集合
//                            doTask(executor, file);
//                        }
                    }
                } else if (file.isDirectory() && file.exists()) {
                    queryDirectory(executor, file);
                }
            }
        }
    }

    private void doTask(ExecutorService executor, File file) {
        Runnable task = () -> {
            printFile(file);
            doTarGz(file);
        };
        executor.submit(task);
    }

    private void printFile(File file) {
        logger.info(file.getName());
    }

    private String relativePath(String absolutePath, String[] dataSource) {
        for (String path : dataSource) {
            if (absolutePath.startsWith(path)) {
                return absolutePath.substring(path.length() + 1);
            }
        }
        return null;
    }

//    private boolean isFileDownloading(File file) {
//        // 这里假设您通过文件的某个特定属性或其他方式来判断文件是否正在下载
//        // 以下是一个简单的示例，您需要根据实际情况修改此判断逻辑
//        // 例如，如果文件存在一个名为"Downloading"的标记文件，则认为正在下载
//        String fileName = file.getName();
//        return fileName.endsWith("downloading");
//    }

    private void doTarGz(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(".downloading")) {
            logger.info("文件正在下载，暂不解析: " + file.getName());
            return;
        }
        String absolutePath = file.getAbsolutePath();
        String relativePath = relativePath(absolutePath, systemConfig.getDATA_SOURCE());
        long tarGzSize = file.length();
        long size = tarGzSize / (1024 * 1024);
        long startTime = System.currentTimeMillis();
        long lastModified = file.lastModified();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lastModified);
        String dateStr = simpleDateFormat.format(date);
        String prefix = FileUtil.filePrefixName(fileName);
        File directory = FileUtil.unTarGz(systemConfig.getUNTARGZ_PATH(), file);
        if (directory != null&&!fileName.endsWith(".downloading")) {
            File[] files = directory.listFiles();
            if (prefix.startsWith("GF3")) {
                RadarSatellite radarSatellite = new RadarSatellite();
                assert files != null;
                FileUtil.doFiles(systemConfig.getFILE_PATH(), files, prefix, radarSatellite);
                radarSatellite.setDirectory(prefix);
                radarSatellite.setTarGzSize(tarGzSize);
                radarSatellite.setTgLastModified(dateStr);
                radarSatellite.setOriginPath(absolutePath);
                radarSatellite.setRelativePath(relativePath);
                raSatService.create(radarSatellite);
                logger.info("radar satellite create: " + radarSatellite.getDirectory());
            }
            if (prefix.startsWith("GF1") ||
                    prefix.startsWith("GF2") ||
                    prefix.startsWith("GF4") ||
                    prefix.startsWith("GF5") ||
                    prefix.startsWith("GF6") ||
                    prefix.startsWith("GF7") ||
                    prefix.startsWith("ZY") ||
                    prefix.startsWith("zy")) {
                OpticalSatellite opticalSatellite = new OpticalSatellite();
                assert files != null;
                FileUtil.doFiles(systemConfig.getFILE_PATH(), files, prefix, opticalSatellite);
                opticalSatellite.setDirectory(prefix);
                opticalSatellite.setTarGzSize(tarGzSize);
                opticalSatellite.setTgLastModified(dateStr);
                opticalSatellite.setOriginPath(absolutePath);
                opticalSatellite.setRelativePath(relativePath);
                opSatService.create(opticalSatellite);
                logger.info("optical satellite create: " + opticalSatellite.getDirectory());
            }
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        logger.info("file size：" + size + " m-" + "running time：" + elapsedTime + " ms");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testConfig() {
        return Arrays.toString(systemConfig.getDATA_SOURCE()) + " : " + systemConfig.getUNTARGZ_PATH() + " : " +
                systemConfig.getFILE_PATH();
    }

}
