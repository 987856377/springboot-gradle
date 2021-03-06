package com.spring.development.common.exception;

import com.spring.development.common.ResultCode;
import com.spring.development.common.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalException {

    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;


    /**
     * 处理空指针的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultJson exceptionHandler(HttpServletRequest request, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return ResultJson.failure(ResultCode.INTERNAL_SERVER_ERROR, "发生空指针异常");
    }

    /**
     * SQL 执行异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = SQLException.class)
    public ResultJson sqlExceptionHandler(HttpServletRequest request, SQLException e){
        logger.error("SQL 执行异常！原因是:",e);
        return ResultJson.failure(ResultCode.INTERNAL_SERVER_ERROR,"SQL 执行异常");
    }

    /**
     * 文件过大异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultJson uploadSizeExceptionHandler(HttpServletRequest request, MaxUploadSizeExceededException e){
        logger.error("文件过大异常！原因是:",e);
        return ResultJson.failure(ResultCode.REQUEST_ENTITY_TOO_LARGE,"文件过大,仅支持 " + maxFileSize + " 大小的文件");
    }


    /**
     * 处理其他异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultJson exceptionHandler(HttpServletRequest request, Exception e){
        logger.error("未知异常！原因是:",e);
        return ResultJson.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }
}