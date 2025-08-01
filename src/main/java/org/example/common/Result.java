package org.example.common;
//统一返回包装类
//作用：统一后端返回的数据类型。code作为前端判断请求成功的依据
public class Result {
    private String code;
    private Object data;
    private String msg;

    //有返回数据的成功情况
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        result.setMsg("success");
        return result;
    }
    //无返回数据的成功情况
    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("success");
        return result;
    }
    //统一的失败情况
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }
    //自定义的失败情况
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
