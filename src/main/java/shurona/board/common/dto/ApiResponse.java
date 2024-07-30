package shurona.board.common.dto;

public class ApiResponse<T> {

    private int code;
    private T data;
    private String message;

    public ApiResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static <T> ApiResponse<T> success(T data, int statusCode) {
        return new ApiResponse<>(statusCode, data, null);
    }


    public static <T> ApiResponse<T> fail(int statusCode, String message) {
        return new ApiResponse<>(statusCode, null, message);
    }
}
