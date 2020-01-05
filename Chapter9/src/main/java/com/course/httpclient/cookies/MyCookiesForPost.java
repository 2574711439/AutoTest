package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {

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
    public void testPostMethod() throws IOException {
        String testUrl = this.url + bundle.getString("test.post.withCookie");

        //声明一个client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(testUrl);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","huhansan");
        param.put("age","18");
        //设置请求头信息
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //设置cookies信息
        client.setCookieStore(this.store);
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        //处理结果，就是判断返回结果是否符合预期
        JSONObject resultJson = new JSONObject(result);
        String success = (String)resultJson.get("huhansan");
        String status = (String)resultJson.get("status");
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}
