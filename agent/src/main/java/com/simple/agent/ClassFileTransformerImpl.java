package com.simple.agent;

import com.simple.monitor.spi.ITraceFilter;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simple on 2017/3/14 18:22
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
public class ClassFileTransformerImpl implements ClassFileTransformer {
    private final ITraceFilter traceFilter;
    private final ClassPool cp;

    protected ClassFileTransformerImpl(final ITraceFilter traceFilter){
        this.traceFilter = traceFilter;
        this.cp = new ClassPool(false);
        if (null != traceFilter.getTraceDebugDumpDirectory()) {
            CtClass.debugDump = traceFilter.getTraceDebugDumpDirectory();
        }
    }
    /**
     *
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     * @throws IllegalClassFormatException
     */

    /**
     * 返回监控点代码头需要织入的代码片段
     * @return
     */
    private String getExcludeBeforeCode(final CtMethod ctMethod, final String exclude) {
//        System.out.println("方法名称====" +ctMethod.getClass().getName()+ "."+ctMethod.getName());
        StringBuilder sbCode = new StringBuilder("java.util.Map methodParameterMap = new java.util.WeakHashMap(8);");
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        try {
            CtClass[] parameterTypes = ctMethod.getParameterTypes();
            if (null != parameterTypes && parameterTypes.length > 0) {
                List<String> parametersName = null;
                if (null != exclude && exclude.length() > 0) {
                    parametersName = Arrays.asList(exclude.split(","));
                }
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                LocalVariableAttribute attribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                /**
                 * 因为在jvm LocalVariableAttribute 变量池时,方法参数和方法内部变量顺序可以任意,故无法根据index获取参数名称
                 * 只能根据javassist 提供的内部变量 $$ ,$0  来定位参数值
                 * |this (if non static) | arg1 | arg2 | ... | argN | var1 | ... | varN|
                 */
                int argIndex = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                String variableTypeName = "";
              //  int paramerLocalVariableIndex = 0;
                for (int i = 0; i < parameterTypes.length; i++) {
                //    paramerLocalVariableIndex = i + argIndex;
                    String  variableName = attribute.variableName(i + argIndex);
                    variableTypeName = parameterTypes[i].getName();
                    //如果方法是在static方法中调用的,第一个参数是this
                    if (variableName.equals("this")) {
                        variableName = attribute.variableName(i+1+ argIndex);

                    }
                    if (null != parametersName && parametersName.contains(variableName)) {
                        //不需要监控的参数
                        continue;
                    }
                    sbCode.append("methodParameterMap.put(\"parameter").append(i).append("\",")
                            .append("com.simple.monitor.support.CodeFactory.getParameterValue($").append(i+1)
                            .append(",\"").append(variableTypeName).append("\"));");
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return sbCode.toString();
    }

    private String getIncludeBeforeCode(final CtMethod ctMethod,final String include) throws  Exception{
//        System.out.println("方法名称====" +ctMethod.getClass().getName()+ "."+ctMethod.getName());
        if (null == include || include.length()==0) {
            return null;
        }
        CtClass[] parameterTypes = ctMethod.getParameterTypes();
        String[] includeParameterNames = include.split(",");
        StringBuilder sbCode = new StringBuilder("java.util.Map methodParameterMap = new java.util.WeakHashMap(")
                .append(includeParameterNames.length).append(");");
        int parameterIndex = 1;
        for (String pm:includeParameterNames) {
            sbCode.append("methodParameterMap.put(\"").append(pm).append("\",")
                    .append("com.simple.monitor.support.CodeFactory.getParameterValue($").append(parameterIndex++)
                    .append(",\"").append(parameterTypes[parameterIndex++].getName()).append("\"));");
        }
        return sbCode.toString();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException{
        String dotName = className.replaceAll("/", ".");
        if (!traceFilter.isTraceClass(dotName)) {
            //跳过不需要修改的类
            return null;
        }
        cp.appendClassPath(new LoaderClassPath(loader));
        cp.insertClassPath(new LoaderClassPath(Object.class.getClassLoader()));
        cp.insertClassPath(new ByteArrayClassPath(className, classfileBuffer));
        try {
            CtClass cc = cp.get(dotName);
            CtMethod[] methods = cc.getDeclaredMethods();
//            System.out.println(dotName + ":=" + Arrays.asList(methods));
            //系统方法应该跳过?
            boolean isMatched = false ;
            for (CtMethod cm : methods) {
//                System.out.println("方法名称====" + cm.getName());
                MethodInfo methodInfo = cm.getMethodInfo();
                AnnotationsAttribute attribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
                if (null != attribute) {
                    Annotation annotation = attribute.getAnnotation("com.simple.monitor.annotation.TraceMethod");
//                    System.out.println("方法annotation====" + annotation);
                    if (null != annotation) {
                        isMatched = true;
//                        System.out.println("方法annotation.getTypeName====" + annotation.getTypeName());
                        //监控点类型
                        String traceType = annotation.getMemberValue("traceType").toString();
                        //监控点是否跟踪参数
                        boolean traceParameter = annotation.getMemberValue("traceParameter") == null ?
                                false : Boolean.valueOf(annotation.getMemberValue("traceParameter").toString());
                        //是否捕获异常
                        String insertBefore = "{";
                        String include = annotation.getMemberValue("include") == null ? null : annotation.getMemberValue("include").toString();
                        String parameterTraceCode = "";
                        if (null != include) {
                            parameterTraceCode = getIncludeBeforeCode(cm, include);
                        } else if (traceParameter) {
                            //需要排除参数名称
                            String exclude = annotation.getMemberValue("exclude") == null ? null : annotation.getMemberValue("exclude").toString();
                            if (null != exclude) {
                                //删除前后的引号
                                include = exclude.substring(1, exclude.length() - 1);
                            }
                            parameterTraceCode = getExcludeBeforeCode(cm, exclude);
                            insertBefore += parameterTraceCode;
                        }
                        //下面这句话,在Web容器中无法修改注解所在的类的字节码(ClassLoader的问题,暂时未找到解决方案)
                        // TraceMethod traceMethod = (TraceMethod) annotation.toAnnotationType(loader, cp);
                        String methodName = cm.getName();
                        String methodParameterMap = traceParameter ? "methodParameterMap" : null;
                        insertBefore += "com.simple.monitor.support.Tracer.startTrace(this,"
                                + traceType + ",\"" + methodName + "\"," + methodParameterMap + ");";
                        insertBefore += "}";
//                        System.out.println(insertBefore);
                        cm.insertBefore(insertBefore);
                        //在每一个return语句前添加语句
                        cm.insertAfter("{com.simple.monitor.support.Tracer.endTrace(this);}");
//                        System.out.println("found method:" + cm.getName() + " traceMethod=" + annotation);
                    }

                }//end if
            }
            if(isMatched) {
                cc.writeFile();
                cc.detach();
                return cc.toBytecode();
            }
           return null;
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("loader=" + loader);
//        System.out.println("className=" + className);
//        System.out.println("protectionDomain=" + protectionDomain.getClass().getName());
//        System.out.println("classBeingRedefined=" + classBeingRedefined);
        return null;
        }
    }
