package com.hzyw.basic.service.impl;

import com.hzyw.basic.dos.LightPowerReportDO;
import com.hzyw.basic.dos.LightRuntimeDO;
import com.hzyw.basic.util.Constant;
import com.hzyw.basic.vo.PoleEquipmentVO;
import com.hzyw.basic.vo.PolePowerVO;
import com.hzyw.basic.vo.PowerVO;
import com.hzyw.basic.vo.ProjectPowerVO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author haoyuan
 * @date 2019.08.07
 */
@Service
public class LightTimeTaskServiceImpl {

    @Resource
    ReportServiceImpl reportService;

    @Resource
    TimeTaskServiceImpl timeTaskService;



    @Scheduled(cron = "59 59 23 * * ?")
    public void getCountByDay() {
        //项目分组统计项目的基础数据，查询运行表查询
        List<LightRuntimeDO> list = reportService.findTodayAllCount();

        //查询灯杆物理表
        List<LightRuntimeDO> lights=reportService.findAllLight();
        //构建统计项目报表对象
        Set<Long> projectIds = new HashSet();
        //设备单灯CODE列表
        List<PowerVO> powerVOS=new ArrayList<>();
        //拿到项目列表,Code列表
        for (LightRuntimeDO runtimeDO : lights) {
            projectIds.add(runtimeDO.getProjectId());

        }

        //1.1计算实际使用电能量
        for (LightRuntimeDO runtimeDO:lights) {
            PowerVO powerVO=new PowerVO();
            powerVO.setEquipmentCode(runtimeDO.getEquipmentCode());
            powerVO.setEquipmentName(runtimeDO.getEquipmentName());
            Double electric=0D;
            Double rated=0D;
            for (LightRuntimeDO runtimeDO1:list) {
                if(runtimeDO.getEquipmentCode().equalsIgnoreCase(runtimeDO1.getEquipmentCode())){
                    runtimeDO1.setRatedPower(runtimeDO.getRatedPower());
                    long time = runtimeDO1.getWorkTime() == null ? 0 : runtimeDO1.getWorkTime();
                    double v = (runtimeDO1.getActivePower()==null?0:runtimeDO1.getActivePower()) * time;
                    double v0=(runtimeDO.getRatedPower()==null?0:runtimeDO.getRatedPower())*time;
                    electric+=v;
                    rated+=v0;
                }
            }
            //单位转换kw*ms --1000*60*60(11/12将毫伏变为V要乘以1000)
            powerVO.setElectricEnergy(electric/3600000000D);
            powerVO.setSaveEnergy((rated-electric)/3600000000D);
            powerVOS.add(powerVO);
        }
        //1.2插入表
        insertReport(Constant.ReportType.lightDay,
                Constant.ReportDimension.light,
                null,
                null,
                powerVOS);



        //2拿到绑定有灯的灯杆列表及灯杆下关灯code列表
        List<PoleEquipmentVO> poleList=reportService.findBindings();
        //构建统计灯杆报表对象
        Set<Long> poleIds = new HashSet();
        List<PolePowerVO> polePowerVOS = new ArrayList<>();
        for(PoleEquipmentVO poleEquipmentVO:poleList){
            if (poleEquipmentVO.getEquipmentType()==4112){
                poleIds.add(poleEquipmentVO.getPoleId());
            }
        }
        //2.2装入code
        for (Long  poleId:poleIds) {
            PolePowerVO polePowerVO=new PolePowerVO();
            List<String> codeList=new ArrayList<>();
            polePowerVO.setPoleId(poleId);
            for (PoleEquipmentVO poleEquipmentVO:poleList) {
                if (poleId.equals(poleEquipmentVO.getPoleId())){
                    codeList.add(poleEquipmentVO.getEquipmentCode());
                }
            }
            polePowerVO.setCodeList(codeList);
            polePowerVOS.add(polePowerVO);
        }
        for (PolePowerVO polePowerVO:polePowerVOS) {
            for (PoleEquipmentVO poleEquipmentVO:poleList) {
                if (polePowerVO.getPoleId().equals(poleEquipmentVO.getPoleId())){
                    polePowerVO.setEquipmentName(poleEquipmentVO.getEquipmentName());
                }
            }
        }
        //2.2装入汇总
        for (PolePowerVO vo:polePowerVOS) {
            Double electric=0D;
            Double save=0D;
            for (PowerVO vo1:powerVOS) {
                if (vo.getCodeList().contains(vo1.getEquipmentCode())) {
                    electric+=vo1.getElectricEnergy();
                    save+=vo1.getSaveEnergy();
                }
            }
            vo.setElectricEnergy(electric);
            vo.setSaveEnergy(save);

        }
        //2.3汇总灯杆，插入表
        insertReport(Constant.ReportType.lightDay,
                Constant.ReportDimension.pole,
                null,
                polePowerVOS,
                null);

        //3.1拿到项目列表及项目列表下的灯CODE列表
        List<ProjectPowerVO> projectPowerVOS=new ArrayList<>();

        for (Long  projectId:projectIds){
            ProjectPowerVO projectPowerVO=new ProjectPowerVO();
            projectPowerVO.setProjectId(projectId);
            Set<String> codes=new HashSet<>();
            for(LightRuntimeDO runtimeDO:list){
                if (projectId.equals(runtimeDO.getProjectId())){
                    codes.add(runtimeDO.getEquipmentCode());
                }
            }
            projectPowerVO.setProjectCodes(codes);
            projectPowerVOS.add(projectPowerVO);
        }
        //3.2汇总
        for (ProjectPowerVO projectPowerVO:projectPowerVOS) {
            Double electric=0D;
            Double save=0D;
            for (PowerVO powerVO:powerVOS){
//                if(projectPowerVO.getProjectCodes().contains(powerVO.getEquipmentCode())){
                    electric+=powerVO.getElectricEnergy();
                    save+=powerVO.getSaveEnergy();
//                }
            }
            projectPowerVO.setElectricEnergy(electric);
            projectPowerVO.setSaveEnergy(save);
        }
        //3.3汇总项目，插入表
        insertReport(Constant.ReportType.lightDay,
                Constant.ReportDimension.project,
                projectPowerVOS,
                null,
                null);

        timeTaskService.getCountByDay(projectPowerVOS);


    }

