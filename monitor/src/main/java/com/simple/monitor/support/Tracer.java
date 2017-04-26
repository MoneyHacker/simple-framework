package com.simple.monitor.support;

import com.simple.monitor.ExtensionFactory;
import com.simple.monitor.ExtensionPointEnum;
import com.simple.monitor.spi.ITraceMeta;
import com.simple.monitor.spi.ITraceRpc;
import com.simple.monitor.spi.TraceMeta;
import com.simple.monitor.spi.TraceTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.simple.monitor.TraceIdContext.*;
import static java.lang.Thread.currentThread;

/**
 * Created by simple on 2017/4/17 9:08
 * 同步记录,目前未做异步
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
@Slf4j
public class Tracer {
        private static final Map<Integer,TraceMeta> traceMetaMap = new HashMap<>();
        private static ITraceRpc traceRpc = ExtensionFactory.getExtension(ExtensionPointEnum.traceRpc.name());
        private static ITraceMeta traceMetaExtension = ExtensionFactory.getExtension(ExtensionPointEnum.traceMeta.name());
        private static Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler(){
        /**
         * 当出现异常时,这个方法在endTrace之后调用的
         * @param t
         * @param e
         */
        @Override
        public void uncaughtException(Thread t, Throwable e){
            Thread.UncaughtExceptionHandler ueh =t.getDefaultUncaughtExceptionHandler();
            Integer traceId = getTraceId();
            TraceMeta traceMeta =   traceMetaMap.get(traceId);
            traceMeta.setThrowable(e);
            traceMeta.setEndTime(System.currentTimeMillis());
            log.debug("Thread.currentThread()={},traceId={},traceMeta=",currentThread(),traceId,traceMeta);
            if (ueh != null) {
                ueh.uncaughtException(t, e);
            } else if (!(e instanceof ThreadDeath)) {
                log.error("Exception in thread \""+ t.getName() + "\"");
                e.printStackTrace(System.err);
            }
            traceMetaExtension.endTrace(traceMeta);
        }
    };

    /**
     * 监控点开始
     * @param caller            调用者
     * @param traceTypeEnum     监控点类型
     * @param methodName        监控点所在的方法名称
     * @param parameterMap      监控点参数
     * @param <T>
     */
    public  static <T>  void  startTrace(T caller,TraceTypeEnum traceTypeEnum,final String methodName,final Map<String,Object> parameterMap){
        //还原,但如果用户自定义了UncaughtExceptionHandler,在监控过程中会替换
        Thread.currentThread().setUncaughtExceptionHandler(uncaughtExceptionHandler);
        log.debug("startTrace:Thread.currentThread()={}", currentThread());
        TraceMeta traceMeta = new TraceMeta();
        Integer traceId = getTraceId();
        if (null == traceId && traceTypeEnum.name().equals(TraceTypeEnum.Rpc.name())) {
            traceId = traceRpc.getTraceId();
            log.debug("远程调用traceId={}",traceId);
        }
        if (null == traceId) {
            //远程方法也可能在本地调用
            traceId = getAndSetTraceId();
            traceMeta.setParentId(traceId);
        }else{
            traceMeta.setParentId(traceId);
            traceId =  getAndSetSubTraceId(traceId);
        }
        traceMeta.setStartTime(System.currentTimeMillis());
        traceMeta.setMedthodName(methodName);
        traceMeta.setMonitoredClassName(caller.getClass().getName());
        traceMeta.setTraceId(traceId);
        traceMeta.setType(traceTypeEnum.name());
        traceMeta.setThreadName(currentThread().getName());
        traceMeta.setParameterMap(parameterMap);
        traceMetaMap.put(traceId,traceMeta);
        traceMetaExtension.startTrace(traceMeta);
    }

    /**
     * 监控点结束
     * @param caller
     * @param <T>
     */
    public  static <T> void endTrace(T caller) {
        Integer traceId = getTraceId();
        log.debug("endTrace:Thread.currentThread()={},traceId={}", currentThread(),traceId);
        TraceMeta traceMeta = traceMetaMap.get(traceId);
        traceMeta.setEndTime(System.currentTimeMillis());
        log.debug("endTrace,traceMeta={}",traceMeta);
       // TraceContext.endTrace();
        setTraceId(traceMeta.getParentId());
        traceMetaExtension.endTrace(traceMeta);
    }
}
