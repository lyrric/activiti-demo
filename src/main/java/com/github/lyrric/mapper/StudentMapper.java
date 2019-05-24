package com.github.lyrric.mapper;

import com.github.lyrric.model.vo.StudentTaskListVO;
import com.github.pagehelper.Page;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
public interface StudentMapper {

    Page<StudentTaskListVO> list();
}
