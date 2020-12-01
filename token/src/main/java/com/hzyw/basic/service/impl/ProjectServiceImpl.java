package com.hzyw.basic.service.impl;

import com.hzyw.basic.dao.mapper.ProjectMapper;
import com.hzyw.basic.dos.ProjectDO;
import com.hzyw.basic.service.ProjectService;
import com.hzyw.basic.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public List<ProjectDO> selectAll() {
        return projectMapper.selectAll();
    }

    @Override
    public PageVO<ProjectDO> queryAuProjectVOListInfo(String projectName, int currentPage, int pageSize) {
        int totalCount = projectMapper.queryAuUserTotalCount(projectName);
        int pageNo = (currentPage - 1) * pageSize;
        PageVO<ProjectDO> pageResult = new PageVO<>(currentPage, pageSize, totalCount);
        pageResult.setCode(200);
        pageResult.setMessage("SUCCESS");
        List<ProjectDO> resultList = projectMapper.queryProjectInfoByParameter(projectName, pageNo, pageSize);
        if (resultList == null || resultList.size() == 0) {
            resultList = new ArrayList<>();
        }
        pageResult.setData(resultList);
        return pageResult;
    }


    @Override
    public List<ProjectDO> queryAuProjectVOInfo(ProjectDO projectVO) {
        return projectMapper.queryAuProjectVOInfo(projectVO);
    }

    @Override
    public int updateAuProjectVO(ProjectDO projectVO) {
        return projectMapper.updateAuProjectVO(projectVO);
    }

    @Override
    public void deleteProjectByProjectId(List<String> projectIds) {
        projectMapper.deleteProjectByProjectId(projectIds);
    }

    @Override
    public int insertProjectVO(ProjectDO projectVO) {
        return projectMapper.insertProjectVO(projectVO);
    }
}
