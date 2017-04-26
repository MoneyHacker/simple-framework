package com.simple.agent;


import com.simple.monitor.ExtensionFactory;
import com.simple.monitor.ExtensionPointEnum;

import java.lang.instrument.Instrumentation;

/**
 * Created by simple on 2017/3/14 16:50
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
public class SimpleAgent {
    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     * 并被同一个System ClassLoader装载
     * 被统一的安全策略(security policy)和上下文(context)管理
     *
     * @param agentOps
     * @param inst
     * @author SHANHY
     * @create  2016年3月30日
     */
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行start========");
        System.out.println("agentOps="+agentOps);
        inst.addTransformer(new ClassFileTransformerImpl(ExtensionFactory.getExtension(ExtensionPointEnum.traceFilter.name())),true);
        System.out.println("=========premain方法执行end========");
    }
    public static void premain(String var0) throws Exception {
        System.out.println("=========main方法执行var0========");
    }
    public  static  void main(String[] args) {
        System.out.println("main");
    }
}
