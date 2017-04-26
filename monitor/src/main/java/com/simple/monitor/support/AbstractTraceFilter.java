package com.simple.monitor.support;

import com.simple.monitor.spi.ITraceFilter;

/**
 * Created by simple on 2017/4/24 16:43
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public abstract  class AbstractTraceFilter implements ITraceFilter {
    @Override
    public String getTraceDebugDumpDirectory(){
        return null;
    }
}
