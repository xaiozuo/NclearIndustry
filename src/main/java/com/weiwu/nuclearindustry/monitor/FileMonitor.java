package com.weiwu.nuclearindustry.monitor;

import com.weiwu.nuclearindustry.config.SystemConfig;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class FileMonitor {

    @PostConstruct
    public void init(){
        int time = 10;
        long interval = TimeUnit.SECONDS.toMillis(time);

        FileAlterationObserver observer = new FileAlterationObserver(SystemConfig.SOURCE_PATH);
        observer.addListener(new FileListener());
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);

        try {
            monitor.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
