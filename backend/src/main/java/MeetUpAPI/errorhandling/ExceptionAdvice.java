package MeetUpAPI.errorhandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        HttpStatus httpStatus;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(ex instanceof CustomException){
            return new ResponseEntity<>(ex.toString(), headers, ((CustomException) ex).getHttpStatus());
        }
        if(ex instanceof HttpRequestMethodNotSupportedException){
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            return new ResponseEntity<>(new CustomException(ex.getMessage(), httpStatus).toString(), headers, httpStatus);
        }

        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();
        return new ResponseEntity<>(new CustomException(ex.getMessage(), httpStatus).toString(), headers, httpStatus);
    }
}