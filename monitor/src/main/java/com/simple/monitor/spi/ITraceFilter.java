package com.simple.monitor.spi;

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
     * 是否是需要监控的方法
     * @param className
     * @param methodName
     * @return
     */
    Boolean isTraceMethod(final String className,final String methodName);

    /**
     * 监控点代码织入,class文件保存的路径
     * @return
     */
    String getTraceDebugDumpDirectory();
}
