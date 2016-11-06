package com.tutal.ahmet.springboot.jpa;

import java.io.Serializable;

/**
 * Created by tutal on 04.11.2016.
 */

public class Result implements Serializable {

    String code;
    String message;

    public Result() {
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
