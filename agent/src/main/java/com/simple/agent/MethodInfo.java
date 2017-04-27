package com.simple.agent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by simple on 2017/4/26 16:55
 *
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class MethodInfo {
    private String name;
    private Method method;
    private List<MethodParameterInfo> parameterInfoList;
}
