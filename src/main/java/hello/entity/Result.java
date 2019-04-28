package hello.entity;

public class Result {
    String status;
    String msg;
    boolean isLogin;
    Object data;

    public static Result failure(String message) {
        return new Result("fail", message, false);
    }

//        public static Result success() {
//
//        }

    public Result(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public Result(String status, String msg, boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public Object getData() {
        return data;
    }
}
