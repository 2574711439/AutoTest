package com.course.cases;

import com.course.config.TestConfig;
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
import java.util.List;

public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue", description = "查询多个用户列表")
    public void getUserInfoListTest() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", 1);

        JSONArray jsonResult = getJsonResult(getUserListCase);
        //用getUserListCase表的第一条数据的expected字段值"getUserList"作为sql名字，去User表中查询出3条相相同数据
        List<User> userList = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        JSONArray jsonUserList = new JSONArray(userList);

        //比较这3条数据是否一致
        Assert.assertEquals(jsonUserList.length(), jsonResult.length());
        for(int i = 0 ;i < jsonResult.length(); i++){
            JSONObject expect = (JSONObject) jsonResult.get(i);
            JSONObject actual = (JSONObject) jsonUserList.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }
    }

    /**
     * 查询getUserListCase表的第一条数据,得到3条jsonArray数据
     * @param getUserListCase
     * @return
     */
    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("age",  getUserListCase.getAge());
        param.put("sex", getUserListCase.getSex());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");

        HttpPost post = new HttpPost(TestConfig.getUserListInfoUrl);
        post.setHeader("content-type", "application/json");
        post.setEntity(entity);
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        String result = EntityUtils.toString(response.getEntity());
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }
}
