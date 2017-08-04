package org.sxiong.yarpc.common.entity;

/**
 * Created by sxiong on 7/28/17.
 */
public class ProviderInfo {
    private String address;
    private int port;

    public ProviderInfo(String address,int port){
        this.address = address;
        this.port = port;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object object){
        if (object==this)return true;
        if (object==null||getClass()!=object.getClass())return false;
        ProviderInfo o = (ProviderInfo) object;

        if (port!=o.getPort())return false;
        if (!address.equals(o.getAddress()))return false;

        return true;
    }

    @Override
    public int hashCode(){
        int hash = address.hashCode();
        return hash*31+port;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("ProviderIndo{");
        sb.append("address='").append(address).append("\'");
        sb.append("port=").append(port);
        sb.append("}");
        return sb.toString();
    }
}
