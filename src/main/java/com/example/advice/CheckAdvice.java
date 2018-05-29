package com.example.advice;

import com.example.exceptions.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * ==========================================
 * 异常处理切面
 * User: 小破天
 * Date: 2018-05-30
 * Time: 0:19
 * 博客园：http://www.cnblogs.com/xiaopotian/
 * ===========================================
 */
@ControllerAdvice
@RestControllerAdvice
public class CheckAdvice {
    /**
     * 捕获框架异常
     * @param e
     * @return
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handlerBindException(WebExchangeBindException e) {
        return new ResponseEntity<>(toStr(e),HttpStatus.BAD_REQUEST);
    }

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleCheckException(
            CheckException e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    private String toStr(CheckException e) {
        return e.getFieldName() + ": 错误的值 " + e.getFieldValue();
    }

    /**
     * 把校验异常转换为字符串
     *
     * @param ex
     * @return
     */
    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }
}
