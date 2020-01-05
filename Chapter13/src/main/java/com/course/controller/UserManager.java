package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1", description = "用户管理系统")
@RequestMapping(value = "v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Boolean login(@RequestBody User user, HttpServletResponse response){
        int i = template.selectOne("login", user);
        if (i == 1){
            Cookie cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            log.info("查询到的结果是"+user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户", httpMethod = "POST")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Boolean addUser(@RequestBody User user, HttpServletRequest request){
        Boolean x = verifyCookies(request);
        int result = 0;
        if (x != null){
            result = template.insert("addUser", user);
            log.info("添加用户的数量是："+ result);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取多个用户列表信息", httpMethod = "POST")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public List<User> getUserListInfo(HttpServletRequest request, @RequestBody User user){
        Boolean x = verifyCookies(request);
        if (x == true){
            List<User> users = template.selectList("getUserList",user);
            log.info("getUserListInfo获取到的用户数量是："+ users.size());
            return users;
        }else {
            return null;
        }
    }

    @ApiOperation(value = "更新/删除单个用户信息", httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public int updateUserInfo(HttpServletRequest request, @RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if (x == true){
            i = template.update("updateUserInfo", user);
        }
        log.info("更新数据的条目数为："+i);
        return i;
    }

    /**
     * 校验cookies是否正确
     * @param request
     * @return Boolean
     */
    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            log.info("cookies为空");
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login")){
                return true;
            }
        }
        return false;
    }
}
