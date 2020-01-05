package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPostOne {

    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String testUrl = this.url + bundle.getString("getCookies.url");
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        client.execute(get);
        store = client.getCookieStore();
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostMethod() throws IOException {
        //输入访问路径
        String testUrl = this.url + bundle.getString("test.post.withCookie");
        //声明post方法,传入路径,设置头信息
        HttpPost post = new HttpPost(testUrl);
        post.setHeader("content-type","application/json");
        //传入json参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","huhansan");
        jsonObject.put("age","18");
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies信息
        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(this.store);
        //执行访问
        HttpResponse response = client.execute(post);

        //断言结果
        String testResult = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject object = new JSONObject(testResult);
        String success = (String) object.get("huhansan");
        String status = (String) object.get("status");
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);
    }
}
