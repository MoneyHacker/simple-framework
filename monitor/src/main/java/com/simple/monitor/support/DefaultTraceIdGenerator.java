package com.simple.monitor.support;

import com.simple.monitor.spi.ITraceIdGenerator;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by simple on 2017/4/24 14:43
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class DefaultTraceIdGenerator implements ITraceIdGenerator {
    private volatile   AtomicInteger counter =  new AtomicInteger(1);
    //当生成traceId到达此值时,重新从0开始计数,在重新计数前,除非并发数 >  maxCurrent,不然不会重复
    private int maxCurrent = 200000;
    private int maxTraceId = Integer.MAX_VALUE - maxCurrent;
    private final  ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    @Override
    public Integer getTraceId() {
        scheduledExecutorService.execute(() -> {
            if (counter.get() == maxTraceId) {
                counter.set(1);
            }
        });
        return counter.getAndIncrement();
    }
    @Override
    public Integer getTraceId(Integer parentTraceId){
        return getTraceId();
    }
}
