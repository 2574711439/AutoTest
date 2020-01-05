package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取单个用户信息")
    public void getUserInfoTest() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        //查询user表中id=1的数据
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        List userList = new ArrayList<User>();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);

        Assert.assertEquals(jsonArray.toString(), resultJson.toString());//断言2条数据
    }

    /**
     * 从getUserInfoCase表中查出第一条数据，取userId=1作为user表的Id=1
     * 返回user表中id=1的数据
     * @param getUserInfoCase
     * @return
     */
    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        JSONObject param = new JSONObject();
        param.put("userId", getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");

        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        post.setEntity(entity);
        post.setHeader("content-type", "application/json");
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        List<String> resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);
        return jsonArray;
    }
}
