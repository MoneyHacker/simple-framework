package com.simple.monitor.spi;

import com.simple.monitor.support.ParameterMeta;

/**
 * Created by simple on 2017/4/20 9:14
 *
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
public interface ITraceParameter {
    /**
     * 通过监控点,获取监控点的参数信息
     * @param traceTypeEnum
     * @return
     */
    ParameterMeta geParameterMeta(final TraceTypeEnum traceTypeEnum);

    /**
     * 根据参数名称和参数类型,返回获取参数值的代码
     * @param variableName
     * @param variableTypeName
     * @return
     */
    <T> String getParameterValue(final T variableName,final String  variableTypeName);
}
