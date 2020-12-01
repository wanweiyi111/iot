package com.hzyw.basic.vo;

import com.hzyw.basic.dos.ProjectDO;
import com.hzyw.basic.dos.UserDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVO extends UserDO {

    //关联角色
    private Integer roleId;

    private List<ProjectDO> projectDOList;

    //关联项目
    private String projectName;
    //关联角色
    private String roleName;

    //关联部门
    private String deptName;

    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
}
