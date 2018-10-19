package com.cetc.datasynch.model;

/**
 * 认证令牌
 * Created by llj on 2018/10/14.
 */
public class Token {
    String key;
    String value;

    public Token(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
