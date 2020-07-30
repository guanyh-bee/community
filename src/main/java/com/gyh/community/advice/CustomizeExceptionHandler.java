package com.gyh.community.advice;

import com.alibaba.fastjson.JSON;
import com.gyh.community.Exception.CustomizeErrorCode;
import com.gyh.community.Exception.CustomizeException;
import com.gyh.community.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author gyh
 * @create 2020-07-29 16:57
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, HttpServletResponse response ,Throwable ex, Model model) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        ResultDTO resultDTO = null;
        if("application/json".equals(contentType)){
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            if(ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);

            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERR);

            }
            try {
                PrintWriter writer = response.getWriter();
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                response.setContentType("application/json");
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            if(ex instanceof CustomizeException){
                model.addAttribute("msg",ex.getMessage());
            }else {
                model.addAttribute("msg",CustomizeErrorCode.SYS_ERR.getMessage());

            }
            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
