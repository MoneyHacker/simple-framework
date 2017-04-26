package com.simple.monitor.spi;

import com.simple.monitor.support.ParameterMeta;

/**
 * Created by simple on 2017/4/19 18:14
 * 起始请求监控点
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface IRequestTrace {
    ParameterMeta getRequestMeta();
}
