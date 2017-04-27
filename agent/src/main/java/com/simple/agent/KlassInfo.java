package com.simple.agent;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by simple on 2017/4/26 16:54
 *
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
@Setter
@Getter
@ToString
public class KlassInfo {
    private String name;
    private Class klass;
    private List<MethodInfo> methodInfoList;
}
