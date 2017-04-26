package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/14 16:54
 * 对监控信息的处理
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface ITraceMeta {
    //开始监控
    void startTrace(final TraceMeta traceMeta);

    //结束监控
    void endTrace(final TraceMeta traceMeta);

}
