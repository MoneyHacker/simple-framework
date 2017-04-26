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
    @TraceMethod(traceType = TraceTypeEnum.Service,traceParameter = true)
    public  void testOne(Integer i) {
        System.out.println(i);
        testTwo(i+"name");
    }
    @TraceMethod(traceType = TraceTypeEnum.Service,traceParameter = true)
    public void testTwo(String name) {
        System.out.println("testTwo");
    }
}
