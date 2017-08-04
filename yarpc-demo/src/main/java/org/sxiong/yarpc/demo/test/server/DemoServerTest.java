package org.sxiong.yarpc.demo.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sxiong on 7/30/17.
 */
public class DemoServerTest {
    private static final Logger logger = LoggerFactory.getLogger(DemoServerTest.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("demoServer start");
        new ClassPathXmlApplicationContext("classpath*:config/spring/applicationContext-server.xml");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
