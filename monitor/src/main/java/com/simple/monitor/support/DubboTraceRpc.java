package com.simple.monitor.support;

import com.alibaba.dubbo.rpc.RpcContext;
import com.simple.monitor.spi.ITraceRpc;

/**
 * Created by simple on 2017/4/24 15:43
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class DubboTraceRpc implements ITraceRpc {
    @Override
    public Integer getTraceId() {
        return  Integer.valueOf(RpcContext.getContext().getAttachment(RPC_TRACEID_NAME));
    }

    @Override
    public void setTraceId(Integer tracdId) {
        RpcContext.getContext().setAttachment(RPC_TRACEID_NAME, tracdId.toString());
    }
}
