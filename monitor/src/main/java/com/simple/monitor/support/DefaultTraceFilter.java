package com.simple.monitor.support;

import com.simple.monitor.annotation.TraceMethod;

import java.lang.reflect.Method;

/**
 * Created by simple on 2017/4/24 9:35
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class DefaultTraceFilter extends AbstractTraceFilter {

    @Override
    public Boolean isTraceClass(String className){
        return className.startsWith("com.simple");
    }


    @Override
    public Boolean isTraceMethod(Method method){
        return null != method.getAnnotation(TraceMethod.class);
    }

}
