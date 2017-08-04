package org.sxiong.yarpc.common.serialize;

/**
 * Created by sxiong on 7/27/17.
 */
public enum SerializeType {
    HESSIAN(1),JAVA(2);

    private int value;

    SerializeType(int value){
        this.value = value;
    }

    public String toString(){
        return String.valueOf(this.value);
    }

    public Integer getValue(){
        return this.value;
    }
}
