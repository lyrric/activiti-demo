package com.github.lyrric.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
@RequestMapping(value = "/api/v1.0/student")
@Api(value = "学生端")
public class StudentController {

    @GetMapping(value = "/list")
    String list(int id){
        return "test";
    }


}
