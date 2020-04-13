package com.example.musicdemo.interceptor;

import com.example.musicdemo.enums.ResultEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute(Constant.CURRENT_USER) == null){
            response.setStatus(ResultEnum.LOGIN_ERROR_ALL.getCode());
            return false;
        }else{
            return true;
        }
    }

}
