package com.volunteer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private Integer code;

    private String msg;

    private Object data;

    public static Result ok(Object data) {
        return new Result(0, null, data);
    }

    public static Result ok() {
        return new Result(0, null, null);
    }

    public static Result fail(String msg) {
        return new Result(1, msg, null);
    }
}
