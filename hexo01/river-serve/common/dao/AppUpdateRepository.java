package com.general.common.dao;

import com.general.common.entity.AppUpdate;
import com.general.common.entity.PushTarget;
import com.general.core.repository.MyRepository;

/**
 * App更新Repository
 */
public interface AppUpdateRepository extends MyRepository<AppUpdate, String> {
    AppUpdate findTopByIsDelFalseOrderByCreateTimeDesc();
}
