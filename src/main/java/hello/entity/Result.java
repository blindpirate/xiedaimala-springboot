package hello.entity;

public abstract class Result<T> {
    public enum ResultStatus {
        OK("ok"),
        FAIL("fail");

        private String status;

        ResultStatus(String status) {
            this.status = status;
        }
    }

    ResultStatus status;
    String msg;
    T data;


    protected Result(ResultStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status.status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
