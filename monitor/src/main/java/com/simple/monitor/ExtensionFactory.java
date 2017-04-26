package com.simple.monitor;

import com.simple.monitor.support.Tracer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by simple on 2017/4/24 13:54
 *
 * @mail moneyhacker@163.com
 * @since 1.0
 */
@Slf4j
public class ExtensionFactory {
    private  final static Map <String,Object> extensionMap = new HashMap<>();
    private  final static  String MONITOR_PROPERTIES_NAME = "monitor.properties";
    private final static AtomicBoolean started = new AtomicBoolean(false);
    /**
     * 用户自定义的扩展点实现
     * @return
     */
    private static Properties getExtensionProperties(){
        Properties pp = new Properties();
        //先把默认的放进去
        InputStream is =  Tracer.class.getClassLoader().getResourceAsStream(MONITOR_PROPERTIES_NAME);
        if (is == null) {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(MONITOR_PROPERTIES_NAME);
        }
        if (is == null) {
            is =  ClassLoader.getSystemResourceAsStream(MONITOR_PROPERTIES_NAME);
        }
        try {
            pp.load(is);
        } catch (IOException e) {
           log.info("找不到配置文件monitor.properties,e={}",e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //do nothing
            }
        }
        return pp;
    }

    /**
     * 加载扩展点,实例化并缓存
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void initExtension() throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        Properties pp = getExtensionProperties();
        if (null != pp) {
            for (Map.Entry<Object, Object> entry : pp.entrySet()) {
                extensionMap.put(entry.getKey().toString(), Class.forName(entry.getValue().toString()).newInstance());
            }
        }
        //加入默认的扩展点实现(如果用户没有重新定义)
       ExtensionPointEnum[] defaultExtension = ExtensionPointEnum.values();
       for (int i = 0,len = defaultExtension.length ; i < len ; i++ ) {
           log.info("加载扩展点:"+defaultExtension[i].name()+"="+defaultExtension[i].getDefaultClassName());
           extensionMap.putIfAbsent(defaultExtension[i].name(), Class.forName(defaultExtension[i].getDefaultClassName()).newInstance());
       }
    }

   public static  <T> T  getExtension(final  String  extensionPointNName) {
       if (!started.getAndSet(true)) {
           try {
               initExtension();
           } catch (Exception e) {
              throw new RuntimeException(e);
           }
       }
       return (T) extensionMap.get(extensionPointNName);
   }

}
