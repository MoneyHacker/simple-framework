package com.simple.monitor.annotation;

import com.simple.monitor.spi.TraceTypeEnum;

import java.lang.annotation.*;

/**
 * Created by simple on 2017/4/14 15:03
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TraceMethod {
    /**
     * 监控点类型
     */
    TraceTypeEnum traceType();

    /**
     * 需要监控记录的参数名称,
     * 当指定include值时,相当于traceParameter=true,而且不考虑exclude参数值
     * @return
     */
    String include() default "";
    /**
     * 是否监控方法参数,默认不监控
     * @return
     */

    boolean traceParameter() default  false;

    /**
     * 不需要监控参数的名称,多个逗号分隔
     * @return
     */
    String exclude() default  "";

}
