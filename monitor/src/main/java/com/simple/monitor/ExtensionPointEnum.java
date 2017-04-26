package com.simple.monitor;

import com.simple.monitor.support.DefaultTraceFilter;
import com.simple.monitor.support.DefaultTraceIdGenerator;
import com.simple.monitor.support.DefaultTraceMeta;
import com.simple.monitor.support.DubboTraceRpc;

/**
 * Created by simple on 2017/4/24 14:11
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
public enum ExtensionPointEnum {
    traceFilter("匹配需要监控的类的方法", DefaultTraceFilter.class.getName()),
    traceId("监控点id的生成规章", DefaultTraceIdGenerator.class.getName()),
    traceRpc("rpc监控点id的传递", DubboTraceRpc.class.getName()),
    traceMeta("监控点信息处理", DefaultTraceMeta.class.getName());
    private final String descs;//说明

    private final String defaultClassName;//默认实现类

    ExtensionPointEnum(final String descs,final String defaultClassName) {
        this.descs = descs;
        this.defaultClassName = defaultClassName;
    }

    public String getDefaultClassName(){
        return this.defaultClassName;
    }

    public static Boolean isExtendionPoint(final String name) {
      return null !=  ExtensionPointEnum.valueOf(name);
    }


}
