package com.challengerstory.chattingstory.common.exception;

import com.challengerstory.chattingstory.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;



@Slf4j
//필기. 해당 패키지에서 에러 발생시 작동하는 Handler
@RestControllerAdvice(basePackages = "com.challengerstory.chattingstory", name = "restControllerAdvice")
public class GlobalExceptionHandler {

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseDTO<?> handleNoPageFoundException(Exception e) {
        log.error("handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}"
                , e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.WRONG_ENTRY_POINT));
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}"
                , e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}"
                , e.getMessage());
        return ResponseDTO.fail(e);
    }

    // httpstatus를 우리 고유 exception의 httpstatus와 동적으로 맞추기 위해 responseEntity로 바꿔서 반환하도록 수정
    @ExceptionHandler(value = {CommonException.class})
    public ResponseDTO<?> handleCustomException(CommonException e) {
        log.error("handleCustomException() in GlobalExceptionHandler: {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseDTO<?> handleServerException(Exception e) {
        log.info("occurred exception in handleServerError = {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseDTO<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("handleDataIntegrityViolationException() in GlobalExceptionHandler : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.DATA_INTEGRITY_VIOLATION));
    }


}