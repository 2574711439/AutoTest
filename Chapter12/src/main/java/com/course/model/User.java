package com.course.model;

import lombok.Data;

/**
 * 用户信息管理系统的基础表，存储用户信息
 */
@Data
public class User {
    private String id;
    private String userName;
    private String password;
    private int age;
    private String sex;
    private String permission;
    private String isDelete;

    @Override
    public String toString() {
        return(
                '{'+
                "id=" + id + ',' +
                "userName=" + userName + ',' +
                "password=" + password + ',' +
                "age=" + age + ',' +
                "sex=" + sex + ',' +
                "permission=" + permission + ',' +
                "isDelete=" + isDelete
                 + '}'
        );
    }
}
