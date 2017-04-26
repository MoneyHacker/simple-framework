package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/24 15:41
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface ITraceRpc {
    String RPC_TRACEID_NAME = "_traceId";

    Integer getTraceId();

    void setTraceId(final Integer tracdId);
}
