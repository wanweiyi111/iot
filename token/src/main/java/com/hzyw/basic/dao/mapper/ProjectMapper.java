package com.hzyw.basic.dao.mapper;

import com.hzyw.basic.dos.ProjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {

    List<ProjectDO> selectAll();

    /**查询（根据主键ID查询*/
    List<ProjectDO> queryProjectInfoByParameter(@Param("projectName") String projectName, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    int queryAuUserTotalCount(@Param("projectName") String projectName);

    List<ProjectDO> queryAuProjectVOInfo(@Param("projectVO") ProjectDO projectVO);

    int updateAuProjectVO(@Param("projectVO") ProjectDO projectVO);

    int deleteProjectByProjectId(List<String> projectIds);

    int insertProjectVO(@Param("projectVO") ProjectDO projectVO);

}
