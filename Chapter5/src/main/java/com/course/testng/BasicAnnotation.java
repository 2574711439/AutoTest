package com.course.testng;

import org.testng.annotations.*;

public class BasicAnnotation {

    //最基本的注解，用来把方法标记为测试的一部分
    @Test
    public void testCase1(){
        System.out.println("这是测试用例1");
        System.out.printf("Thread testCase1 Id: %s%n",Thread.currentThread().getId());
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("这是在测试方法之前运行");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("这是在测试方法之后运行");
    }

    @BeforeClass
    public void beforeVlass(){
        System.out.println("这是在类运行之前的执行方法");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("这是在类运行之后的执行方法");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("在测试套件之前运行");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("在测试套件之运行");
    }
}
