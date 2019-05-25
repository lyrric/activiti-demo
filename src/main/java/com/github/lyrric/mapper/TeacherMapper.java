package com.github.lyrric.mapper;

import com.github.lyrric.model.vo.TeacherTaskListVO;
import com.github.pagehelper.Page;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
public interface TeacherMapper {

    /**
     * 查询走到教师的流程，和该教师已处理的流程
     * @param teacherId
     * @return
     */
    Page<TeacherTaskListVO> list(Integer teacherId);
}
