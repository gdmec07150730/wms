package com.wms.util;

import java.lang.reflect.Method;

public abstract class PermissionUtil {
    public static String buildExpression(Method m){
        return m.getDeclaringClass().getName()+":"+m.getName();
    }
}
