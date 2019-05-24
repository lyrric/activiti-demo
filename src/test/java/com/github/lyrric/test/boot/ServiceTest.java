package com.github.lyrric.test.boot;

import com.github.lyrric.Application;
import com.github.lyrric.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceTest {
    @Resource
    private StudentService studentService;

    @Test
    public void DaoTest(){
        studentService.create(new Date(), 1);
    }
}
