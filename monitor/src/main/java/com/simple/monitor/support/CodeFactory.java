package com.simple.monitor.support;

import com.simple.monitor.annotation.TraceMethod;
import com.simple.monitor.spi.ITraceParameter;

/**
 * Created by simple on 2017/4/20 9:49
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class CodeFactory {
    private final static CodeFactory codeFactory = new CodeFactory();
    private final ITraceParameter  traceParameter;
    private CodeFactory(){
        traceParameter = new DefaultTraceParameter();
    }
    public static  String getBeforeCode(final TraceMethod traceMethod){
        return "";
    }

    public static <T> String  getParameterValue(final T variableName,final String variableTypeName){
        return codeFactory.traceParameter.getParameterValue(variableName,variableTypeName);
    }

}
