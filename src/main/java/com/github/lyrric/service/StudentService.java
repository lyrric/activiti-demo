package com.github.lyrric.service;

import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.StudentTaskListVO;

import java.util.Date;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
public interface StudentService {

    void create(Date startDate, Integer day);

    PageResult<StudentTaskListVO> list(Integer pageNum, Integer pageSize);
}
