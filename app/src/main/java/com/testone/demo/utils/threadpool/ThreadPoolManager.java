package com.testone.demo.utils.threadpool;

import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明：Executors各个方法的弊端：
 * 1）newFixedThreadPool和newSingleThreadExecutor:
 *   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
 * 2）newCachedThreadPool和newScheduledThreadPool:
 *   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
 * 3) 注意，线程池不调用shutdown会长驻内存
 *
 * @author wangqi
 */
public class ThreadPoolManager {

    private static final int MaxQueueSize = 1024;
    private static final int MaxPoolSize = 128;
    private static final int ScheduledPoolSize = 256;
    private static final int MessageCacheTime = 1000;

    private static Map<ExecutorService, Object> mThreadPoolCache = new WeakHashMap<>();

    private static class MyRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
            getGlobalScheduledThreadPool().schedule(new Runnable() {
                @Override
                public void run() {
                    executor.execute(r);
                }
            }, 1000, TimeUnit.MILLISECONDS);
        }
    }

    private static class GlobalThreadPool {
        private static ThreadPoolExecutor instance = newThreadPool();
    }

    private static class GlobalMessageThreadPool {
        private static ThreadPoolExecutor instance = newMessageThreadPool();
    }

    private static class GlobalSingleThreadPool {
        private static ThreadPoolExecutor instance = newSingleThreadPool();
    }

    private static class GlobalScheduledThreadPool {
        private static ScheduledExecutorService instance = newScheduledThreadPool();
    }

    public static ThreadPoolExecutor getGlobalThreadPool() {
        return GlobalThreadPool.instance;
    }

    public static ThreadPoolExecutor getGlobalMessageThreadPool() {
        return GlobalMessageThreadPool.instance;
    }

    public static ThreadPoolExecutor getGlobalSingleThreadPool() {
        return GlobalSingleThreadPool.instance;
    }

    public static ScheduledExecutorService getGlobalScheduledThreadPool() {
        return GlobalScheduledThreadPool.instance;
    }

    private static ThreadPoolExecutor newThreadPool() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, MaxPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(MaxQueueSize), new MyRejectedExecutionHandler());
        mThreadPoolCache.put(threadPool, null);
        return threadPool;
    }

    private static ThreadPoolExecutor newMessageThreadPool() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(MaxQueueSize), new MyRejectedExecutionHandler()) {
            PriorityBlockingQueue<MessagePriorityTask> taskCache = new PriorityBlockingQueue(MaxQueueSize, new Comparator<MessagePriorityTask>() {
                @Override
                public int compare(MessagePriorityTask o1, MessagePriorityTask o2) {
                    return o1.compareTo(o2);
                }
            });

            {
                getGlobalScheduledThreadPool().scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        executeCache();
                    }
                }, MessageCacheTime, MessageCacheTime, TimeUnit.MILLISECONDS);
            }

            @Override
            public void execute(Runnable command) {
                taskCache.add((MessagePriorityTask) command);
            }

            private void executeCache() {
                while (taskCache.size() > 0) {
                    super.execute(taskCache.poll());
                }
            }
        };
        mThreadPoolCache.put(threadPool, null);
        return threadPool;
    }

    private static ScheduledExecutorService newScheduledThreadPool() {
        ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(16) {
            @Override
            protected void finalize() {
                ThreadPoolManager.shutdown(this);
            }
        };
        threadPool.setMaximumPoolSize(ScheduledPoolSize);
        mThreadPoolCache.put(threadPool, null);
        return threadPool;
    }

    private static ThreadPoolExecutor newSingleThreadPool() {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(MaxQueueSize), new MyRejectedExecutionHandler());
        mThreadPoolCache.put(threadPool, null);
        return threadPool;
    }

    public static void shutdownAll() {
        for (ExecutorService threadPool : mThreadPoolCache.keySet()) {
            shutdown(threadPool);
        }
        mThreadPoolCache.clear();
    }

    private static void shutdown(ExecutorService threadPool) {
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdown();
        }
        mThreadPoolCache.remove(threadPool);
    }

//    Example
//
//    ThreadPoolManager.getGlobalThreadPool().execute(new Runnable() {
//        @Override
//        public void run() {
//
//        }
//    });
}
