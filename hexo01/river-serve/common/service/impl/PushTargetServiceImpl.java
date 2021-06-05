package com.general.common.service.impl;

import com.general.common.dao.PushTargetRepository;
import com.general.common.dto.PushTargetDTO;
import com.general.common.entity.PushMessage;
import com.general.common.entity.PushTarget;
import com.general.common.enums.MessageStateEnum;
import com.general.common.enums.PushTargetStatusEnum;
import com.general.common.service.PushTargetService;
import com.general.logic.base.entity.BaseAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * 推送目标
 */
@Service
public class PushTargetServiceImpl extends CommonServiceImpl<PushTarget, PushTargetDTO> implements PushTargetService {

    @Autowired
    PushTargetService service;

    @Override
    @Transactional
    public Page<PushTargetDTO> findByUserIdAndStatus(String userId, PushTargetStatusEnum statusEnum, Pageable pageable) {
        PushTarget target = new PushTarget();
        target.setStatus(statusEnum.getValue());
        BaseAccount account = new BaseAccount();
        account.setId(userId);

        target.setBaseAccount(account);
        return findDTOPage(target, pageable, true);
    }

    @Override
    public List<PushTargetDTO> findByRelationIdAndStatus(String relationId, PushTargetStatusEnum status) {
        PushTarget target = new PushTarget();
        target.setStatus(status.getValue());
        PushMessage message = new PushMessage();
        message.setRelationId(relationId);
        target.setPushMessage(message);
        return this.findDTOByEntity(target);
    }
}
