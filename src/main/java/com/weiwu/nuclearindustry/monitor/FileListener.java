package com.weiwu.nuclearindustry.monitor;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.service.OpSatService;
import com.weiwu.nuclearindustry.service.RaSatService;
import com.weiwu.nuclearindustry.service.impl.OpSatServiceImpl;
import com.weiwu.nuclearindustry.utils.BeanUtil;
import lombok.SneakyThrows;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.logging.Logger;

@Component
public class FileListener extends FileAlterationListenerAdaptor {
    private static FileListener fileListener;

    @Autowired
    OpSatService opSatService;
    @Autowired
    RaSatService raSatService;

    @PostConstruct
    public void init() {
        fileListener = this;
    }

    public static final Logger logger = Logger.getLogger(FileListener.class.getName());

    @Override
    public void onStart(FileAlterationObserver observer) {
        logger.info("start monitor: " + observer.getClass().getSimpleName());
    }

    @SneakyThrows
    @Override
    public void onDirectoryCreate(File directory) {
        logger.info("new file directory: " + directory.getName());
        File[] files = directory.listFiles();
        String directoryName = directory.getName();
        BeanUtil beanUtil = new BeanUtil();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.endsWith("jpg") || fileName.endsWith("xml")) {
                if (fileName.endsWith("xml")) {
                    String prefix = fileName.substring(0, fileName.lastIndexOf("."));
                    if (prefix.contains(directoryName)) {
                        if (fileName.startsWith("GF3")) {
                            RadarSatellite radarSatellite = beanUtil.buildRaSat(file);
                            fileListener.raSatService.create(radarSatellite);
                        }
                        if (fileName.startsWith("GF1") ||
                                fileName.startsWith("GF2") ||
                                fileName.startsWith("GF4") ||
                                fileName.startsWith("GF5") ||
                                fileName.startsWith("GF6") ||
                                fileName.startsWith("GF7")) {
                            if (prefix.equals(directoryName)) {
                                OpticalSatellite opticalSatellite = beanUtil.buildOpSat(file);
                                fileListener.opSatService.create(opticalSatellite);
                            }
                        }
                    }
                }
            }
        }
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
    @Override
    public void onFileCreate(File file) {
        String[] parentArray = file.getParent().split("/|\\\\");
        String parentDir = parentArray[parentArray.length - 1];
        String fileName = file.getName();
        BeanUtil beanUtil = new BeanUtil();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        if (prefix.contains(parentDir) && fileName.endsWith("xml")) {
            if (fileName.startsWith("GF3")) {
                RadarSatellite radarSatellite = beanUtil.buildRaSat(file);
                fileListener.raSatService.create(radarSatellite);
            } else if (fileName.startsWith("GF1") || fileName.startsWith("GF2") || fileName.startsWith("GF4")
                    || fileName.startsWith("GF5") || fileName.startsWith("GF6") || fileName.startsWith("GF7")) {
                if (prefix.equals(parentDir)) {
                    OpticalSatellite opticalSatellite = beanUtil.buildOpSat(file);
                    fileListener.opSatService.create(opticalSatellite);
                }
            }
            logger.info("new file create: " + fileName);
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
