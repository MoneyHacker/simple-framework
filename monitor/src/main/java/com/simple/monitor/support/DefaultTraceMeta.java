package com.simple.monitor.support;


import com.simple.monitor.spi.TraceMeta;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by simple on 2017/4/14 16:59
 *
 * @mail  moneyhacker@163.com
 * @since 1.0
 */
 @Slf4j
 public class DefaultTraceMeta extends AbstractTraceMeta {

    @Override
    public void startTrace(final TraceMeta traceMeta){
        log.info("startTrace:traceMeta={}",traceMeta);
        System.out.println("startTrace:traceMeta="+traceMeta);


    }

    @Override
    public void endTrace(final TraceMeta traceMeta){
        log.info("endTrace:traceMeta={}",traceMeta);
        System.out.println("endTrace:traceMeta="+traceMeta);

    }
}
