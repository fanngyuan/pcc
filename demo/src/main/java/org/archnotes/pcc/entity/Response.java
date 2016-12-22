package org.archnotes.pcc.entity;

/**
 * Created by fanngyuan on 12/22/16.
 */
public class Response {

    public static final int SUCC=200;

    private int code;

    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
