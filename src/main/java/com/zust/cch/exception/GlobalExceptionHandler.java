package com.zust.cch.exception;

import com.zust.cch.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 1. 拦截参数校验异常 (配合 DTO 里的 @NotBlank, @Size 等使用)
     * 当 Controller 里的 @Validated 校验失败时，会进入这个方法
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        log.warn("参数校验失败: {}", errorMsg);
        return Result.error(400, errorMsg); // 400 代表客户端请求参数错误
    }


    /**
     * 2. 拦截我们自定义的业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 3. 兜底拦截：处理所有未知的系统异常 (比如空指针、数据库连接失败等)
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        // 这种属于未知系统 Bug，必须打印完整的错误堆栈到控制台，方便排查
        log.error("系统内部异常: ", e);
        return Result.error(500, "服务器内部开小差了，请联系管理员");
    }
}
