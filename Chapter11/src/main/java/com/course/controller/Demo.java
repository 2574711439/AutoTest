package com.course.controller;

import com.course.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "v1", description = "这是我的第一个版本的demo")
@RequestMapping(value = "v1")
public class Demo {

    //首先获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getEmployeeCount", method = RequestMethod.GET)
    @ApiOperation(value = "可以获取到用户数", httpMethod = "GET")
    public int getEmployeeCount(){
        return template.selectOne("getEmployeeCount");
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户数", httpMethod = "POST")
    public int addEmployee(@RequestBody Employee employee){
        return template.insert("addEmployee", employee);
    }

    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public int updateEmployee(@RequestBody Employee employee){
        return template.update("updateEmployee", employee);
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    public int deleteEmployee(@RequestParam int empo){
        return template.delete("deleteEmployee", empo);
    }
}
