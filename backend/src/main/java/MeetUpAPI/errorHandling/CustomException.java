package MeetUpAPI.errorHandling;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private final String message;
  private final HttpStatus httpStatus;

  public CustomException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String toString() {
    return "{" +
            "\"status\": " + httpStatus.value() +
            "," +
            "\"message\": \"" + message + "\""+
            "}";

  }
}
