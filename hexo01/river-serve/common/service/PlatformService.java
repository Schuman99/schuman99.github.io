package com.general.common.service;

import com.alibaba.fastjson.JSONObject;
import com.general.logic.base.dto.BaseDeviceBaseDTO;
import com.general.logic.warn.dto.WarnDeviceDTO;

import java.util.Date;

public interface PlatformService {

    String addInvalidData(JSONObject result);

    String addPlatForm(JSONObject result);

    void addItemManage(BaseDeviceBaseDTO baseDevice, String sourceId, String sourceType, Date time, String paramName, String manageContent);

    WarnDeviceDTO addWarnDevice(BaseDeviceBaseDTO deviceBaseDTO, String faultExplain, Date faultTime);
}

