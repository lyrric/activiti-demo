package com.github.lyrric.service.impl;

import com.github.lyrric.mapper.TeacherMapper;
import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.TeacherTaskListVO;
import com.github.lyrric.service.TeacherService;
import com.github.lyrric.util.ActivitiUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019-05-23
 *
 * @author wangxiaodong
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    /**
     * 教师ID
     */
    private final Integer TEACHER_ID = 1;
    /**
     * 教师名称
     */
    private final String TEACHER_NAME = "张红";
    /**
     * 班主任节点名称
     */
    private final String TASK_NAME = "班主任";

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private ActivitiUtil activitiUtil;
    @Override
    public PageResult<TeacherTaskListVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageResult<>(teacherMapper.list(TEACHER_ID));
    }

    @Override
    public void approval(Integer id, boolean pass) throws BusinessException {
        Map<String, Object> var = new HashMap<>(2);
        var.put("teacherId", TEACHER_ID);
        var.put("teacherName", TEACHER_NAME);
        if(pass){
            activitiUtil.completeTask(id, var, TASK_NAME);
        }else{
            activitiUtil.rollbackToFirstTask(id, TASK_NAME);
        }
    }


}
