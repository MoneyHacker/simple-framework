package com.simple.monitor.spi;

/**
 * Created by simple on 2017/4/14 15:39
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
public enum TraceTypeEnum implements java.io.Serializable {
    API((byte)1,"api"),
    Service((byte)2,"Service"),
    Dao((byte)3,"Dao"),
    Cache((byte)4,"Cache"),
    Rpc((byte)5,"RPC");
    private final Byte typeId;
    private final String typeName;
    private TraceTypeEnum(Byte typeId, String typeName){
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
