import com.hnair.consumer.monitor.support.Tracer;

import java.io.InputStream;

/**
 * Created by simple on 2017/4/24 13:49
 *
 * @mail xiang.lv@hnair.com
 * @since 1.0
 */
public class ResourcesTester {
    public static void main(String[] args) {
            InputStream is =  Tracer.class.getClassLoader().getResourceAsStream("monitor.properties");
            if (is == null) {
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream("monitor.properties");
            }
            if (is == null) {
                is =  ClassLoader.getSystemResourceAsStream("monitor.properties");
            }
            System.out.println("is="+is);
    }
}
