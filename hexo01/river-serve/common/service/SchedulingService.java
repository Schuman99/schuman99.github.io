package com.general.common.service;

/**
 * 定时任务
 * @author zy
 */
public interface SchedulingService {
    /**
     * 通过高德数据接口更新省市区信息
     */
    void updateProvinces();

    /**
     * 通过webService更新视频设备
     */
    void updateVideoDevices();
    /**
     * 每日计算 前一天的 水质达标率
     */
    void  getWaterRateByYesterday();

    /**
     * 运维任务推送定时
     */
    void pushMaintenanceMission();

    /**
     * 更新巡查配置
     */
    void updatePatrolConfig();

    /**
     * 监听设备离线
     */
    void updateDeviceState();

    /**
     * 清空采集的无效数据记录
     */
    void clearInvalidDataRecord();
}
