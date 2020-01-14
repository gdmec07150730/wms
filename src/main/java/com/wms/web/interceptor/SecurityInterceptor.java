package com.wms.web.interceptor;

import com.wms.annotation.RequiredPermission;
import com.wms.domain.Employee;
import com.wms.exception.SystemException;
import com.wms.util.PermissionUtil;
import com.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取当前用户
        Employee user = UserContext.getCurrentUser();

        //如果用户是超级管理员 放行
        if (user.getAdmin()){
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        Method m = hm.getMethod();
        //如果用户没有权限限制,放行
        if (!m.isAnnotationPresent(RequiredPermission.class)){
            return true;
        }

        String exp = PermissionUtil.buildExpression(m);
        List<String> exps = UserContext.getCurrentPerms();
        //如果用户拥有权限,放行
        if (exps.contains(exp)){
            return true;
        }
       throw new SystemException("没有权限");
    }
}
