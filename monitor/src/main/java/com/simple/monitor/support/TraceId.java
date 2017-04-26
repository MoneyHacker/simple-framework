package com.simple.monitor.support;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by simple on 2017/4/26 11:27
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
@Getter
@Setter
public class TraceId implements java.io.Serializable {
    private static final long serialVersionUID = -2664986025774331419L;
    //调用顺序
    private Byte order;

    private Integer id;
}
