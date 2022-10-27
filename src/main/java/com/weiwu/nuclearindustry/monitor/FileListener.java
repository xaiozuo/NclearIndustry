package com.weiwu.nuclearindustry.monitor;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.service.OpSatService;
import com.weiwu.nuclearindustry.service.RaSatService;
import com.weiwu.nuclearindustry.utils.BeanUtil;
import com.weiwu.nuclearindustry.utils.FileUtil;
import com.weiwu.nuclearindustry.utils.NameUtil;
import com.weiwu.nuclearindustry.utils.XmlParser;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class FileListener extends FileAlterationListenerAdaptor {
    private static FileListener fileListener;
    private static final XmlParser xmlParser = new XmlParser();
    private String imagesPath;
    private String untargzPath;

    @Autowired
    OpSatService opSatService;
    @Autowired
    RaSatService raSatService;
    @Autowired
    private SystemConfig systemConfig;

    @PostConstruct
    public void init() {
        fileListener = this;
        fileListener.imagesPath = systemConfig.getIMAGE_PATH();
        fileListener.untargzPath = systemConfig.getUNTARGZ_PATH();
    }

    public static final Logger logger = Logger.getLogger(FileListener.class.getName());

    @Override
    public void onStart(FileAlterationObserver observer) {
        logger.info("start monitor: " + observer.getClass().getSimpleName());
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
//        File[] files = directory.listFiles();
//        String directoryName = directory.getName();
//        if(directoryName.matches("GF[1-7]+")){
//            return;
//        }
//
//        if (directoryName.startsWith("GF3")) {
//            RadarSatellite radarSatellite = new RadarSatellite();
//            radarSatellite = (RadarSatellite) doFiles(files, directoryName, radarSatellite);
//            radarSatellite.setDirectory(directoryName);
//            fileListener.raSatService.create(radarSatellite);
//        }
//        if (directoryName.startsWith("GF1") ||
//                directoryName.startsWith("GF2") ||
//                directoryName.startsWith("GF4") ||
//                directoryName.startsWith("GF5") ||
//                directoryName.startsWith("GF6") ||
//                directoryName.startsWith("GF7")) {
//            OpticalSatellite opticalSatellite = new OpticalSatellite();
//            opticalSatellite = (OpticalSatellite) doFiles(files, directoryName, opticalSatellite);
//            opticalSatellite.setDirectory(directoryName);
//            fileListener.opSatService.create(opticalSatellite);
//        }
    }

    @SneakyThrows
    private void objectSetFieldValue(Object object, String field, String value){
        Class<?> aClass = object.getClass();
        Method getField = aClass.getDeclaredMethod("get" + field);
        Method setField = aClass.getDeclaredMethod("set" + field, String.class);
        String val = (String) getField.invoke(object);
        if(val == null){
            setField.invoke(object, value);
        }
    }

    @SneakyThrows
    private void doJpg(String imagesPath, File file, String directoryName, Object object) {
        String newPath = imagesPath + File.separator + directoryName + File.separator + file.getName();
        File newFile = new File(newPath);
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        Class<?> aClass = object.getClass();
        if(prefix.equals(directoryName) || prefix.contains(directoryName) ||
                NameUtil.nameEqual(prefix, directoryName)){
            if(!prefix.contains("thumb") && !prefix.contains("Thumb")){
                Method getImageUrl = aClass.getDeclaredMethod("getImageUrl");
                Method setImageUrl = aClass.getDeclaredMethod("setImageUrl", String.class);
                String imageUrl = (String) getImageUrl.invoke(object);
                if(imageUrl == null){
                    setImageUrl.invoke(object, fileName);
                    FileUtils.copyFile(file, newFile);
                }
            }
            if(prefix.contains("thumb") || prefix.contains("Thumb")){
                Method getThumbUrl = aClass.getDeclaredMethod("getThumbUrl");
                Method setThumbUrl = aClass.getDeclaredMethod("setThumbUrl", String.class);
                String thumbUrl = (String) getThumbUrl.invoke(object);
                if(thumbUrl == null){
                    setThumbUrl.invoke(object, fileName);
                    FileUtils.copyFile(file, newFile);
                }
            }
        }
    }

    /**
     * Ability to parse multiple XML files
     *
     * @param file
     * @param directoryName
     * @param object
     */
    @SneakyThrows
    private void doXml(File file, String directoryName, Object object) {
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        if (fileName.startsWith("GF3") || fileName.startsWith("ZY") ||
                prefix.equals(directoryName) || prefix.contains(directoryName)) {
            BeanUtil.build(new XmlParser(), file, object);
        }
    }

    private Object doFiles(File[] files, String directoryName, Object object) {
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith("jpg") || fileName.endsWith("xml")) {
                if (fileName.endsWith("xml")) {
                    doXml(file, directoryName, object);
                }
                if (fileName.endsWith("jpg")) {
                    doJpg(fileListener.imagesPath, file, directoryName, object);
                }
            }
        }
        return object;
    }

    @Override
    public void onDirectoryChange(File directory) {
        logger.info("file directory change: " + directory.getName());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        logger.info("file directory delete: " + directory.getName());
    }

    @SneakyThrows
    private Object doTarGz(File file, String prefix, Object object){
        String fileName = file.getName();
        if(fileName.endsWith(".tar.gz") || fileName.endsWith(".tar")){
            TarArchiveInputStream tai = null;
            if(fileName.endsWith(".tar.gz")){
                GzipCompressorInputStream gci = new GzipCompressorInputStream(
                        Files.newInputStream(file.toPath()), true);
                tai = new TarArchiveInputStream(gci);
            }
            if(fileName.endsWith(".tar")){
                tai = new TarArchiveInputStream(Files.newInputStream(file.toPath()));
            }

            TarArchiveEntry entry;
            while((entry = tai.getNextTarEntry()) != null){
                String entryName = entry.getName();
                if(entry.isDirectory() || (!entryName.endsWith(".xml") && !entryName.endsWith(".jpg"))){
                    continue;
                }
                if(entryName.endsWith(".xml") || entryName.endsWith(".jpg")){
                    String filename = fileListener.untargzPath + File.separator + prefix + File.separator + entryName;
                    File entryFile = FileUtil.getFile(tai, filename);
                    if (entryName.endsWith("xml")) {
                        doXml(entryFile, prefix, object);
                    }
                    if (entryName.endsWith("jpg")) {
                        doJpg(fileListener.imagesPath, entryFile, prefix, object);
                    }
                }
            }
        }
        return object;
    }

    @Override
    public void onFileCreate(File file) {
        logger.info("new file create: " + file.getName());
        String fileName = file.getName();
        long tarGzSize = file.length();
        long lastModified = file.lastModified();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lastModified);
        String dateStr = simpleDateFormat.format(date);
        String prefix = FileUtil.filePrefixName(fileName);
        File directory = FileUtil.unTarGz(fileListener.untargzPath, file);
        if(directory != null) {
            File[] files = directory.listFiles();
            if (prefix.startsWith("GF3")) {
                RadarSatellite radarSatellite = new RadarSatellite();
                assert files != null;
                FileUtil.doFiles(fileListener.imagesPath,
                        files, prefix, radarSatellite);
                radarSatellite.setDirectory(prefix);
                radarSatellite.setTarGzSize(tarGzSize);
                radarSatellite.setTgLastModified(dateStr);
                fileListener.raSatService.create(radarSatellite);
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
                FileUtil.doFiles(fileListener.imagesPath,
                        files, prefix, opticalSatellite);
                opticalSatellite.setDirectory(prefix);
                opticalSatellite.setTarGzSize(tarGzSize);
                opticalSatellite.setTgLastModified(dateStr);
                fileListener.opSatService.create(opticalSatellite);
            }
        }
    }

    @Override
    public void onFileChange(File file) {
        logger.info("file change: " + file.getName());
    }

    @Override
    public void onFileDelete(File file) {
        logger.info("file delete: " + file.getName());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        logger.info("stop monitor: " + observer.getClass().getSimpleName());
    }
}
