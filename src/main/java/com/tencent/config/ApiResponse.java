package com.tencent.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

    private Integer code;
    private String errorMsg;
    private Object data;

    private ApiResponse(int code, String errorMsg, Object data) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static ApiResponse ok() {
        return new ApiResponse(ApiCode.OK, "", new HashMap<>());
    }

    public static ApiResponse ok(Object data) {
        return new ApiResponse(ApiCode.OK, "", data);
    }

    public static ApiResponse error(String errorMsg) {
        return new ApiResponse(ApiCode.INTERNAL_ERROR, errorMsg, new HashMap<>());
    }

    public static ApiResponse error(int code, String errorMsg) {
        return new ApiResponse(code, errorMsg, new HashMap<>());
    }
}
