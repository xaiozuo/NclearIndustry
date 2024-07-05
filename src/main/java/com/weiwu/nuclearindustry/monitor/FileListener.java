package com.weiwu.nuclearindustry.monitor;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.service.OpSatService;
import com.weiwu.nuclearindustry.service.RaSatService;
import com.weiwu.nuclearindustry.utils.FileUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class FileListener extends FileAlterationListenerAdaptor {
    private static FileListener fileListener;
    private String filesPath;
    private String untargzPath;
    private String[] dataSource;

    @Autowired
    OpSatService opSatService;
    @Autowired
    RaSatService raSatService;
    @Autowired
    private SystemConfig systemConfig;

    @PostConstruct
    public void init() {
        fileListener = this;
        fileListener.filesPath = systemConfig.getFILE_PATH();
        fileListener.untargzPath = systemConfig.getUNTARGZ_PATH();
        fileListener.dataSource = systemConfig.getDATA_SOURCE();
    }

    public static final Logger logger = Logger.getLogger(FileListener.class.getName());

    @Override
    public void onStart(FileAlterationObserver observer) {
//        logger.info("start monitor: " + observer.getClass().getSimpleName());
    }

    /**
     * Several cases of generating null data:
     * 1. don't have <xml version="1.0" encoding="UTF-8"?>
     * 2. file format don't match
     * 3. use GF3/GF1,GF2,GF4,GF5,GF6,GF7 directory name
     * @param directory The directory created (ignored)
     */
    @SneakyThrows
    @Override
    public void onDirectoryCreate(File directory) {
        logger.info("new file directory: " + directory.getName());
    }

    @Override
    public void onDirectoryChange(File directory) {
        logger.info("file directory change: " + directory.getName());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        logger.info("file directory delete: " + directory.getName());
    }

    private String relativePath(String absolutePath, String[] dataSource){
        for (int i = 0; i < dataSource.length; i++) {
            String path = dataSource[i];
            if(absolutePath.startsWith(path)){
                return absolutePath.substring(path.length() + 1);
            }
        }
        return null;
    }

    private void doTarGz(File file){
        if (file.exists() && file.canRead() && file.canWrite()) {
            logger.info("new file create: " + file.getName());
            String fileName = file.getName();
            String absolutePath = file.getAbsolutePath();
            String relativePath = relativePath(absolutePath, fileListener.dataSource);
            long tarGzSize = file.length();
            long size = tarGzSize / (1024 * 1024);
            long startTime = System.currentTimeMillis();
            long lastModified = file.lastModified();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(lastModified);
            String dateStr = simpleDateFormat.format(date);
            String prefix = FileUtil.filePrefixName(fileName);
            File directory = FileUtil.unTarGz(fileListener.untargzPath, file);
            System.out.println(directory);
            if(directory != null) {
                File[] files = directory.listFiles();
                if (prefix.startsWith("GF3")) {
                    RadarSatellite radarSatellite = new RadarSatellite();
                    assert files != null;
                    FileUtil.doFiles(fileListener.filesPath, files, prefix, radarSatellite);
                    radarSatellite.setDirectory(prefix);
                    radarSatellite.setTarGzSize(tarGzSize);
                    radarSatellite.setTgLastModified(dateStr);
                    radarSatellite.setOriginPath(absolutePath);
                    radarSatellite.setRelativePath(relativePath);
                    fileListener.raSatService.create(radarSatellite);
                    logger.info("radar satellite create: " + radarSatellite);
                }
                if (prefix.startsWith("GF1") ||
                        prefix.startsWith("GF2") ||
                        prefix.startsWith("GF4") ||
                        prefix.startsWith("GF5") ||
                        prefix.startsWith("GF6") ||
                        prefix.startsWith("GF7") ||
                        prefix.startsWith("ZY") ||
                        prefix.startsWith("zy") ) {
                    OpticalSatellite opticalSatellite = new OpticalSatellite();
                    assert files != null;
                    FileUtil.doFiles(fileListener.filesPath, files, prefix, opticalSatellite);
                    opticalSatellite.setDirectory(prefix);
                    opticalSatellite.setTarGzSize(tarGzSize);
                    opticalSatellite.setTgLastModified(dateStr);
                    opticalSatellite.setOriginPath(absolutePath);
                    opticalSatellite.setRelativePath(relativePath);
                    fileListener.opSatService.create(opticalSatellite);
                    logger.info("optical satellite create: " + opticalSatellite);
                }
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            logger.info( "file size：" + size + " m-" + "running time：" + elapsedTime + " ms");
        }
    }

    private void doTask(File file, Long delay) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            synchronized (file){
                doTarGz(file);
            }
        };
        executor.schedule(task, delay, TimeUnit.SECONDS);
        executor.shutdown();
    }

    @Override
    public void onFileCreate(File file) {
        if (!file.getName().endsWith(".downloading")) {  // 新增条件判断
            doTask(file, 10L);
        }
//        doTask(file, 10L);
    }

    @Override
    public void onFileChange(File file) {
//        logger.info("file change: " + file.getName());
//        doTask(file, 1L);
        if (!file.getName().endsWith(".downloading")) {  // 新增条件判断
            logger.info("file change: " + file.getName());
            doTask(file, 1L);
        }
    }

    @Override
    public void onFileDelete(File file) {
//        logger.info("file delete: " + file.getName());
        if (!file.getName().endsWith(".downloading")) {  // 新增条件判断
            logger.info("file delete: " + file.getName());
        }
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
//        logger.info("stop monitor: " + observer.getClass().getSimpleName());
    }
}
