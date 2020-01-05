package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
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

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口")
    public void addUserTest() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase", 1);

        String result = getResult(addUserCase);
        //从addUserCase表中查出第一条数据再去user表中查询得到一条数据
        User user = sqlSession.selectOne("addUser",addUserCase);

        //用从addUserCase表中查出的第一条数据的expected字段值和接口响应值做断言
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    /**
     * 通过在addUserCase表中查出第一条数据和在TestConfig的cookies访问/v1/addUser接口
     * 得到响应结果"true"
     * @param addUserCase
     * @return String
     * @throws IOException
     */
    private String getResult(AddUserCase addUserCase) throws IOException {
        JSONObject param = new JSONObject();
        //使用雪花算法生成id
        //param.put("id", String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId()));
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("age", addUserCase.getAge());
        param.put("sex", addUserCase.getSex());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());

        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        post.setEntity(entity);
        post.setHeader("content-type", "application/json");
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = "";
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        return result;
    }

}
