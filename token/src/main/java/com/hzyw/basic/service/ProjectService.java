package com.hzyw.basic.service;

import com.hzyw.basic.dos.ProjectDO;
import com.hzyw.basic.vo.PageVO;

import java.util.List;

public interface ProjectService {
    List<ProjectDO> selectAll();

    PageVO<ProjectDO> queryAuProjectVOListInfo(String projectName, int currentPage, int pageSize);

    List<ProjectDO> queryAuProjectVOInfo(ProjectDO projectVO);

    int updateAuProjectVO(ProjectDO projectVO);

    /**删除（根据主键ID删除）*/
    void deleteProjectByProjectId(List<String> projectIds);

    int insertProjectVO(ProjectDO projectVO);

}
