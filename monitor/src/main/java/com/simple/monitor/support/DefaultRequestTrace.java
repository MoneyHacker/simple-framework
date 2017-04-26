package com.simple.monitor.support;

import com.simple.monitor.spi.IRequestTrace;

/**
 * Created by simple on 2017/4/19 18:19
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public class DefaultRequestTrace implements IRequestTrace {
    @Override
    public ParameterMeta getRequestMeta(){
        return null;
    }
}
