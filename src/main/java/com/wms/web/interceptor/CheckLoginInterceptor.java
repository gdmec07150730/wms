package com.wms.web.interceptor;

import com.wms.domain.Employee;
import com.wms.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Employee user = UserContext.getCurrentUser();

        if (user != null) {
            return true;
        }
        response.sendRedirect("/login.html");
        return false;
    }
}
