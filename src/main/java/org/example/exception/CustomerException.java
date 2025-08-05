//package org.example.exception;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//
///*
//    自定义异常
//    运行时异常
// */
//
//public class CustomerException extends RuntimeException {
//    private String code;
//    private String message;
//
//    public CustomerException(String code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public CustomerException(String message) {
//        this.code =  "500";
//        this.message = message;
//    }
//
//    public CustomerException() {}
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
