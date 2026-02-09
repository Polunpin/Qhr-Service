package com.tencent.config;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 统一异常出口，保证错误码策略一致
  @ExceptionHandler(ApiException.class)
  public ApiResponse handleApiException(ApiException ex) {
    return ApiResponse.error(ex.getCode(), ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ApiResponse handleIllegalArgument(IllegalArgumentException ex) {
    return ApiResponse.error(ApiCode.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponse handleValidation(MethodArgumentNotValidException ex) {
    return ApiResponse.error(ApiCode.BAD_REQUEST, "参数校验失败");
  }

  @ExceptionHandler(Exception.class)
  public ApiResponse handleException(Exception ex) {
    return ApiResponse.error(ApiCode.INTERNAL_ERROR, "服务器异常");
  }
}
