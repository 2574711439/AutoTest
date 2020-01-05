package com.course.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("before Suite运行啦");
    }

    @AfterSuite
    public void afterSuie(){
        System.out.println("after Suie运行啦");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("beforeTest运行啦");
    }

    @AfterTest
    public void aferTest(){
        System.out.println("aferTest运行啦");
    }
}
