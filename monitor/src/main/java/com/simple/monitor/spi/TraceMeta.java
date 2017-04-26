package com.simple.monitor.spi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * Created by simple on 2017/4/14 15:30
 * 监控点信息
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class TraceMeta implements java.io.Serializable{
    private static final long serialVersionUID = -7713952059039568862L;
    private Byte order;
    private Long startTime;
    private Long endTime;
    private String monitoredClassName;
    private String medthodName;
    private Integer traceId;
    private Integer parentId;
    private String type;
    private String threadName;
    private Map<String,Object> parameterMap;
    //记录调用堆栈信息
    private Throwable throwable;
    private String exceptionMsg;
    public void setThrowable(Throwable throwable){
        this.throwable = throwable;
        StackTraceElement[] stackTraceElements =  throwable.getStackTrace();
        StringBuilder sbExceptionMsg =  new StringBuilder();
        for (StackTraceElement ste:stackTraceElements ){
            sbExceptionMsg.append(ste);
        }
        this.exceptionMsg = sbExceptionMsg.toString();
    }
    public Byte getOrder(){
        //通过traceId来判断调用先后时序
        return null == this.traceId ? null:(byte)String.valueOf(this.traceId).length();
    }
}
