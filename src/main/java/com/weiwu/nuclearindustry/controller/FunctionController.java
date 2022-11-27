package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.monitor.FileMonitor;
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

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String initDB(){
        String[] dataSource = systemConfig.getDATA_SOURCE();
        for (String s : dataSource) {
            File directory = new File(s);
            queryDirectory(directory);
        }
        return "success";
    }

    private void queryDirectory(File directory){
        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile() && file.exists()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".tar.gz") || fileName.endsWith(".tar")) {
                        doTarGz(file);
                    }
                } else if (file.isDirectory() && file.exists()) {
                    queryDirectory(file);
                }
            }
        }
    }

    private void doTarGz(File file){
        String fileName = file.getName();
        String absolutePath = file.getAbsolutePath();
        long tarGzSize = file.length();
        long lastModified = file.lastModified();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lastModified);
        String dateStr = simpleDateFormat.format(date);
        String prefix = FileUtil.filePrefixName(fileName);
        File directory = FileUtil.unTarGz(systemConfig.getUNTARGZ_PATH(), file);
        if(directory != null) {
            File[] files = directory.listFiles();
            if (prefix.startsWith("GF3")) {
                RadarSatellite radarSatellite = new RadarSatellite();
                assert files != null;
                FileUtil.doFiles(systemConfig.getFILE_PATH(), files, prefix, radarSatellite);
                radarSatellite.setDirectory(prefix);
                radarSatellite.setTarGzSize(tarGzSize);
                radarSatellite.setTgLastModified(dateStr);
                radarSatellite.setOriginPath(absolutePath);
                raSatService.create(radarSatellite);
                logger.info("radar satellite create: " + radarSatellite.toString());
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
                FileUtil.doFiles(systemConfig.getFILE_PATH(), files, prefix, opticalSatellite);
                opticalSatellite.setDirectory(prefix);
                opticalSatellite.setTarGzSize(tarGzSize);
                opticalSatellite.setTgLastModified(dateStr);
                opticalSatellite.setOriginPath(absolutePath);
                opSatService.create(opticalSatellite);
                logger.info("optical satellite create: " + opticalSatellite.toString());
            }
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testConfig(){
        return Arrays.toString(systemConfig.getDATA_SOURCE()) + " : " + systemConfig.getUNTARGZ_PATH() + " : " +
                systemConfig.getFILE_PATH();
    }
}
