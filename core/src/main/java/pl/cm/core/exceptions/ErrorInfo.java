package pl.cm.core.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ErrorInfo {

    @Getter
    @Setter
    private String message;

    public ErrorInfo(String message) {
        this.message = message;
    }

}
