package com.course.server;

import com.course.bean.user;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@Api(value = "/", description = "这是我全部的post方法")
@RequestMapping("/v1")
public class MyPostMethod {
    //这个变量是用来装我们cookies信息的
    private static Cookie cookie;

    //场景：用户登录成功获取到cookies，然后再访问其他接口获取列表

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "passWord", required = true) String password){
        if (userName.equals("xiaoli") && password.equals("123")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功了";
        }
        return "用户名或者是密码错误";
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request, @RequestBody user user){
        user u;
        String s = "获取用户列表失败";
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        if (Objects.isNull(cookies)){
            return s;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && user.getName().equals("zhangsan")){
                //验证通过，返回用户列表
                u = new user();
                u.setName("lisi");
                u.setAge("15");
                u.setSex("woman");
                return u.toString();
            }
        }
        return s;
    }
}
