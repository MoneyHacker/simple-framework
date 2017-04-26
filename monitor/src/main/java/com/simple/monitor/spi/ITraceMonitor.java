package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/14 14:52
 * 监控点
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
interface ITraceMonitor {
    //监控点的顺序,值越小,越靠前,起始监控点的order=0,
    Byte getOrder();
    //监控点代码执行的开始时间,单位毫秒
    Long getStratTime();
    //监控点代码执行完成时间,单位毫秒
    Long getEndTime();
    //被监控类的全名称
    String getMonitoredClassName();
    //监控点的唯一标识
    String getTraceId();



}
