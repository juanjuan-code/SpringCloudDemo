package com.juanjuan.service.executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @Author: yuhui.guan
 * @Date: 2020/8/3 17:45
 */
public class ExecutorsDemo {
    /**
     * 创建一个固定长度的线程池，每当提交一个任务就创建一个线程，直到达到线程池的最大数量，这时线程规模将不再变化，
     * 当线程发生未预期的错误而结束时，线程池会补充一个新的线程。
     */
    ExecutorService executorService = Executors.newFixedThreadPool(0);

    /**
     * 创建一个可缓存的线程池，如果线程池的规模超过了处理需求，将自动回收空闲线程，而当需求增加时，则可以自动添加新线程，
     * 线程池的规模不存在任何限制。
     */
    private ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 这是一个单线程的Executor，它创建单个工作线程来执行任务，如果这个线程异常结束，会创建一个新的来替代它；
     * 它的特点是能确保依照任务在队列中的顺序来串行执行。
     */
    private ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * newSingleThreadScheduledExecutor和newScheduledThreadPool(int corePoolSize)，创建的是个ScheduledExecutorService，
     * 可以进行定时或周期性的工作调度，区别在于单一工作线程还是多个工作线程
     */
    private ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(0);
    private ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();


    /**
     * 手动创建线程池
     */
    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
    /**
     * 1、corePoolSize线程池的核心线程数
     * 2、maximumPoolSize能容纳的最大线程数
     * 3、keepAliveTime空闲线程存活时间
     * 4、unit 存活的时间单位
     * 5、workQueue 存放提交但未执行任务的队列
     * 6、threadFactory 创建线程的工厂类
     * 7、handler 等待队列满后的拒绝策略 -> defaultHandler =    new AbortPolicy();
     */
    private static final ExecutorService TASK_EXE = new ThreadPoolExecutor(10, 20, 200L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), NAMED_THREAD_FACTORY);

    public static synchronized ExecutorService getInstanceExecutor() {

        return TASK_EXE;
    }

}
