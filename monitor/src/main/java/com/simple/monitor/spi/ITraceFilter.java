package com.simple.monitor.spi;

import java.lang.reflect.Method;

/**
 * Created by simple on 2017/4/24 9:30
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public interface ITraceFilter {
    /**
     * 是否是需要监控的类
     * @param className
     * @return
     */
    Boolean isTraceClass(final String className);


    /**
     * 监控点代码织入,class文件保存的路径
     * @return
     */
    String getTraceDebugDumpDirectory();

    /**
     * 是否是需要监控的方法
     * @param method
     * @return
     */
    Boolean isTraceMethod(final Method method);
}
