package pl.cm.core.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = CommonException.class)
    public ResponseEntity<ErrorInfo> handleException(CommonException e) {
        return ResponseEntity.status(e.getType().getCode()).body(new ErrorInfo(e.getType().getMessage()));
    }
}
