package com.general.common.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.general.common.constant.ModuleConstants;
import com.general.common.constant.RoleConstants;
import com.general.common.enums.*;
import com.general.common.service.PlatformService;
import com.general.common.service.PushMessageService;
import com.general.core.util.DateUtils;
import com.general.logic.base.dto.*;
import com.general.logic.base.service.BaseAccountService;
import com.general.logic.base.service.BaseDeviceService;
import com.general.logic.base.service.BaseRiverService;
import com.general.logic.base.service.BaseWaterQualityStandardsService;
import com.general.logic.collect.dto.CollectCommunityRiverDTO;
import com.general.logic.collect.dto.CollectDataInfoDTO;
import com.general.logic.collect.dto.CollectFlowSpeedDTO;
import com.general.logic.collect.dto.CollectInvalidDataHistoryDTO;
import com.general.logic.collect.service.CollectDataInfoService;
import com.general.logic.collect.service.CollectFlowSpeedService;
import com.general.logic.collect.service.CollectInvalidDataHistoryService;
import com.general.logic.dict.dto.DictRiverBrakeDTO;
import com.general.logic.dict.dto.DictWaterQualityStandardsDTO;
import com.general.logic.dict.entity.DictRiverBrake;
import com.general.logic.dict.service.DictRiverBrakeService;
import com.general.logic.dict.service.DictWaterQualityStandardsService;
import com.general.logic.gis.dto.GisRiverBrakeBaseDTO;
import com.general.logic.gis.dto.GisRiverBrakeDTO;
import com.general.logic.gis.dto.GisRiverBrakeDetailsDTO;
import com.general.logic.gis.service.GisRiverBrakeDetailsService;
import com.general.logic.gis.service.GisRiverBrakeService;
import com.general.logic.item.dto.ItemManageDTO;
import com.general.logic.item.dto.ItemTraceDTO;
import com.general.logic.item.service.ItemManageService;
import com.general.logic.item.service.ItemTraceService;
import com.general.logic.warn.dto.WarnBrakeDTO;
import com.general.logic.warn.dto.WarnDeviceDTO;
import com.general.logic.warn.dto.WarnWaterQualityDTO;
import com.general.logic.warn.dto.WarnWaterQualityDetailDTO;
import com.general.logic.warn.service.WarnBrakeService;
import com.general.logic.warn.service.WarnDeviceService;
import com.general.logic.warn.service.WarnWaterQualityDetailService;
import com.general.logic.warn.service.WarnWaterQualityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformServiceImpl extends CommonServiceImpl<DictRiverBrake, DictRiverBrakeDTO> implements PlatformService {
    private Logger logger = LogManager.getLogger(PlatformServiceImpl.class);
    @Autowired
    private BaseDeviceService baseDeviceService;
    @Autowired
    private BaseRiverService baseRiverService;
    @Autowired
    private GisRiverBrakeService gisRiverBrakeService;
    @Autowired
    private GisRiverBrakeDetailsService gisRiverBrakeDetailsService;
    @Autowired
    private CollectDataInfoService collectDataInfoService;
    @Autowired
    private CollectFlowSpeedService collectFlowSpeedService;
    @Autowired
    private BaseWaterQualityStandardsService baseWaterQualityStandardsService;
    @Autowired
    private DictRiverBrakeService dictRiverBrakeService;
    @Autowired
    private DictWaterQualityStandardsService dictWaterQualityStandardsService;
    @Autowired
    private WarnBrakeService warnBrakeService;
    @Autowired
    private WarnDeviceService warnDeviceService;
    @Autowired
    private WarnWaterQualityService warnWaterQualityService;
    @Autowired
    private ItemManageService itemManageService;
    @Autowired
    private ItemTraceService itemTraceService;
    @Autowired
    private BaseAccountService baseAccountService;
    @Autowired
    private WarnWaterQualityDetailService warnWaterQualityDetailService;
    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private CollectInvalidDataHistoryService collectInvalidDataHistoryService;

    /**
     * 接受传感器数据测试
     */
    @Override
    public String addPlatForm(JSONObject result) {
        // 数据类别
        String type = result.getString("type");
        // 发送时间
        Date collectTime = result.getDate("send_time") == null ? new Date() : result.getDate("send_time");
        JSONArray array = result.getJSONArray("device_list");
        switch (type) {
            case "1":   // 水质参数
            case "2":   // 闸泵站参数
                handleWaterParam(array, collectTime);
                break;
            case "3":   // 设备状态参数
                handleDeviceParam(array, collectTime);
                break;
            case "4":   // 流量流速参数
                handleFlowSpeedParam(array);
                break;
            default:
                break;
        }
        return "success";
    }

    /**
     * 接收设备流量流速数据
     */
    private void handleFlowSpeedParam(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i ++) {
            JSONObject json = jsonArray.getJSONObject(i);
            CollectFlowSpeedDTO collectFlowSpeed = JSONObject.parseObject(json.toJSONString(), CollectFlowSpeedDTO.class);
            String deviceNum = json.getString("device_code");
            BaseDeviceBaseDTO baseDevice = baseDeviceService.findByDeviceNum(deviceNum);
            if (baseDevice != null && baseDevice.getBaseRiver() != null) {
                collectFlowSpeed.setBaseDevice(baseDevice);
                collectFlowSpeed.setRiver(baseDevice.getBaseRiver());
                collectFlowSpeed = collectFlowSpeedService.saveByDTO(collectFlowSpeed);
                baseRiverService.updateFlowSpeedByRiverId(baseDevice.getBaseRiver().getId(), collectFlowSpeed.getId());
            }
        }
    }

    /**
     * 接收设备电源状态和故障状态数据
     */
    private void handleDeviceParam(JSONArray jsonArray, Date collectTime) {
        for (int i = 0; i < jsonArray.size(); i ++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String deviceNum = json.getString("device_code");
//            Integer powerState = json.getInteger("cs01");
//            Integer troubleState = json.getInteger("cs02");
            System.out.println(json);
            if (deviceNum.indexOf("-b-") > -1) {
                Boolean flag1 =  json.getBoolean("cs01");
                Boolean flag2 =  json.getBoolean("cs02");
                Boolean flag3 =  json.getBoolean("cs03");
                if (flag1 != null) {
                    System.out.println(deviceNum + (flag1 ? "泵站电源启动" : "泵站电源未启动"));
                }
                if (flag2 != null) {
                    System.out.println(deviceNum + (flag2 ? "泵站正常" : "泵站故障"));
                }
                if (flag3 != null) {
                    System.out.println(deviceNum + (flag3 ? "泵站启动" : "泵站关闭"));
                }
            }
            if (deviceNum.indexOf("-z-") > -1) {
                Boolean flag1 =  json.getBoolean("cs01");
                Boolean flag2 =  json.getBoolean("cs02");
                Boolean flag3 =  json.getBoolean("cs03");
                Boolean flag4 =  json.getBoolean("cs04");
                Boolean flag5 =  json.getBoolean("cs05");
                Boolean flag6 =  json.getBoolean("cs06");
                if (flag1 != null) {
                    System.out.println(deviceNum + (flag1 ? "闸门开到位" : "闸门未开到位"));
                }
                if (flag2 != null) {
                    System.out.println(deviceNum + (flag2 ? "闸门关到位" : "闸门未关到位"));
                }
                if (flag3 != null) {
                    System.out.println(deviceNum + (flag3 ? "闸门下降" : "闸门未下降"));
                }
                if (flag4 != null) {
                    System.out.println(deviceNum + (flag4 ? "闸门停止" : "闸门未停止"));
                }
                if (flag5 != null) {
                    System.out.println(deviceNum + (flag5 ? "闸门上升" : "闸门未上升"));
                }
                if (flag6 != null) {
                    System.out.println(deviceNum + (flag6 ? "闸门电源开" : "闸门电源关"));
                }
            }
//            BaseDeviceBaseDTO baseDevice = baseDeviceService.findByDeviceNum(deviceNum);
//            if (baseDevice == null || (powerState == null && troubleState == null)) continue;

//            if (powerState != null && baseDevice.getOpenState() != powerState) {
//                baseDeviceService.updateBaseDevicePowerState(baseDevice.getId(), powerState);
//            }
//            if (troubleState == FaultStateEnum.TROUBLE.getState() && baseDevice.getFaultState() != FaultStateEnum.TROUBLE.getState()) {
//                baseDeviceService.updateDeviceFaultState(baseDevice.getId(), FaultStateEnum.TROUBLE.getState());
//                WarnDeviceDTO warnDeviceDTO = addWarnDevice(baseDevice, " 设备故障", collectTime);
//                addItemManage(baseDevice, warnDeviceDTO.getId(), ItemManageSourceTypeEnum.DEVICE_FAULT.getState(), collectTime, warnDeviceDTO.getFaultExplain(), warnDeviceDTO.getFaultExplain());
//            }
        }
    }

    /**
     * 接收水质和闸泵站数据
     */
    private void handleWaterParam(JSONArray jsonArray, Date collectTime) {
        for (int i = 0; i < jsonArray.size(); i ++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String deviceNum = json.getString("device_code");
            BaseDeviceBaseDTO baseDevice = baseDeviceService.findByDeviceNum(deviceNum);
            if (baseDevice == null) continue;
            String str = baseDevice.getDictDeviceType().getTypeName();
            JSONArray csList = json.getJSONArray("cs_list");
            if (!csList.isEmpty()) {
                Long start = System.currentTimeMillis();
                if (baseDevice.getOpenState() != PowerStateEnum.OPEN.getState()) {
                    baseDeviceService.updateBaseDevicePowerState(baseDevice.getId(), PowerStateEnum.OPEN.getState());
                }
                if (isAllZero(csList)) {
                    // 更新故障状态为设备离线
                    if (baseDevice.getFaultState() == null || baseDevice.getFaultState() == FaultStateEnum.ONLINE.getState()) {
                        baseDeviceService.updateDeviceFaultState(baseDevice.getId(), FaultStateEnum.OFFLINE.getState());
                        WarnDeviceDTO warnDeviceDTO = addWarnDevice(baseDevice, " 设备离线", collectTime);
                        addItemManage(baseDevice, warnDeviceDTO.getId(), ItemManageSourceTypeEnum.DEVICE_FAULT.getState(), collectTime, warnDeviceDTO.getFaultExplain(), warnDeviceDTO.getFaultExplain());
                    }
                } else if (!isRepeat(baseDevice.getBaseRiver().getId(), collectTime)) {
                    // 更新故障状态为设备在线
                    if (baseDevice.getFaultState() == null || baseDevice.getFaultState() != FaultStateEnum.ONLINE.getState()) {
                        baseDeviceService.updateDeviceFaultState(baseDevice.getId(), FaultStateEnum.ONLINE.getState());
                        WarnDeviceDTO warnDeviceDTO = addWarnDevice(baseDevice, " 设备上线", collectTime);
                        pushMessageService.addPushMessageToUser(PushMessageTypeEnum.WARN_NOTICE, baseDevice.getBaseAccount().getId(), baseDevice.getDeviceName() + " 设备上线", ModuleConstants.WARN_DIVICE, warnDeviceDTO.getId());
                    }
                }

                if (str.contains("闸泵站") && !isAllZero(csList)) {
                    // 闸泵检测信息
                    GisRiverBrakeDTO riverBrakeDTO = new GisRiverBrakeDTO(baseDevice, collectTime);
                    List<GisRiverBrakeDetailsDTO> details = new ArrayList<>();
                    for (int j = 0; j < csList.size(); j ++) {
                        String cs_no = csList.getJSONObject(j).getString("cs_no");
                        Double cs_value = csList.getJSONObject(j).getDouble("cs_value");
                        DictRiverBrakeDTO dictRiverBrake = null;
                        if ("闸泵站-闸".equals(str)) {
                            // 闸泵站-闸 检测参数
                            dictRiverBrake = dictRiverBrakeService.findBrakeZBySerial(cs_no);
                        }
                        if ("闸泵站-泵".equals(str)) {
                            // 闸泵站-泵 检测参数
                            dictRiverBrake = dictRiverBrakeService.findBrakeBBySerial(cs_no);
                        }
                        if (dictRiverBrake == null) break;
                        GisRiverBrakeDetailsDTO detail = new GisRiverBrakeDetailsDTO(dictRiverBrake, cs_value);
                        details.add(detail);
                    }
                    riverBrakeDTO = gisRiverBrakeService.saveByDTO(riverBrakeDTO);
                    for (GisRiverBrakeDetailsDTO detail : details) {
                        detail.setGisRiverBrake(new GisRiverBrakeBaseDTO(riverBrakeDTO.getId()));
                    }
                    details = gisRiverBrakeDetailsService.saveByDTO(details);
                    for (GisRiverBrakeDetailsDTO detail : details) {
                        // 判断该设备该参数当天有没有生成过预警（一天只生成一条预警）
                        Integer num = warnBrakeService.countByDeviceIdAndParamId(riverBrakeDTO.getBaseDevice().getId(), detail.getDictRiverBrake().getId(), riverBrakeDTO.getDetectionTime());
                        if (num == 0){
                            DictRiverBrakeDTO dictRiverBrake = dictRiverBrakeService.findDTOById(detail.getDictRiverBrake().getId());
                            Double value = detail.getNumber();
                            // 判断是否生成预警
                            if (isGenerateBrakeWarn(dictRiverBrake, value)) {
                                WarnBrakeDTO warnBrakeDTO = addWarnBrake(baseDevice, dictRiverBrake, value.toString(), collectTime);
                                String unit = "";
                                if (dictRiverBrake.getUnit() != null) {
                                    unit = dictRiverBrake.getUnit();
                                }
                                addItemManage(baseDevice, warnBrakeDTO.getId(), ItemManageSourceTypeEnum.BRAKE_WARN.getState(), collectTime, dictRiverBrake.getDetection() + " 超标", dictRiverBrake.getDetection() + ": " + value + unit + " (正常区间: " + dictRiverBrake.getScopeMin() + dictRiverBrake.getUnit() + "-" + dictRiverBrake.getScopeMax() + dictRiverBrake.getUnit() + ")");
                            }
                        }
                    }
                } else if ("水质检测仪".equals(str) && !isAllZero(csList) && !isRepeat(baseDevice.getBaseRiver().getId(), collectTime)) {
                    // 水质检测信息
                    CollectDataInfoDTO collect = new CollectDataInfoDTO(new BaseRiverBaseDTO(baseDevice.getBaseRiver().getId()), new BaseDeviceBaseDTO(baseDevice.getId()), CollectDataTypeEnum.SHE_BEI.toString(), baseDevice.getBaseRiver().getAssessmentTarget(), collectTime);
                    List<CollectCommunityRiverDTO> community = new ArrayList<>();
                    for (int j = 0; j < csList.size(); j ++) {
                        JSONObject jsonObject = csList.getJSONObject(j);
                        BigDecimal value = jsonObject.getBigDecimal("cs_value");
                        String num = jsonObject.getString("cs_no");
                        DictWaterQualityStandardsDTO standardsDTO = dictWaterQualityStandardsService.findDTOBySerial(num);
                        CollectCommunityRiverDTO collectDetail = new CollectCommunityRiverDTO(standardsDTO, value);
                        community.add(collectDetail);
                    }
                    collect.setCollectCommunityRiverList(community);
                    CollectDataInfoDTO newData = collectDataInfoService.saveDataByDTO(collect);
                    // 生成水质预警（现状类别 > 目标水质）
                    if (Integer.parseInt(newData.getNowCategory()) > Integer.parseInt(newData.getAppraisalTarget())) {
                        WarnWaterQualityDTO warnWater = addWarnWater(newData);
                        if (warnWater != null && warnWater.getDetailList().size() > 0) {
                            for (WarnWaterQualityDetailDTO dto : warnWater.getDetailList()) {
                                DictWaterQualityStandardsDTO baseDTO = dto.getDictWaterQualityStandards();
                                String message = "";
                                if (dto.getScopeMin() != null && dto.getSymbolMin() != null && dto.getScopeMax() != null && dto.getSymbolMax() != null) {
                                    message = baseDTO.getParamName() + ": " + dto.getNowValues() + baseDTO.getUnit() + " (正常区间: " + dto.getScopeMin() + baseDTO.getUnit() + "-" + dto.getScopeMax() + baseDTO.getUnit() + ")";
                                } else if (dto.getScopeMax() != null && dto.getSymbolMax() != null) {
                                    message = baseDTO.getParamName() + ": " + dto.getNowValues() + baseDTO.getUnit() + " (正常区间: " + dto.getSymbolMin() + dto.getScopeMin() + baseDTO.getUnit() + ")";
                                }
                                addItemManage(baseDevice, dto.getId(), ItemManageSourceTypeEnum.WATER_WARN.getState(), collectTime, baseDTO.getParamName() + " 超标", message);
                            }
                        }
                    }
                    // 生成黑臭预警（黑臭程度 == 重度）
                    if (BlackStinkEnum.HEIGHT.getState().equals(newData.getBlackOdorousExtent())) {
                        WarnWaterQualityDTO warnWater = addWarnBlack(newData);
                        if (warnWater != null && warnWater.getDetailList().size() > 0) {
                            for (WarnWaterQualityDetailDTO dto : warnWater.getDetailList()) {
                                DictWaterQualityStandardsDTO baseDTO = dto.getDictWaterQualityStandards();
                                String message = "";
                                if (dto.getScopeMin() != null && dto.getSymbolMin() != null) {
                                    message = baseDTO.getParamName() + ": " + dto.getNowValues() + baseDTO.getUnit() + " (正常区间: " + dto.getSymbolMin() + dto.getScopeMin() + baseDTO.getUnit() + ")";
                                }
                                if (dto.getScopeMax() != null && dto.getSymbolMax() != null) {
                                    message = baseDTO.getParamName() + ": " + dto.getNowValues() + baseDTO.getUnit() + " (正常区间: " + dto.getSymbolMax() + dto.getScopeMax() + baseDTO.getUnit() + ")";
                                }
                                addItemManage(baseDevice, dto.getId(), ItemManageSourceTypeEnum.WATER_WARN.getState(), collectTime, baseDTO.getParamName() + " 超标", message);
                            }
                        }
                    }
                }
                Long end = System.currentTimeMillis();
                logger.info("数据处理耗时: " + (end-start) + "毫秒");
            }
        }
    }

    /**
     * 生成事项信息
     */
    public void addItemManage(BaseDeviceBaseDTO baseDevice, String sourceId, String sourceType, Date time, String itemTheme, String manageContent) {
        // 截止日期  暂定未来七天
        Date deadline = DateUtils.getPastDate(time, 7);
        // 新建事项
        ItemManageDTO itemManageDTO = new ItemManageDTO();
        // 来源id == 预警信息id
        itemManageDTO.setSourceId(sourceId);
        // 来源类型
        itemManageDTO.setSourceType(sourceType);
        // 截至日期
        itemManageDTO.setDeadline(deadline);
        // 坐标 (设备坐标)
        itemManageDTO.setItemLat(baseDevice.getLat());
        itemManageDTO.setItemLng(baseDevice.getLng());
        // 事项编号
        itemManageDTO.setItemNum(itemManageService.generateItemNum());
        // 事项等级 (紧急)
        itemManageDTO.setLevel(ItemLevelEnum.URGENCY.toString());
        // 事项状态 (未处理)
        itemManageDTO.setState(ItemStateEnum.UNTREATED.getState());
        // 事项主题
        itemManageDTO.setItemTheme(baseDevice.getDeviceName() + " " + itemTheme);
        // 事项地点 (设备地址)
        itemManageDTO.setItemSite(baseDevice.getLocation());
        // 关联河道
        itemManageDTO.setBaseRiver(new BaseRiverBaseDTO(baseDevice.getBaseRiver().getId()));
        // 事项内容
        itemManageDTO.setItemContent(manageContent);
        // 保存事项
        itemManageDTO = itemManageService.saveByDTO(itemManageDTO);
        // 新建一条事项追踪
        ItemTraceDTO itemTraceDTO = new ItemTraceDTO();
        // 关联事项
        itemTraceDTO.setItemManage(new ItemManageDTO(itemManageDTO.getId()));
        // 截止日期
        itemTraceDTO.setDeadline(deadline);
        // 事项等级 (紧急)
        itemTraceDTO.setLevel(ItemLevelEnum.URGENCY.getLevel());
        // 事项类型 (待办)
        itemTraceDTO.setItemType(ItemTypeEnum.WAIT.toString());
        // 来源类型 (系统)
        itemTraceDTO.setSourceType(ItemTraceSourceTypeEnum.SYSTEM.toString());
        // 目标用户
        BaseAccountItemBaseDTO baseAccountItemBaseDTO = baseAccountService.findAccountByRiverIdAndRoleName(baseDevice.getBaseRiver().getId(), RoleConstants.ROLE_AREA);
        // 如果是设备故障目标用户就为设备负责人
        if (ItemManageSourceTypeEnum.DEVICE_FAULT.getState().equals(sourceType) && baseDevice.getBaseAccount() != null) {
            itemTraceDTO.setTargetAccout(new BaseAccountDTO(baseDevice.getBaseAccount().getId()));
        } else if (baseAccountItemBaseDTO == null) {
            itemTraceDTO.setTargetAccout(baseAccountService.getAdminAccount(BaseAccountDTO.class));
        } else {
            itemTraceDTO.setTargetAccout(new BaseAccountDTO(baseAccountItemBaseDTO.getId()));
        }
        // 事项小结
        itemTraceDTO.setItemSummary(baseDevice.getDeviceName() + " " + itemTheme);
        itemTraceDTO = itemTraceService.saveByDTO(itemTraceDTO);
        itemManageService.updateItemTraceId(itemManageDTO.getId(), itemTraceDTO.getId());
        pushMessageService.addPushMessageToUser(PushMessageTypeEnum.TODO_NOTICE, itemTraceDTO.getTargetAccout().getId(), itemTraceDTO.getItemSummary(), ModuleConstants.ITEM, itemTraceDTO.getId());
    }

    /**
     * 生成闸泵站预警
     */
    private WarnBrakeDTO addWarnBrake(BaseDeviceBaseDTO baseDevice, DictRiverBrakeDTO dictRiverBrak, String nowValues, Date warnTime) {
        WarnBrakeDTO warnBrakeDTO = new WarnBrakeDTO();
        warnBrakeDTO.setBaseRiver(new BaseRiverBaseDTO(baseDevice.getBaseRiver().getId()));
        warnBrakeDTO.setBaseDevice(new BaseDeviceBaseDTO(baseDevice.getId()));
        warnBrakeDTO.setWarnTime(warnTime);
        warnBrakeDTO.setDictRiverBrake(new DictRiverBrakeDTO(dictRiverBrak.getId()));
        warnBrakeDTO.setNowValues(nowValues);
        warnBrakeDTO.setScopeMin(dictRiverBrak.getScopeMin());
        warnBrakeDTO.setScopeMax(dictRiverBrak.getScopeMax());
        warnBrakeDTO = warnBrakeService.saveByDTO(warnBrakeDTO);
        return warnBrakeDTO;
    }

    /**
     * 生成水质预警
     */
    private WarnWaterQualityDTO addWarnWater(CollectDataInfoDTO dto) {
        List<CollectCommunityRiverDTO> collects = dto.getCollectCommunityRiverList();
        List<WarnWaterQualityDetailDTO> detailList = new ArrayList<>();
        Integer target = Integer.parseInt(dto.getAppraisalTarget());
        for (CollectCommunityRiverDTO dto1 : collects) {
            // 如果设备该参数超标&&当天有没有生成过预警（一天只生成一条预警）则生成预警
            if (computeWaterLevel(dto1) <= target || warnWaterQualityDetailService.countByDeviceIdAndParamId(dto.getBaseDevice().getId(), dto1.getDictWaterQualityStandards().getId(), dto.getCollectTime(), 1) != 0) continue;
            JSONObject jsonObject = computeParamRange(dto1.getDictWaterQualityStandards().getId(), target, "1");
            WarnWaterQualityDetailDTO detail = new WarnWaterQualityDetailDTO();
            if (jsonObject.getString("symbol1") != null && jsonObject.getBigDecimal("num1") != null) {
                if (jsonObject.getBigDecimal("num").compareTo(jsonObject.getBigDecimal("num1")) == 1) {
                    detail.setScopeMin(jsonObject.getBigDecimal("num1"));
                    detail.setSymbolMin(jsonObject.getString("symbol1"));
                    detail.setScopeMax(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                } else {
                    detail.setScopeMin(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMin(jsonObject.getString("symbol"));
                    detail.setScopeMax(jsonObject.getBigDecimal("num1"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                }
            } else {
                if ("< ≤".contains(jsonObject.getString("symbol"))) {
                    detail.setScopeMax(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                }
                if ("> ≥".contains(jsonObject.getString("symbol"))) {
                    detail.setScopeMin(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMin(jsonObject.getString("symbol"));
                }
            }
            detail.setDictWaterQualityStandards(dto1.getDictWaterQualityStandards());
            detail.setNowValues(dto1.getParamNum());
            detailList.add(detail);
        }
        if (detailList.size() > 0) {
            WarnWaterQualityDTO qualityDTO = warnWaterQualityService.findDTOByRiverIdAndDeviceId(dto.getRiver().getId(), dto.getBaseDevice().getId(), dto.getCollectTime());
            if (qualityDTO == null) {
                WarnWaterQualityDTO warnWaterQuality = new WarnWaterQualityDTO();
                warnWaterQuality.setBaseRiver(dto.getRiver());
                warnWaterQuality.setBaseDevice(dto.getBaseDevice());
                warnWaterQuality.setWarnTime(dto.getCollectTime());
                qualityDTO = warnWaterQualityService.saveByDTO(warnWaterQuality);
            }
            WarnWaterQualityDTO warnDTO = new WarnWaterQualityDTO(qualityDTO.getId());
            for (WarnWaterQualityDetailDTO detailDTO : detailList) {
                detailDTO.setWarnWaterQuality(warnDTO);
                detailDTO.setWarnTime(dto.getCollectTime());
                detailDTO.setWarnType(1);
            }
            detailList = warnWaterQualityDetailService.saveByDTO(detailList);
            qualityDTO.setDetailList(detailList);
            return qualityDTO;
        }
        return null;
    }

    /**
     * 生成黑臭预警
     */
    private WarnWaterQualityDTO addWarnBlack(CollectDataInfoDTO dto) {
        List<CollectCommunityRiverDTO> collects = dto.getCollectCommunityRiverList();
        List<WarnWaterQualityDetailDTO> detailList = new ArrayList<>();
        Integer target = 1;
        for (CollectCommunityRiverDTO dto1 : collects) {
            // 如果设备该参数超标&&当天有没有生成过预警（一天只生成一条预警）则生成预警
            if (computeBlackLevel(dto1) < 2 || warnWaterQualityDetailService.countByDeviceIdAndParamId(dto.getBaseDevice().getId(), dto1.getDictWaterQualityStandards().getId(), dto.getCollectTime(), 2) != 0) continue;
            JSONObject jsonObject = computeParamRange(dto1.getDictWaterQualityStandards().getId(), target, "2");
            WarnWaterQualityDetailDTO detail = new WarnWaterQualityDetailDTO();
            if (jsonObject.getString("symbol1") != null && jsonObject.getBigDecimal("num1") != null) {
                if (jsonObject.getBigDecimal("num").compareTo(jsonObject.getBigDecimal("num1")) == 1) {
                    detail.setScopeMin(jsonObject.getBigDecimal("num1"));
                    detail.setSymbolMin(jsonObject.getString("symbol1"));
                    detail.setScopeMax(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                } else {
                    detail.setScopeMin(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMin(jsonObject.getString("symbol"));
                    detail.setScopeMax(jsonObject.getBigDecimal("num1"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                }
            } else {
                if ("< ≤".contains(jsonObject.getString("symbol"))) {
                    detail.setScopeMax(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMax(jsonObject.getString("symbol"));
                }
                if ("> ≥".contains(jsonObject.getString("symbol"))) {
                    detail.setScopeMin(jsonObject.getBigDecimal("num"));
                    detail.setSymbolMin(jsonObject.getString("symbol"));
                }
            }
            detail.setDictWaterQualityStandards(dto1.getDictWaterQualityStandards());
            detail.setNowValues(dto1.getParamNum());
            detailList.add(detail);
        }
        if (detailList.size() > 0) {
            WarnWaterQualityDTO qualityDTO = warnWaterQualityService.findDTOByRiverIdAndDeviceId(dto.getRiver().getId(), dto.getBaseDevice().getId(), dto.getCollectTime());
            if (qualityDTO == null) {
                WarnWaterQualityDTO warnWaterQuality = new WarnWaterQualityDTO();
                warnWaterQuality.setBaseRiver(dto.getRiver());
                warnWaterQuality.setBaseDevice(dto.getBaseDevice());
                warnWaterQuality.setWarnTime(dto.getCollectTime());
                qualityDTO = warnWaterQualityService.saveByDTO(warnWaterQuality);
            }
            WarnWaterQualityDTO warnDTO = new WarnWaterQualityDTO(qualityDTO.getId());
            for (WarnWaterQualityDetailDTO detailDTO : detailList) {
                detailDTO.setWarnType(2);
                detailDTO.setWarnWaterQuality(warnDTO);
                detailDTO.setWarnTime(dto.getCollectTime());
            }
            detailList = warnWaterQualityDetailService.saveByDTO(detailList);
            qualityDTO.setDetailList(detailList);
            return qualityDTO;
        }
        return null;
    }

    /**
     * 生成设备提醒
     */
    public WarnDeviceDTO addWarnDevice(BaseDeviceBaseDTO deviceBaseDTO, String faultExplain, Date faultTime) {
        WarnDeviceDTO warnDeviceDTO = new WarnDeviceDTO();
        warnDeviceDTO.setBaseDevice(deviceBaseDTO);
        warnDeviceDTO.setFaultExplain(faultExplain);
        warnDeviceDTO.setFaultTime(faultTime);
        warnDeviceDTO.setWarnState(WarnStateEnum.UNTREATED.getState());
        return warnDeviceService.saveByDTO(warnDeviceDTO);
    }

    // 判断是否需要生成闸泵站预警
    private Boolean isGenerateBrakeWarn(DictRiverBrakeDTO dictRiverBrake, Double value) {
        if (dictRiverBrake.getScopeMax() != null && dictRiverBrake.getScopeMin() != null
                && value > dictRiverBrake.getScopeMax().doubleValue() || value < dictRiverBrake.getScopeMin().doubleValue()) {
            // 大于最大值或者小于最小值
                return true;
        } else if (dictRiverBrake.getScopeMax() != null && value > dictRiverBrake.getScopeMax().doubleValue()) {
            // 大于最大值
            return true;
        } else if (dictRiverBrake.getScopeMin() != null && value < dictRiverBrake.getScopeMin().doubleValue()) {
            // 小于最小值
            return true;
        }
        return false;
    }

    /**
     * 根据水质id和目标等级计算出参数标准范围
     */
    private JSONObject computeParamRange(String dictId, Integer target, String paramType) {
        List<JSONObject> jsonObjectList = baseWaterQualityStandardsService.findByDictWaterAndWaterType(dictId, paramType);
        String symbol = jsonObjectList.get(target - 1).getString("symbol");
        if (target < jsonObjectList.size()) {
            String symbol1 = jsonObjectList.get(target).getString("symbol");
            if ("< ≤".contains(symbol) && "< ≤".contains(symbol1)) {
                // 后<前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == -1 && target > 0) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target).getBigDecimal("num"));
                }
                // 后>前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == 1 && target > 1) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target - 2).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target - 2).getBigDecimal("num"));
                }
            }
            if ("< ≤".contains(symbol) && "> ≥".contains(symbol1)) {
                // 后==前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == 0 && target > 1) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target - 2).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target - 2).getBigDecimal("num"));
                }
            }
            if ("> ≥".contains(symbol) && "> ≥".contains(symbol1)) {
                // 后<前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == -1 && target > 1) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target - 2).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target - 2).getBigDecimal("num"));
                }
                // 后>前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == 1 && target > 0) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target).getBigDecimal("num"));
                }
            }
            if ("> ≥".contains(symbol) && "< ≤".contains(symbol1)) {
                // 后==前
                if (jsonObjectList.get(target).getBigDecimal("num").compareTo(jsonObjectList.get(target - 1).getBigDecimal("num")) == 0 && target > 1) {
                    jsonObjectList.get(target - 1).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(target - 2).getString("symbol")));
                    jsonObjectList.get(target - 1).put("num1", jsonObjectList.get(target - 2).getBigDecimal("num"));
                }
            }
        }
        return jsonObjectList.get(target - 1);
    }

    /**
     * 计算水质等级
     */
    private Integer computeWaterLevel(CollectCommunityRiverDTO riverDTO) {
        Integer nowCategory = 0;
        List<JSONObject> jsonObjectList = baseWaterQualityStandardsService.findByDictWaterAndWaterType(riverDTO.getDictWaterQualityStandards().getId(), "1");
        if (jsonObjectList.size() > 2) {
            for (int i = 0; i < jsonObjectList.size() - 1; i++) {
                if ("< ≤".contains(jsonObjectList.get(i).getString("symbol")) && "< ≤".contains(jsonObjectList.get(i + 1).getString("symbol"))) {
                    // 后<前
                    if (jsonObjectList.get(i + 1).getBigDecimal("num").compareTo(jsonObjectList.get(i).getBigDecimal("num")) == -1) {
                        jsonObjectList.get(i).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(i + 1).getString("symbol")));
                        jsonObjectList.get(i).put("num1", jsonObjectList.get(i + 1).getBigDecimal("num"));
                    }
                    // 后>前
                    if (jsonObjectList.get(i + 1).getBigDecimal("num").compareTo(jsonObjectList.get(i).getBigDecimal("num")) == 1 && i > 0) {
                        jsonObjectList.get(i).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(i - 1).getString("symbol")));
                        jsonObjectList.get(i).put("num1", jsonObjectList.get(i - 1).getBigDecimal("num"));
                    }
                }
                if (("< ≤".contains(jsonObjectList.get(i).getString("symbol")) && "> ≥".contains(jsonObjectList.get(i + 1).getString("symbol"))) ||
                        ("> ≥".equals(jsonObjectList.get(i).getString("symbol")) && "< ≤".contains(jsonObjectList.get(i + 1).getString("symbol")))) {
                    // 后==前
                    if (jsonObjectList.get(i + 1).getBigDecimal("num").compareTo(jsonObjectList.get(i).getBigDecimal("num")) == 0 && i > 0) {
                        jsonObjectList.get(i).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(i - 1).getString("symbol")));
                        jsonObjectList.get(i).put("num1", jsonObjectList.get(i - 1).getBigDecimal("num"));
                    }
                }
                if ("> ≥".equals(jsonObjectList.get(i).getString("symbol")) && "> ≥".contains(jsonObjectList.get(i + 1).getString("symbol"))) {
                    // 后<前
                    if (jsonObjectList.get(i + 1).getBigDecimal("num").compareTo(jsonObjectList.get(i).getBigDecimal("num")) == -1 && i > 0) {
                        jsonObjectList.get(i).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(i - 1).getString("symbol")));
                        jsonObjectList.get(i).put("num1", jsonObjectList.get(i - 1).getBigDecimal("num"));
                    }
                    // 后>前
                    if (jsonObjectList.get(i + 1).getBigDecimal("num").compareTo(jsonObjectList.get(i).getBigDecimal("num")) == 1) {
                        jsonObjectList.get(i).put("symbol1", SymbolEnum.getReCode(jsonObjectList.get(i + 1).getString("symbol")));
                        jsonObjectList.get(i).put("num1", jsonObjectList.get(i + 1).getBigDecimal("num"));
                    }
                }
            }
        }
        for (JSONObject json : jsonObjectList) {
            if (json.getBigDecimal("num1") != null && json.getString("symbol1") != null) {
                if ("< >".contains(json.getString("symbol1")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num1")) == SymbolEnum.getValue(json.getString("symbol1"))) {
                    if ("< >".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) == SymbolEnum.getValue(json.getString("symbol"))) {
                        if (nowCategory < json.getInteger("level")) {
                            nowCategory = json.getInteger("level");
                        }
                    }
                    if ("≤ ≥".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) != SymbolEnum.getValue(json.getString("symbol"))) {
                        if (nowCategory < json.getInteger("level")) {
                            nowCategory = json.getInteger("level");
                        }
                    }
                }
                if ("≤ ≥".contains(json.getString("symbol1")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num1")) != SymbolEnum.getValue(json.getString("symbol1"))) {
                    if ("< >".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) == SymbolEnum.getValue(json.getString("symbo"))) {
                        if (nowCategory < json.getInteger("level")) {
                            nowCategory = json.getInteger("level");
                        }
                    }
                    if ("≤ ≥".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) != SymbolEnum.getValue(json.getString("symbo"))) {
                        if (nowCategory < json.getInteger("level")) {
                            nowCategory = json.getInteger("level");
                        }
                    }
                }
            } else {
                if ("< >".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) == SymbolEnum.getValue(json.getString("symbol"))) {
                    if (nowCategory < json.getInteger("level")) {
                        nowCategory = json.getInteger("level");
                    }
                }
                if ("≤ ≥".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) != SymbolEnum.getValue(json.getString("symbol"))) {
                    if (nowCategory < json.getInteger("level")) {
                        nowCategory = json.getInteger("level");
                    }
                }
            }
        }
        return nowCategory;
    }

    /**
     * 计算黑臭等级
     */
    private Integer computeBlackLevel(CollectCommunityRiverDTO riverDTO) {
        Integer blackLevel = 0;
        // 黑臭程度
        List<JSONObject> jsonObjectList = baseWaterQualityStandardsService.findByDictWaterAndWaterType(riverDTO.getDictWaterQualityStandards().getId(), "2");
        for (JSONObject json : jsonObjectList) {
            if ("< >".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) == SymbolEnum.getValue(json.getString("symbol"))) {
                if (blackLevel < json.getInteger("level")) {
                    blackLevel = json.getInteger("level");
                }
            }
            if ("≤ ≥".contains(json.getString("symbol")) && riverDTO.getParamNum().compareTo(json.getBigDecimal("num")) != SymbolEnum.getValue(json.getString("symbol"))) {
                if (blackLevel < json.getInteger("level")) {
                    blackLevel = json.getInteger("level");
                }
            }
        }
        return blackLevel;
    }

    /**
     * 判断数据是否重复
     */
    private Boolean isRepeat(String riverId, Date collectTime) {
        BaseRiverDTO riverDTO = baseRiverService.findDTOById(riverId);
        if (riverDTO.getFacility() == null) {
            return false;
        } else {
            return riverDTO.getFacility().getCollectTime().getTime() == collectTime.getTime();
        }
    }

    /**
     * 判断数据是否都是零
     */
    private Boolean isAllZero(JSONArray jsonArray) {
        List<Object> csValues = jsonArray.stream().filter(detail -> ((JSONObject) detail).getBigDecimal("cs_value").compareTo(BigDecimal.ZERO) != 0).collect(Collectors.toList());
        if (csValues.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String addInvalidData(JSONObject result) {
        String deviceCode = result.getString("deviceCode");
        BaseDeviceBaseDTO baseDeviceBaseDTO = baseDeviceService.findByDeviceNum(deviceCode);
        if (baseDeviceBaseDTO != null && baseDeviceBaseDTO.getBaseRiver() != null) {
            CollectInvalidDataHistoryDTO dto = new CollectInvalidDataHistoryDTO();
            dto.setBaseRiver(baseDeviceBaseDTO.getBaseRiver());
            dto.setBaseDevice(baseDeviceBaseDTO);
            dto.setInvalidType(result.getInteger("invalidType"));
            dto.setSourceData(result.getString("sourceData"));
            dto = collectInvalidDataHistoryService.saveByDTO(dto);
            if (dto.getId() != null) {
                return "success";
            }
        }
        return "error";
    }
}
