//package hzyw.basic;
//
//import com.alibaba.fastjson.JSON;
//import com.hzyw.TokenApplication;
//import com.hzyw.basic.dos.*;
//import com.hzyw.basic.service.*;
//import com.hzyw.basic.vo.PageVO;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TokenApplication.class)
//public class DbTests {
//
//    @Autowired()
//    LoginLogService auLoginLogTService;
//
//    @Autowired()
//    AuLogTService auLogTService;
//
//    @Autowired()
//    AuPermissionTService auPermissionTService;
//
//    @Autowired()
//    RoleService auRoleTService;
//
//    @Autowired()
//    UserService auUserTService;
//
//
//    @Test
//    public void contextLoads() {
//        /*  查询数据
//        PageVO<AuLoginLogDO> auLoginLogDOPageVO=auLoginLogTService.selectByParameter("张三",1,2);
//        PageVO<AuLogDO> auLogDOPageVO=auLogTService.selectByParameter("zhangsan",1,2);
//        PageVO<AuPermissionDO> auPermissionDOPageVO=auPermissionTService.selectByParameter("删除",1,2);
//        PageVO<AuRoleDO> auRoleDOPageVO=auRoleTService.selectByParameter("系统管理员",1,2);
//        PageVO<UserDO> auUserDOPageVO=auUserTService.selectByParameter("zhangsan",1,2);
//        */
//        /*  新增用户登录日志
//        AuLoginLogDO auLoginLogDO=new AuLoginLogDO();
//        auLoginLogDO.setUserName("wangmazi");
//        auLoginLogDO.setIp("12.12.12.12");
//        auLoginLogDO.setLocation("changsha");
//        auLoginLogDO.setLoginTime(new Date());
//        auLoginLogTService.insertAuLoginLogDO(auLoginLogDO);
//        */
//        /*用户操作日志新增
//        AuLogDO auLogDO=new AuLogDO();
//        auLogDO.setUserName("wangmazi");
//        auLogDO.setIp("12.12.12.12");
//        auLogDO.setLocation("changsha");
//        auLogDO.setMethod("chick");
//        auLogDO.setOperation("diandiandian");
//        auLogDO.setTime(123456789);
//        auLogDO.setCreateTime(new Date());
//        auLogTService.insertAuLogDO(auLogDO);
//        */
//        /* 删除权限
//        List<AuPermissionDO> list=new ArrayList<>();
//        AuPermissionDO auPermission=new AuPermissionDO();
//        AuPermissionDO auPermissionDO=new AuPermissionDO();
//        auPermission.setPermissionName("xinzeng");
//        auPermissionDO.setPermissionName("tianjia");
//        list.add(auPermission);
//        list.add(auPermissionDO);
//        auPermissionTService.deleteByParameter(list);
//        */
//
//        /*新增权限表数据
//        AuPermissionDO auPermissionDO=new AuPermissionDO();
//        auPermissionDO.setPermissionName("xinzeng");
//        auPermissionDO.setISEnable("1");
//        auPermissionDO.setParentId(12568);
//        auPermissionDO.setPath("/add");
//        auPermissionDO.setCreateTime(new Date());
//        auPermissionDO.setType("1");
//        auPermissionDO.setAppId(83);
//        auPermissionTService.insertAuPermissionDO(auPermissionDO);
//        */
//
//        /*修改权限表数据
//        AuPermissionDO auPermissionDO=new AuPermissionDO();
//        auPermissionDO.setPermissionName("xinzeng");
//        auPermissionDO.setISEnable("1");
//        auPermissionDO.setParentId(2344);
//        auPermissionDO.setPath("/addAll");
//        auPermissionDO.setCreateTime(new Date());
//        auPermissionDO.setType("1");
//        auPermissionDO.setAppId(83);
//        auPermissionTService.updateByParameter(auPermissionDO);
//        */
//
//        /**批量删除角色信息
//        List<AuRoleDO> list=new ArrayList<>();
//        AuRoleDO auRoleDO=new AuRoleDO();
//        AuRoleDO auRole=new AuRoleDO();
//        auRole.setRoleName("系统管理员");
//        auRoleDO.setRoleName("促销员");
//        list.add(auRoleDO);
//        list.add(auRole);
//        auRoleTService.deleteByParameter(list);
//        */
//
//        /*新增角色
//        AuRoleDO auRoleDO=new AuRoleDO();
//        auRoleDO.setRoleName("系统管理员");
//        auRoleDO.setIsEnable("1");
//        auRoleDO.setRemark("系统管理员");
//        auRoleDO.setCreateTime(new Date());
//        auRoleDO.setAppId("83");
//        auRoleTService.insertAuRoleDO(auRoleDO);
//*/
//        /**修改角色信息
//        AuRoleDO auRoleDO=new AuRoleDO();
//        auRoleDO.setRoleName("系统管理员");
//        auRoleDO.setIsEnable("1");
//        auRoleDO.setRemark("系统管理");
//        auRoleDO.setCreateTime(new Date());
//        auRoleDO.setAppId("831");
//        auRoleTService.updateByParameter(auRoleDO);
//        */
//        /**批量删除用户信息
//        List<UserDO> list=new ArrayList<>();
//         UserDO auUserDO=new UserDO();
//         UserDO a=new UserDO();
//        auUserDO.setUserName("zhangsan");
//        a.setUserName("zhangsn");
//        list.add(auUserDO);
//        list.add(a);
//        auUserTService.deleteByParameter(list);
//        */
//        /*新增用户信息
//        UserDO auUserT=new UserDO();
//        auUserT.setUserName("wangmazi");
//        auUserT.setDescription("mazimazi");
//        auUserT.setPassWord("987654");
//        auUserT.setSsex("0");
//        auUserT.setEmail("65445@qq.com");
//        auUserT.setStatus("1");
//        auUserT.setCreateTime(new Date());
//        auUserTService.insertAuUserDO(auUserT);
//        */
//        /*修改用户信息
//        UserDO auUserT=new UserDO();
//        auUserT.setUserName("wangmazi");
//        auUserT.setDescription("mazimazi");
//        auUserT.setPassWord("456789");
//        auUserT.setSsex("0");
//        auUserT.setEmail("rfdfdfd@qq.com");
//        auUserT.setStatus("0");
//        auUserT.setModifyTime(new Date());
//        auUserTService.updateByParameter(auUserT);
//        */
//
//
//
//        /**随机生成6密码（含大小写字母，数字的组合）
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < 6; j++) {
//                double rand = Math.random();
//                double randTri = Math.random() * 3;
//                if (randTri >= 0 && randTri < 1) {
//                    sb.append((char) (rand * ('9' - '0') + '0'));
//                } else if (randTri >= 1 && randTri < 2) {
//                    sb.append((char) (rand * ('Z' - 'A') + 'A'));
//                } else {
//                    sb.append((char) (rand * ('z' - 'a') + 'a'));
//                }
//            }
//            */
//
//    }
//}
