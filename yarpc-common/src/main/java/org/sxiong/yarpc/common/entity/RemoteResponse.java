package org.sxiong.yarpc.common.entity;

/**
 * Created by sxiong on 7/27/17.
 */
public class RemoteResponse {
    private String requestId;
    private int responseCode;
    private Object responseValue;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(Object responseValue) {
        this.responseValue = responseValue;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("RemoteResponse{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", responseCode=").append(responseCode);
        sb.append(", responseValue=").append(responseValue);
        sb.append('}');
        return sb.toString();
    }
}
