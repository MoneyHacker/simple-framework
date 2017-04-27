package com.simple;


import com.simple.monitor.annotation.TraceMethod;
import com.simple.monitor.spi.TraceTypeEnum;

/**
 * Created by simple on 2017/4/26 9:37
 * @mail
 * @since 1.0
 */
public class TracerTester {
    public static void  main(String[] args) {
        TracerTester tt = new TracerTester();
        tt.testOne(12);
    }
    @TraceMethod(traceType = TraceTypeEnum.Service,include = "i")
    public  void testOne(Integer i) {
        System.out.println(i);
        testTwo(i+"name");
    }
    @TraceMethod(traceType = TraceTypeEnum.Service,traceParameter = true)
    public void testTwo(String name) {
        System.out.println("testTwo");
    }
}
/**
 16:15:56.295 [main] DEBUG c.s.m.support.DefaultTraceParameter - 参量名=12,参数类型=java.lang.Integer
 16:15:56.300 [main] DEBUG com.simple.monitor.support.Tracer - startTrace:Thread.currentThread()=Thread[main,5,main]
 16:15:56.328 [main] INFO  c.s.monitor.support.DefaultTraceMeta -(监控开始点) startTrace:traceMeta=TraceMeta(order=1, startTime=1493194556328, endTime=null, monitoredClassName=com.simple.TracerTester, medthodName=testOne, traceId=1, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12}, throwable=null, exceptionMsg=null)
 startTrace:traceMeta=TraceMeta(order=1, startTime=1493194556328, endTime=null, monitoredClassName=com.simple.TracerTester, medthodName=testOne, traceId=1, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12}, throwable=null, exceptionMsg=null)
 12
 16:15:56.329 [main] DEBUG c.s.m.support.DefaultTraceParameter - 参量名=12name,参数类型=java.lang.String
 16:15:56.329 [main] DEBUG com.simple.monitor.support.Tracer - startTrace:Thread.currentThread()=Thread[main,5,main]
 16:15:56.329 [main] INFO  c.s.monitor.support.DefaultTraceMeta - (监控开始点)startTrace:traceMeta=TraceMeta(order=1, startTime=1493194556329, endTime=null, monitoredClassName=com.simple.TracerTester, medthodName=testTwo, traceId=2, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12name}, throwable=null, exceptionMsg=null)
 startTrace:traceMeta=TraceMeta(order=1, startTime=1493194556329, endTime=null, monitoredClassName=com.simple.TracerTester, medthodName=testTwo, traceId=2, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12name}, throwable=null, exceptionMsg=null)
 testTwo
 16:15:56.330 [main] DEBUG com.simple.monitor.support.Tracer - endTrace:Thread.currentThread()=Thread[main,5,main],traceId=2
 16:15:56.330 [main] DEBUG com.simple.monitor.support.Tracer - (监控结束点)endTrace,traceMeta=TraceMeta(order=1, startTime=1493194556329, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testTwo, traceId=2, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12name}, throwable=null, exceptionMsg=null)
 16:15:56.330 [main] INFO  c.s.monitor.support.DefaultTraceMeta - endTrace:traceMeta=TraceMeta(order=1, startTime=1493194556329, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testTwo, traceId=2, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12name}, throwable=null, exceptionMsg=null)
 endTrace:traceMeta=TraceMeta(order=1, startTime=1493194556329, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testTwo, traceId=2, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12name}, throwable=null, exceptionMsg=null)
 16:15:56.330 [main] DEBUG com.simple.monitor.support.Tracer - endTrace:Thread.currentThread()=Thread[main,5,main],traceId=1
 16:15:56.330 [main] DEBUG com.simple.monitor.support.Tracer - (监控结束点)endTrace,traceMeta=TraceMeta(order=1, startTime=1493194556328, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testOne, traceId=1, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12}, throwable=null, exceptionMsg=null)
 16:15:56.330 [main] INFO  c.s.monitor.support.DefaultTraceMeta - endTrace:traceMeta=TraceMeta(order=1, startTime=1493194556328, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testOne, traceId=1, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12}, throwable=null, exceptionMsg=null)
 endTrace:traceMeta=TraceMeta(order=1, startTime=1493194556328, endTime=1493194556330, monitoredClassName=com.simple.TracerTester, medthodName=testOne, traceId=1, parentId=1, type=Service, threadName=main, parameterMap={parameter0=12}, throwable=null, exceptionMsg=null)
 **/