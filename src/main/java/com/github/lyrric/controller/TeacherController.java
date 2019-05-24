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
@RequestMapping(value = "/api/v1.0/teacher")
@Api(description = "班主任")
public class TeacherController {


    @GetMapping(value = "/test")
    String index(int id){
        return "test";
    }


}
