package com.simple.monitor;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.simple.monitor.spi.ITraceRpc;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by simple on 2017/4/17 10:56
 * dubbo rpc 时传递 traceId
 * @mail moneyhacker@163.com
 * @since 1.0
 */
@Slf4j
@Activate(group = Constants.CONSUMER)
public class RpcFilter implements Filter {
    private ITraceRpc traceRpc = ExtensionFactory.getExtension(ExtensionPointEnum.traceRpc.name());
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException{
        log.debug("Thread.currentThread()={},_traceId={}",Thread.currentThread(),TraceIdContext.getTraceId());
        //向远程服务传递调用者traceId
        traceRpc.setTraceId(TraceIdContext.getTraceId());
        Result result =  invoker.invoke(invocation);
        RpcContext.getContext().removeAttachment(ITraceRpc.RPC_TRACEID_NAME);
        return result;
    }
}
