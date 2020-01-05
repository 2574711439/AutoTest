package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

public class FirstHttpClient {

    @Test
    public void test1() throws IOException {

        String result = "";//用来存放结果
        HttpGet get = new HttpGet("http://wwww.baidu.com");
        DefaultHttpClient client = new DefaultHttpClient();//用来执行get请求
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}
