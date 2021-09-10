package com.dakai.readfile;

import com.dakai.readfile.webshell.utils.ThreadPoolUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadFileApplication {

    public static void main(String[] args) {

        // log4j2全局异步日志配置 http://logging.apache.org/log4j/2.x/manual/async.html#AllAsync
        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        SpringApplication.run(ReadFileApplication.class, args);

        // 停止应用时，关闭线程池钩子，或者使用 @PreDestroy 注解执行一系列操作
        Runtime.getRuntime().addShutdownHook(new Thread(ThreadPoolUtils::shutdown, "ShutdownThreadPoolHook"));
    }

}
