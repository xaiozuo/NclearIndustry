package com.weiwu.nuclearindustry;

import com.weiwu.nuclearindustry.utils.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TarGzTests {
    public static final Logger logger = Logger.getLogger(TarGzTests.class.getName());
    private void unTarGz(File file){
        long size = file.length();
        size = size / (1024 * 1024);
        long startTime = System.currentTimeMillis();
        FileUtil.unTarGz("D:\\sasmac\\untargz", file);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        logger.info( "文件大小：" + size + "M-" + "程序运行时间：" + elapsedTime + " 毫秒");
    }

    private void doTask(File file, long delay) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            unTarGz(file);
        };
        executor.schedule(task, delay, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    @Test
    public void testTarGz() {
        long startTime = System.currentTimeMillis();
        String filePath0 = "D:\\sasmac\\data\\202238290059771\\tar\\GF5_AHSI_E121.49_N41.17_20190730_006516_L10000052406.tar";
        String filePath1 = "D:\\sasmac\\data\\202238290059771\\tar\\GF3_SAY_QPSI_011530_E107.6_N36.4_20181018_L1A_AHV_L10003530309.tar.gz";
        String filePath2 = "D:\\sasmac\\data\\202238290059771\\tar\\GF7_DLC_E120.0_N30.1_20200503_L1A0000093851.tar.gz";
        String filePath3 = "D:\\sasmac\\data\\202238290059771\\tar\\GF3_KRN_FSII_019137_W115.4_N37.6_20200329_L1A_HHHV_L10004710680.tar.gz";
        ArrayList<String> files = new ArrayList<String>();
        files.add(filePath0);
        files.add(filePath1);
        files.add(filePath2);
        files.add(filePath3);
        for (String filePath : files) {
            File file = new File(filePath);
            unTarGz(file);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        logger.info("程序运行时间：" + elapsedTime + " 毫秒");
    }

    @Test
    public void testTarGzByThread() {
        long startTime = System.currentTimeMillis();
        String filePath0 = "D:\\sasmac\\data\\202238290059771\\tar\\GF5_AHSI_E121.49_N41.17_20190730_006516_L10000052406.tar";
        String filePath1 = "D:\\sasmac\\data\\202238290059771\\tar\\GF3_SAY_QPSI_011530_E107.6_N36.4_20181018_L1A_AHV_L10003530309.tar.gz";
        String filePath2 = "D:\\sasmac\\data\\202238290059771\\tar\\GF7_DLC_E120.0_N30.1_20200503_L1A0000093851.tar.gz";
        String filePath3 = "D:\\sasmac\\data\\202238290059771\\tar\\GF3_KRN_FSII_019137_W115.4_N37.6_20200329_L1A_HHHV_L10004710680.tar.gz";
        ArrayList<String> files = new ArrayList<String>();
        files.add(filePath0);
        files.add(filePath1);
        files.add(filePath2);
        files.add(filePath3);
        for (String filePath : files) {
            File file = new File(filePath);
            doTask(file, 10);
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        logger.info("程序运行时间：" + elapsedTime + " 毫秒");
        while (true) {}
    }
}
