package com.simple.monitor.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Created by simple on 2017/4/19 18:16
 *  起始请求的参数对象
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class ParameterMeta implements java.io.Serializable {
    private static final long serialVersionUID = -4331683959461377164L;
    private String url;
    private Map<String,Object> paramMap;
}
