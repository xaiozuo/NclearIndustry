package com.weiwu.nuclearindustry;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTests {

    // 创建ThreadPoolExecutor实例
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5,  // corePoolSize
            10, // maximumPoolSize
            1,  // keepAliveTime
            TimeUnit.MINUTES, // unit
            new LinkedBlockingQueue<>() // workQueue
    );

    @Test
    public void testThreadPool() {
        // 提交任务给线程池执行
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());
        executor.submit(new MyTask());

        // 关闭线程池
        executor.shutdown();

        // 判断任务是否全部完成
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            boolean allTasksCompleted = executor.isTerminated();
            if (allTasksCompleted) {
                System.out.println("所有任务已完成");
            } else {
                System.out.println("还有任务在执行");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyTask implements Runnable {
        @Override
        public void run() {
            // 执行任务的逻辑
            System.out.println("run thread");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
