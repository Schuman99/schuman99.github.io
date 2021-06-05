//package com.general.common.service.impl;
//
//
//import com.general.common.service.SchedulingService;
//import com.general.logic.base.service.BaseDeviceService;
//import com.general.logic.cockpit.service.WaterQualityStandardRateServce;
//import com.general.logic.collect.service.CollectInvalidDataHistoryService;
//import com.general.logic.dict.entity.DictProvinces;
//import com.general.logic.dict.service.DictProvincesService;
//import com.general.logic.maintenance.service.MaintenanceInfoService;
//import com.general.logic.patrol.service.PatrolConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * @author ysj
// * @create 2018-04-26
// **/
//@Component
//public class SchedulingServiceImpl implements SchedulingService {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private DictProvincesService provincesService;
//    @Autowired
//    private BaseDeviceService baseDeviceService;
//    @Autowired
//    private WaterQualityStandardRateServce waterQualityStandardRateServce;
//    @Autowired
//    private MaintenanceInfoService maintenanceInfoService;
//    @Autowired
//    private PatrolConfigService patrolConfigService;
//    @Autowired
//    private CollectInvalidDataHistoryService collectInvalidDataHistoryService;
//
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Override
//    public void updateProvinces() {
//        DictProvinces provinces = provincesService.updateOrSaveDictProvince();
//        logger.info("{}", provinces);
//    }
//
//    /**
//     * 通过webService更新视频设备
//     */
////    @Scheduled(cron = "0 0 1 * * ?")
////    @Scheduled(cron="0/15 * *  * * ? ")
//    @Override
//    public void updateVideoDevices() {
//        logger.info("开始");
//        baseDeviceService.updateVideoDevices();
//    }
//
//    /**
//     * 每天凌晨一点的时候 跑一遍 获取 驾驶舱页面的 水质达标率
//     */
//    @Scheduled(cron = "0 0 1 * * *")
//    @Override
//    public void getWaterRateByYesterday() {
//        waterQualityStandardRateServce.getWaterRateByYesterday();
//    }
//
//    /**
//     * 每天8：00 推送
//     */
//    @Scheduled(cron = "0 0 8 * * *")
//    @Override
//    public void pushMaintenanceMission() {
//        maintenanceInfoService.timingPushPlan();
//    }
//
//    /**
//     * 每天8：00 更新巡查配置
//     */
//    @Scheduled(cron = "0 0 8 * * *")
//    @Override
//    public void updatePatrolConfig() {
//        patrolConfigService.timingUpdatePatrolConfig();
//    }
//
//    /**
//     * 每过一小时监听设备离线
//     * （四小时没有收到数据即设备离线）
//     */
//    @Scheduled(cron = "0 0 0/1 * * ?")
//    @Override
//    public void updateDeviceState() {
//        baseDeviceService.updateDeviceFaultState(120, 3);
//    }
//
//    /**
//     * 每天零点的时候 清空采集的无效数据记录
//     */
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Override
//    public void clearInvalidDataRecord() {
//        collectInvalidDataHistoryService.clearRecord(new Date());
//    }
//}
