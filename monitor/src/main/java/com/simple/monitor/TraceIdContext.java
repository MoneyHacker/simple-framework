package com.simple.monitor;

import com.simple.monitor.spi.ITraceIdGenerator;

/**
 * Created by simple on 2017/4/14 16:50
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
public class TraceIdContext {
    //缓存每个线程独立的变量(traceId),不会并发到同一个线程,不会有问题
    private final static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    private final static ITraceIdGenerator traceIdGenerator = ExtensionFactory.getExtension(ExtensionPointEnum.traceId.name());

    private TraceIdContext(){}

    /**
     * @param traceId
     * @return
     */
    public  static  Integer getAndSetSubTraceId(Integer traceId) {
        Integer currentTraceId = traceIdGenerator.getTraceId(traceId);
        threadLocal.set(currentTraceId);
        return currentTraceId;
    }


    public static void setTraceId(Integer traceId) {
        threadLocal.set(traceId);
    }
    public static Integer getAndSetTraceId() {
         Integer traceId =  traceIdGenerator.getTraceId();
        threadLocal.set(traceId);
        return  traceId;
    }

    public static Integer getTraceId() {
        return threadLocal.get();
    }



 }