    private void insertReport( String type,
                               String dimension,
                               List<ProjectPowerVO> projectPowerVOS,
                               List<PolePowerVO> polePowerVOS,
                               List<PowerVO> powerVOS) {
        List<LightPowerReportDO> list=new ArrayList<>();
        //灯
        if(dimension.equalsIgnoreCase(Constant.ReportDimension.light)){
            for (PowerVO powerVO:powerVOS){
                LightPowerReportDO lightPowerReportDO=new LightPowerReportDO();
                lightPowerReportDO.setElectricEnergy(powerVO.getElectricEnergy());
                lightPowerReportDO.setSaveEnergy(powerVO.getSaveEnergy());
                lightPowerReportDO.setSignCode(powerVO.getEquipmentCode());
                lightPowerReportDO.setEquipmentName(powerVO.getEquipmentName());

                lightPowerReportDO.setReportDate(new Date());
                lightPowerReportDO.setReportType(type);
                lightPowerReportDO.setReportDimension(dimension);

                list.add(lightPowerReportDO);
            }
            reportService.saveLightPowerReport(list);
            //灯杆
        }else if (dimension.equalsIgnoreCase(Constant.ReportDimension.pole)){
            for (PolePowerVO polePowerVO:polePowerVOS){
                LightPowerReportDO lightPowerReportDO=new LightPowerReportDO();
                lightPowerReportDO.setElectricEnergy(polePowerVO.getElectricEnergy());
                lightPowerReportDO.setSaveEnergy(polePowerVO.getSaveEnergy());
                lightPowerReportDO.setEquipmentName(polePowerVO.getEquipmentName());

                lightPowerReportDO.setSignCode(polePowerVO.getPoleId()+"");
                lightPowerReportDO.setReportDate(new Date());
                lightPowerReportDO.setReportType(type);
                lightPowerReportDO.setReportDimension(dimension);
                list.add(lightPowerReportDO);
            }
            reportService.saveLightPowerReport(list);
            //项目
        }else if (dimension.equalsIgnoreCase(Constant.ReportDimension.project)){
            for (ProjectPowerVO projectPowerVO:projectPowerVOS) {
                LightPowerReportDO lightPowerReportDO=new LightPowerReportDO();
                lightPowerReportDO.setElectricEnergy(projectPowerVO.getElectricEnergy());
                lightPowerReportDO.setSaveEnergy(projectPowerVO.getSaveEnergy());

                lightPowerReportDO.setSignCode(projectPowerVO.getProjectId()+"");
                lightPowerReportDO.setReportDate(new Date());
                lightPowerReportDO.setReportType(type);
                lightPowerReportDO.setReportDimension(dimension);
                list.add(lightPowerReportDO);
            }
            reportService.saveLightPowerReport(list);
        }

    }


}
