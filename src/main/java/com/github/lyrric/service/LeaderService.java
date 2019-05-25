package com.github.lyrric.service;

import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.LeaderTaskListVO;
import com.github.lyrric.model.vo.TeacherTaskListVO;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
public interface LeaderService {

    PageResult<LeaderTaskListVO> list(Integer pageNum, Integer pageSize);

    void approval(Integer id, boolean pass) throws BusinessException;
}
