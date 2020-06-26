package MeetUpAPI.errorHandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;


@RestControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private HttpHeaders headers;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        HttpStatus httpStatus;

        if(ex instanceof CustomException)
            return new ResponseEntity<>(ex.toString(), headers, ((CustomException) ex).getHttpStatus());

        if(ex instanceof MethodArgumentNotValidException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            List<String> messages = new LinkedList<>();
            List<FieldError> violations = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            violations.forEach(fieldError -> messages.add(fieldError.getDefaultMessage()));
            return new ResponseEntity<>(new CustomException(messages.toString().replace("[", "").replace("]", ""), httpStatus).toString(), headers, httpStatus);
        }

        if(ex instanceof ConstraintViolationException) {
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
            return new ResponseEntity<>(new CustomException("ConstraintViolation: Invalid input", httpStatus).toString(), headers, httpStatus);
        }


        if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            return new ResponseEntity<>(new CustomException(ex.getMessage(), httpStatus).toString(), headers, httpStatus);
        }

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();
        return new ResponseEntity<>(new CustomException(ex.getMessage(), httpStatus).toString(), headers, httpStatus);
    }
}