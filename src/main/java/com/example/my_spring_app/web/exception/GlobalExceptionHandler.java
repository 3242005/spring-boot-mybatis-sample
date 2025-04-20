package com.example.my_spring_app.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 一般的な例外ハンドラー - 1つに統合
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, WebRequest request) {
        // Accept ヘッダーをチェック
        String acceptHeader = request.getHeader("Accept");
        boolean isJsonRequest = acceptHeader != null && acceptHeader.contains("application/json");
        
        if (isJsonRequest) {
            // JSONレスポンス用
            ErrorResponse errorResponse = new ErrorResponse("ERROR", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        } else {
            // HTML用 - ModelAndViewを使用
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("errorMessage", ex.getMessage());
            return mav;
        }
    }

    // HTTPメソッドエラー用のハンドラー
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        // Accept ヘッダーをチェック
        String acceptHeader = request.getHeader("Accept");
        boolean isJsonRequest = acceptHeader != null && acceptHeader.contains("application/json");
        
        if (isJsonRequest) {
            ErrorResponse errorResponse = new ErrorResponse("METHOD_ERROR", "リクエストメソッドがサポートされていません: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("errorMessage", "リクエストメソッドがサポートされていません: " + ex.getMessage());
            return mav;
        }
    }

    // エラーレスポンスクラス
    static class ErrorResponse {
        private String code;
        private String message;
        
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
        
        // getters and setters
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
}