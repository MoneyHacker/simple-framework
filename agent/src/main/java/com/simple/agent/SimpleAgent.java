package com.simple.agent;

import com.simple.monitor.ExtensionFactory;
import com.simple.monitor.ExtensionPointEnum;
import com.simple.monitor.spi.ITraceFilter;

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
     * @create 2016年3月30日
     */
    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("=========premain方法执行start========");
        ITraceFilter traceFilter = ExtensionFactory.getExtension(ExtensionPointEnum.traceFilter.name());
//        parseLoadedClass(traceFilter, inst.getAllLoadedClasses());
//        System.out.println("###############################");
//       KlassFactory.showDetail();
        System.out.println("agentOps=" + agentOps);
        inst.addTransformer(new ClassFileTransformerImpl(traceFilter), true);
        System.out.println("=========premain方法执行end========");
    }

    public static void premain(String var0) throws Exception{
        System.out.println("=========main方法执行var0========");
    }

    /*

    private static void parseLoadedClass(final ITraceFilter traceFilter, final Class[] classes){
        for (Class klass : classes) {
            if (!traceFilter.isTraceClass(klass.getName())) {
                //不是需要监控的类
                continue;
            }
            Method[] methods = klass.getMethods();
            List<MethodInfo> methodInfoList = null;
            for (Method method : methods) {
                if (!traceFilter.isTraceMethod(method)) {
                    //不是需要监控的方法
                    continue;
                }
                if (null == methodInfoList) {
                    methodInfoList = new ArrayList<>();
                }
                MethodInfo methodInfo = new MethodInfo();
                methodInfo.setName(method.getName());
                methodInfo.setMethod(method);
                List<MethodParameterInfo> methodParameterInfoList = collectTraceMethodParameterInfo(method);
                methodInfo.setParameterInfoList(methodParameterInfoList);
                methodInfoList.add(methodInfo);
            }
            if (null != methodInfoList) {
                KlassInfo klassInfo = new KlassInfo();
                klassInfo.setKlass(klass);
                klassInfo.setMethodInfoList(methodInfoList);
                KlassFactory.put(klass.getName(), klassInfo);
            }
        }
    }

    private static List<MethodParameterInfo> collectTraceMethodParameterInfo(final Method method){
        Parameter[] parameters = method.getParameters();
        if (null == parameters || parameters.length== 0) {
            return  null;
        }
        List<MethodParameterInfo> methodParameterInfoList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            MethodParameterInfo methodParameterInfo = new MethodParameterInfo();
            methodParameterInfo.setName(parameter.getName());
            methodParameterInfo.setClassType(parameter.getType());
            Type type = parameter.getParameterizedType();
            methodParameterInfo.setTypeName(type.getTypeName());
            methodParameterInfo.setType(type);
            methodParameterInfoList.add(methodParameterInfo);
        }
        return methodParameterInfoList;
    }
    */
}
