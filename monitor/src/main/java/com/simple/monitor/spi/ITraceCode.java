package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/19 18:08
 * 定义监控点需要插入的代码
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface ITraceCode {
    default String before(){
        return "";
    }

    /**
     * 监控点方法结时需要织入的代码
     * @return
     */
    default  String end() {
       return " { TraceFactory.endTrace(this);}";
    }
}
