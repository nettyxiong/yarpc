package org.sxiong.yarpc.demo.impl;

import org.sxiong.yarpc.demo.api.DemoService;
import org.sxiong.yarpc.demo.api.MessageDTO;

/**
 * Created by sxiong on 7/29/17.
 */
public class DemoServiceImpl implements DemoService {
    public String echo(String input) {
        return "echo:" + input;
    }

    public MessageDTO loadObject(int messageId) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(1);
        messageDTO.setContent("sxiong");
        return messageDTO;
    }
}
