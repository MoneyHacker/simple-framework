package com.simple.agent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by simple on 2017/4/26 16:57
 *
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
public class KlassFactory {
    private KlassFactory(){}
    private static Map<String,KlassInfo> klassMap = new HashMap<>();

    public  static void put(final String klassName, final KlassInfo klassInfo) {
        klassMap.put(klassName,klassInfo);
    }
    public static  void showDetail() {
        System.out.println(klassMap);
    }
}
