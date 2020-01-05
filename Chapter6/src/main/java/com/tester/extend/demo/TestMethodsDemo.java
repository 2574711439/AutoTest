package com.tester.extend.demo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodsDemo {

    @Test
    public void test1(){
        Assert.assertEquals("name","lisa");//第二个值是预期的结果
    }

    @Test
    public void longDemo(){
        Reporter.log("记录日志的方法");
        throw new RuntimeException("运行时异常");
    }
}
