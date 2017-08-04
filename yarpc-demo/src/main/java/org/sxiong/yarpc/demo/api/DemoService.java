package org.sxiong.yarpc.demo.api;

/**
 * Created by sxiong on 7/29/17.
 */
public interface DemoService {
    String echo(String input);
    MessageDTO loadObject(int messageId);
}
