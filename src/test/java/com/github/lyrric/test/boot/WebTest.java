package com.github.lyrric.test.boot;

import com.github.lyrric.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebTest {

    @Test
    public void DaoTest(){

    }
}
