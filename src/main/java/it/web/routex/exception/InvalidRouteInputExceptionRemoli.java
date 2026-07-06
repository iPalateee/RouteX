package it.web.routex.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class InvalidRouteInputExceptionRemoli extends Exception {

  private final String field;
  private final LocalDateTime timestamp;

  public InvalidRouteInputExceptionRemoli(String field, String message) {
    super(message);
    this.field = field;
    this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
  }

  public LocalDateTime getTimestamp() { return timestamp; }

  @Override
  public String toString() {
    return "InvalidRouteInputExceptionRemoli{" +
            "field='" + field + '\'' +
            ", message='" + getMessage() + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }
}
