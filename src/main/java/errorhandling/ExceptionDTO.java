package errorhandling;

public class ExceptionDTO {

    public ExceptionDTO(int code, String description) {
        this.code = code;
        this.message = description;
    }

    public ExceptionDTO(int code, String description, String info) {
        this.code = code;
        this.message = description;
        this.info = info;
    }

    private int code;
    private String message;
    private String info;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getInfo() {
        return info;
    }

}
