package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/24 14:40
 * 生成监控id(是否唯一)
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface ITraceIdGenerator {
    /**
     * 起始监控点的id
     * @return
     */
    Integer getTraceId();

    /**
     * 根据上一层监控点id生成当前监控点的id
     * @param parentTraceId
     * @return
     */
    Integer getTraceId(final Integer parentTraceId);
}
