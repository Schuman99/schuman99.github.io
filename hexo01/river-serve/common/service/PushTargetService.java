package com.general.common.service;

import com.general.common.dto.PushTargetDTO;
import com.general.common.entity.PushTarget;
import com.general.common.enums.PushTargetStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 推送目标
 */
public interface PushTargetService extends CommonService<PushTarget, PushTargetDTO>{

    Page<PushTargetDTO> findByUserIdAndStatus(String userId, PushTargetStatusEnum statusEnum, Pageable pageable);

    List<PushTargetDTO> findByRelationIdAndStatus(String relationId, PushTargetStatusEnum status);
}
