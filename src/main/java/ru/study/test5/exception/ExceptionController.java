package ru.study.test5.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(UniqueException.class)
  public ResponseEntity<String> exception(UniqueException ex) {
    log.error(ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(NotEmptyException.class)
  public ResponseEntity<String> exception(NotEmptyException ex) {
    log.error(ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(DoubleException.class)
  public ResponseEntity<String> exception(DoubleException ex) {
    log.error(ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<String> exception(NoDataFoundException ex) {
    log.error(ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}
