package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = {"loginTrue"}, description = "测试准备工作，获取httpClient对象")
    public void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.getUserListInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }

    @Test(groups = {"loginTrue"}, description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase", 1);
        String result = getResult(loginCase);
        //从loginCase表中查出的第一条数据字段为Expected的值 是否和接口返回的结果一致
        Assert.assertEquals(loginCase.getExpected(), result);
    }

    @Test(groups = {"loginFalse"}, description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase", 2);
        String result = getResult(loginCase);
        //从loginCase表中查出的第2条数据字段为Expected的值 是否和接口返回的结果一致
        Assert.assertEquals(loginCase.getExpected(), result);
    }

    /**
     * 从loginCase表中查出id=1的一条数据
     * 通过UserName和passWord访问接口得到返回结果"true"和cookies
     * 将cookies存储进TestConfig.store中
     * @param loginCase
     * @return
     */
    private String getResult(LoginCase loginCase) throws IOException {
        JSONObject param = new JSONObject();
        param.put("userName", loginCase.getUserName());
        param.put("password", loginCase.getPassword());

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        post.setEntity(entity);
        post.setHeader("content-type", "application/json");
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();

        String result = "";
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        return result;
    }
}
