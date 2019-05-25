package com.github.lyrric.service;

import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.TeacherTaskListVO;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
public interface TeacherService {

    /**
     * 我的列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<TeacherTaskListVO> list(Integer pageNum, Integer pageSize);

    /**
     * 审批流程
     * @param id
     * @param pass
     */
    void approval(Integer id, boolean pass) throws BusinessException;
}
