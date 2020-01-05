package com.course.model;

import lombok.Data;

@Data
public class AddUserCase {
    private String id;
    private String userName;
    private String password;
    private String sex;
    private int age;
    private String permission;
    private String isDelete;
    private String expected; //期望结果，是否添加成功
}
