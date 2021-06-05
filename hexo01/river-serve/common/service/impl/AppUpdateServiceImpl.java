package com.general.common.service.impl;

import com.general.common.dao.AppUpdateRepository;
import com.general.common.dto.AppUpdateDTO;
import com.general.common.dto.PushConfigDTO;
import com.general.common.entity.AppUpdate;
import com.general.common.entity.PushConfig;
import com.general.common.service.AppUpdateService;
import com.general.common.service.PushConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * APP更新
 */
@Service
public class AppUpdateServiceImpl extends CommonServiceImpl<AppUpdate, AppUpdateDTO> implements AppUpdateService {
    @Autowired
    AppUpdateRepository appUpdateRepository;
    @Override
    public AppUpdateDTO findNew() {
        return getDTO(appUpdateRepository.findTopByIsDelFalseOrderByCreateTimeDesc());
    }
}
