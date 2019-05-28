package com.github.lyrric.service.impl;

import com.github.lyrric.mapper.LeaderMapper;
import com.github.lyrric.model.BusinessException;
import com.github.lyrric.model.PageResult;
import com.github.lyrric.model.vo.LeaderTaskListVO;
import com.github.lyrric.service.LeaderService;
import com.github.lyrric.util.ActivitiUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2019-05-23.
 *
 * @author wangxiaodong
 */
@Service
public class LeaderServiceImpl implements LeaderService {

    /**
     * 领导ID
     */
    private final Integer LEADER_ID = 1;
    /**
     * 领导名称
     */
    private final String LEADER_NAME = "王晴";
    /**
     * 领导节点名称
     */
    private final String TASK_NAME = "教务处";

    @Resource
    private LeaderMapper leaderMapper;
    @Resource
    private ActivitiUtil activitiUtil;



    @Override
    public PageResult<LeaderTaskListVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageResult<>(leaderMapper.list(LEADER_ID));
    }

    @Override
    public void approval(Integer id, boolean pass) throws BusinessException {
        Map<String, Object> var = new HashMap<>(1);
        var.put("LeaderId", LEADER_ID);
        var.put("LeaderName", LEADER_NAME);
        if(pass) {
            activitiUtil.completeTask(id, var, TASK_NAME);
        }
        else {
            activitiUtil.rollbackToFirstTask(id, TASK_NAME);
        }
    }
}
