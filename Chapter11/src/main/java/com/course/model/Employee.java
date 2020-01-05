package com.course.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Employee {
    private int empno;

    private String ename;

    private String job;

    private int mgr;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiredate;

    private double sal;

    private int deptnu;
}
