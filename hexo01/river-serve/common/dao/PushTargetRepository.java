package com.general.common.dao;

import com.general.common.entity.PushTarget;
import com.general.core.repository.MyRepository;
import com.general.logic.base.entity.BaseAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 推送目标信息Repository
 */
public interface PushTargetRepository extends MyRepository<PushTarget, String> {

    @Query("from PushTarget target " +
            "where target.isDel = false " +
            "and target.baseAccount.id = ?1 " +
            "and target.pushMessage.messageType in ?2 " +
            " and target.pushMessage.isDel = false")
    Page<PushTarget> findPushTarget(String userId, List<String> messageType , Pageable pageable);
    Integer countByBaseAccountAndStatusAndIsDelIsFalse(BaseAccount baseAccount, Integer status);
}
