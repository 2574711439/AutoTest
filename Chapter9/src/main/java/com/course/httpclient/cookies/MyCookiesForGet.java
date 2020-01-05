package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    //用来存储cookie信息的私有变量
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        //用getBundle方法读取propertites文件
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        this.url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        //从配置文件中拼接测试的url
        String testUrl = this.url + bundle.getString("getCookies.url");
        HttpGet get = new HttpGet(testUrl);

        //测试逻辑代码书写
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        //System.out.println(result);

        //获取cookies信息
        this.store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for(Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            //System.out.println(name+":"+value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String testUrl = this.url + bundle.getString("test.get.cookie");
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();

        client.setCookieStore(this.store);//设置cookie信息

        HttpResponse response = client.execute(get);

        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);

        if (statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
