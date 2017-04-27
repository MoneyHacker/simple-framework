package com.simple.agent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * Created by simple on 2017/4/26 17:25
 * 方法参数
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class MethodParameterInfo {
    private  String name;
    private Class<?> classType;
    private Parameter parameter;
    private String typeName;
    private Type type;
}
