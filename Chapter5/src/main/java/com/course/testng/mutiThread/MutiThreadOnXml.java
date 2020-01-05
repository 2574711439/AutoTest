package com.course.testng.mutiThread;

import org.testng.annotations.Test;

public class MutiThreadOnXml {

    @Test
    public void test1(){
        System.out.printf("Thread test1 Id: %s%n",Thread.currentThread().getId());
    }

    @Test
    public void test2(){
        System.out.printf("Thread test2 Id: %s%n",Thread.currentThread().getId());
    }

    @Test
    public void test3(){
        System.out.printf("Thread test3 Id: %s%n",Thread.currentThread().getId());
    }
}
