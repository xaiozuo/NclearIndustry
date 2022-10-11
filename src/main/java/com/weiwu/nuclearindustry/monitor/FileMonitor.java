package com.weiwu.nuclearindustry.monitor;

import com.weiwu.nuclearindustry.config.SystemConfig;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class FileMonitor {

    private static final Logger logger = Logger.getLogger(FileMonitor.class.getName());

    @Autowired
    private SystemConfig systemConfig;

    @PostConstruct
    private void init(){
        int time = 60;
        long interval = TimeUnit.SECONDS.toMillis(time);

        logger.info("init file monitor...");
        ArrayList<FileAlterationObserver> observers = new ArrayList<>();
        String[] dataSource = systemConfig.getDATA_SOURCE();
        for (int i = 0; i < dataSource.length; i++) {
            String path = dataSource[i];
            FileAlterationObserver observer = new FileAlterationObserver(path);
            observer.addListener(new FileListener());
            observers.add(observer);
        }
        //... Variable length parameter
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observers);

        try {
            logger.info("start file monitor");
            monitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
