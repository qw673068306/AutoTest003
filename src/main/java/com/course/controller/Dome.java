package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value= "v1",tags={"这是我的第一个版本的demo"})
@RequestMapping("v1")
    public class Dome {

    //首先获取一个执行SQL语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "可以获取到用户数",httpMethod = "GET")
    public int getUserCount(){
        return  template.selectOne("getUserCount");

    }

    @RequestMapping(value = "/adduser",method = RequestMethod.GET)
    public int addUser(@RequestBody User user){

        return template.insert("addUser",user);

    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.GET)
    public int updateUser(@RequestBody User user){

        return template.update("updateUser",user);
    }

    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    public int deleteUser(@RequestParam int id){

        return template.delete("deleteUser",id);
    }
}
