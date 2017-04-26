package com.simple.monitor.support;

import com.simple.monitor.spi.ITraceParameter;
import com.simple.monitor.spi.TraceTypeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by simple on 2017/4/20 9:16
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
@Slf4j
public class DefaultTraceParameter implements ITraceParameter,java.io.Serializable {
    @Override
    public ParameterMeta geParameterMeta(TraceTypeEnum traceTypeEnum) {
        switch (traceTypeEnum) {
            case  API:
        }
        return null;
    }
    @Override
    public  String getParameterValue(Object variableName, String variableTypeName) {
        log.debug("参量名={},参数类型={}",variableName,variableTypeName);
        //javax.servlet.http.HttpServletRequest
        if (null == variableName || null == variableTypeName) {
            return  null;
        }
        if ("javax.servlet.http.HttpServletRequest".equals(variableTypeName)) {
            return ((HttpServletRequest)variableName).getRequestURL().toString()+"?"+((HttpServletRequest)variableName).getQueryString();
        }else {
            //其它类型直接返回toString对应的值
            return  String.valueOf(variableName);
        }
    }

}
