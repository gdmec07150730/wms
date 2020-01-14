package com.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter
public class Employee extends BaseDomain {
    private Long id;

    private String name;

    private String password;

    private String email;

    private Integer age;

    private Boolean admin = false;

    private Department dept;

    List<Role> roles = new ArrayList<>();

    public String getRoleNames(){
        if (roles.size() == 0 ){
            return "[未分配角色]";
        }
        if (admin != null && admin){
            return "[超级管理员]";
        }

        StringBuilder sb = new StringBuilder(80);
        sb.append("[");
        for (Role role : roles) {
            sb.append(role.getName()).append(",");
        }
        sb.setCharAt(sb.length()-1,']');
        return sb.toString();

    }


}