package pl.cm.core.exceptions;

public enum ExceptionType {

    BAD_REQUEST(400, "Błąd systemu"),
    NOT_FOUND(404, "Rekord nie istnieje");

    private int code;
    private String message;

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
