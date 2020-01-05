package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import com.course.utils.SnowFlakeUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue", description = "更新用户信息")
    public void updateUserInfoTest() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 1);

        int result = getResult(updateUserInfoCase);
        //通过updateUserInfoCase表中查询的id=1数据,再去查询user表中是否有对应的用户信息
        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    @Test(dependsOnGroups = "loginTrue", description = "删除单个用户信息")
    public void deleteUserInfoTest() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 2);

        int result = getResult(updateUserInfoCase);
        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);

        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    /**
     * 通过updateUserInfoCase表中查询的id=1数据 去访问接口/v1/updateUserInfo得到响应数据“123”
     * @param updateUserInfoCase
     * @return
     */
    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        JSONObject param = new JSONObject();
        //param.put("id", String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId()));
        param.put("userName", updateUserInfoCase.getUserName());
        param.put("sex", updateUserInfoCase.getSex());
        /*param.put("age", updateUserInfoCase.getAge());
        param.put("permission", updateUserInfoCase.getPermission());
        param.put("isDelete", updateUserInfoCase.getIsDelete());*/
        StringEntity entity = new StringEntity(param.toString(), "utf-8");

        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        post.setEntity(entity);
        post.setHeader("content-type", "application/json");

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        return Integer.parseInt(result);
    }
}
