package org.sxiong.yarpc.demo.api;

import java.io.Serializable;

/**
 * Created by sxiong on 7/30/17.
 */
public class MessageDTO implements Serializable {
    private int messageId;
    private String content;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("MessageDTO{");
        sb.append("messageId=").append(messageId);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
