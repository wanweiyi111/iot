package com.hzyw.basic.dos;

import lombok.Data;

import java.util.Date;

/**
 * 配置用户项目设备数据权限
 */
@Data
public class ProjectEquipmentDO {

        private String id;
        private Long userId;
        private String userName;
        private String equipmentId;    //设备编码
        private String equipmentName;  //设备名称
        private String location;    //所属位置
        private String projectId;           //项目ID
        private String equipmentTypeName;       //设备类型名称 EQUIPMENT_TYPE_NAME
        private String sqlPermission;           //权限SQL  SQL_PERMISSION
        private Date createTime;
        private Date modifyTime;

}
