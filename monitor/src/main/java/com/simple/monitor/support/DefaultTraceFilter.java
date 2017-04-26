package com.simple.monitor.support;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simple on 2017/4/24 9:35
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class DefaultTraceFilter extends AbstractTraceFilter {
    List<String> noTraceMethodName = new ArrayList(){{
        add("getClass");
        add("hashCode");
        add("equals");
        add("clone");
        add("toString");
        add("notify");
        add("notifyAll");
        add("wait");
        add("finalize");
    }};
    @Override
    public Boolean isTraceClass(String className){
        return className.startsWith("com.simple");
    }


    @Override
    public Boolean isTraceMethod(String className, String methodName){
        return !noTraceMethodName.contains(methodName);
    }

}
