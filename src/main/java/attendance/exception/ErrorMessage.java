package attendance.exception;

public enum ErrorMessage {

    ERROR_SIGNATURE("[ERROR] "),
    NOT_FOUND_FILE("존재하지 않는 파일 입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return ERROR_SIGNATURE.message + this.message;
    }
}
