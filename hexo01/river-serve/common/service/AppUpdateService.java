package com.general.common.service;

import com.general.common.dto.AppUpdateDTO;
import com.general.common.dto.PushConfigDTO;
import com.general.common.entity.AppUpdate;
import com.general.common.entity.PushConfig;

/**
 * APP更新
 */
public interface AppUpdateService extends CommonService<AppUpdate, AppUpdateDTO>{
    AppUpdateDTO findNew();
}
