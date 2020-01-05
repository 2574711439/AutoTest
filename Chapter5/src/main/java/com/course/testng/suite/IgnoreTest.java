package com.course.testng.suite;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.testng.annotations.Test;

public class IgnoreTest {

    @Test
    public void ignore1(){
        System.out.println("ignore1执行");
    }
    @Test(enabled = false)
    public void ignore2(){
        System.out.println("ignore2执行");
    }
}
