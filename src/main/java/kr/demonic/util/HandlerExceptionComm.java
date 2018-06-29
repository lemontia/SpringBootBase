package kr.demonic.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception 핸들러.
 */
@ControllerAdvice(basePackages = "kr.demonic")
public class HandlerExceptionComm {


    @ExceptionHandler({SQLException.class, DataIntegrityViolationException.class})
    @ResponseBody
    public Map handlerSQLException(SQLException e){
        System.out.println("=============================================== handlerSQLException");
        Map result = new HashMap();
        result.put("code", "9999");
        result.put("message", "Data error");
        return result;
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    @ResponseBody
//    public Map handlerDataIntegrityViolationException(Exception e){
//        System.out.println("=============================== DataIntegrityViolationException.class");
//        e.printStackTrace();
//        Map result = new HashMap();
//        result.put("code", "9999");
//        result.put("message", "Data error");
//        return result;
//    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map handlerException(Exception e){
        e.printStackTrace();
        Map result = new HashMap();
        result.put("code", "9999");
        result.put("message", e.getMessage());
        return result;
    }
}
