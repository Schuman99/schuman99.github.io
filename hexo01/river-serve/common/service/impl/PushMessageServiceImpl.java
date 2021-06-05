package com.general.common.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.general.common.constant.ModuleConstants;
import com.general.common.dao.PushMessageRepository;
import com.general.common.dao.PushTargetRepository;
import com.general.common.dto.PushMessageDTO;
import com.general.common.dto.PushTargetDTO;
import com.general.common.entity.PushMessage;
import com.general.common.entity.PushTarget;
import com.general.common.enums.PushMessageTypeEnum;
import com.general.common.enums.PushTargetStatusEnum;
import com.general.common.enums.PushTargetTypeEnum;
import com.general.common.enums.WebSocketDestinationEnum;
import com.general.common.service.PushMessageService;
import com.general.common.service.PushTargetService;
import com.general.common.service.WebSocketMessageService;
import com.general.core.gexin.service.GexinService;
import com.general.logic.base.entity.BaseAccount;
import com.general.logic.base.service.BaseAccountService;
import com.general.logic.sys.dto.SysModuleDTO;
import com.general.logic.sys.entity.SysModule;
import com.general.logic.sys.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 推送信息Service
 * @author fengw
 * @create 2017年2月17日
 */
@Service
public class PushMessageServiceImpl extends CommonServiceImpl<PushMessage, PushMessageDTO> implements PushMessageService {

    @Autowired
    PushMessageRepository repository;
    @Autowired
    WebSocketMessageService messageService;
    @Autowired
    BaseAccountService accountService;
    @Autowired
    PushTargetService pushTargetService;
    @Autowired
    GexinService gexinService;
    @Autowired
    PushTargetRepository pushTargetRepository;
    @Autowired
    SysModuleService moduleService;



    @Override
    public boolean addPushMessageToUser(PushMessageTypeEnum messageType, String userId, String content, String moduleName, String relationId) {
        return addPushMessageToUser(messageType, userId, content, moduleName, relationId, false);
    }

    @Override
    public boolean addPushMessageToUser(PushMessageTypeEnum messageType, List<String> userIds, String content, String moduleName, String relationId) {
        return addPushMessageToUser(messageType, userIds, content, moduleName, relationId, false);
    }

    @Override
    public boolean addPushMessageToUser(PushMessageTypeEnum messageType, String userId, String content, String moduleName, String relationId, boolean preventRepeat) {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        return addPushMessage(messageType, PushTargetTypeEnum.USER, userIds, content, moduleName, relationId, preventRepeat);
    }

    @Override
    public boolean addPushMessageToUser(PushMessageTypeEnum messageType, List<String> userIds, String content, String moduleName, String relationId, boolean preventRepeat) {
        return addPushMessage(messageType, PushTargetTypeEnum.USER, userIds, content, moduleName, relationId, preventRepeat);
    }

    @Override
    public boolean addPushMessageToRole(PushMessageTypeEnum messageType, String roleName, String content, String moduleName, String relationId) {
        return addPushMessageToRole(messageType, roleName, content,moduleName, relationId,  false);
    }

    @Override
    public boolean addPushMessageToRole(PushMessageTypeEnum messageType, List<String> roleNames, String content, String moduleName, String relationId) {
        return addPushMessageToRole(messageType, roleNames, content,moduleName, relationId,  false);
    }

    @Override
    public boolean addPushMessageToRole(PushMessageTypeEnum messageType, String roleName, String content, String moduleName, String relationId, boolean preventRepeat) {
        List<String> roleNames = new ArrayList<>();
        roleNames.add(roleName);
        return addPushMessage(messageType, PushTargetTypeEnum.ROLE, roleNames, content, moduleName, relationId, preventRepeat);
    }

    @Override
    public boolean addPushMessageToRole(PushMessageTypeEnum messageType, List<String> roleNames, String content, String moduleName, String relationId, boolean preventRepeat) {
        return addPushMessage(messageType, PushTargetTypeEnum.ROLE, roleNames, content, moduleName, relationId, preventRepeat);
    }

    @Override
    public boolean addPushMessageToAll(PushMessageTypeEnum messageType, String content, String moduleName, String relationId) {
        return addPushMessage(messageType, PushTargetTypeEnum.ALL, null, content, moduleName, relationId, false);
    }

    private boolean addPushMessage(PushMessageTypeEnum messageType, PushTargetTypeEnum targetType, List<String> targetIds, String content, String moduleName, String relationId, boolean preventRepeat) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setMessageType(messageType.getType());
        pushMessage.setTargetType(targetType.getType());
        pushMessage.setContent(content);
        pushMessage.setRelationId(relationId);
        SysModuleDTO module = moduleService.findByModuleAlias(moduleName);
        if (module != null){
            pushMessage.setSysModule(new SysModule(module.getId()));
        }
        pushMessage.setIsDel(false);
        List<PushTarget> list = new ArrayList<>();
        List<BaseAccount> userList = null;
        Set<BaseAccount> accountSet = null;
        switch (targetType) {
            case USER:
                for (String targetId : targetIds) {
                    BaseAccount account = new BaseAccount();
                    account.setId(targetId);
                    PushTarget pushTarget = new PushTarget();
                    pushTarget.setPushMessage(pushMessage);
                    pushTarget.setBaseAccount(account);
                    pushTarget.setStatus(0);
                    pushTarget.setIsDel(false);
                    list.add(pushTarget);
                }
                break;
            case ROLE:
                for (String targetId : targetIds) {
                    accountSet.addAll(accountService.findByRoleName(targetId));
                }
                userList = new ArrayList<>(accountSet);
            case ALL:
                if (targetType == PushTargetTypeEnum.ALL) {
                    BaseAccount accountDTO = new BaseAccount();
                    accountDTO.setLocked(false);
                    userList = accountService.findByEntity(accountDTO);
                }
                if (userList != null && userList.size() > 0) {
                    for (BaseAccount user: userList) {
                        PushTarget pushTarget1 = new PushTarget();
                        pushTarget1.setPushMessage(pushMessage);
                        pushTarget1.setBaseAccount(user);
                        pushTarget1.setIsDel(false);
                        if ((preventRepeat && checkRepeat(pushTarget1)) || !preventRepeat) {
                            list.add(pushTarget1);
                        }
                    }
                }
                break;
        }
        pushMessage.setPushTargetList(list);
        pushMessage = save(pushMessage);
        if (pushMessage != null && !StringUtils.isEmpty(pushMessage.getId())) {
            sendPushMessage(getDTO(pushMessage));
            return true;
        }
        return false;
    }
    @Override
    public boolean readPushMessage(String pushTargetId) {
        if (!StringUtils.isEmpty(pushTargetId)) {
            PushTargetDTO pushTargetDTO = pushTargetService.findDTOById(pushTargetId);
            if (pushTargetDTO != null) {
                pushTargetDTO.setStatus(PushTargetStatusEnum.READ.getValue());
                int i = pushTargetService.updateByDTO(pushTargetDTO);
                if (i > 0) {
                    this.sendPushTarget(pushTargetDTO);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean readPushMessageByReleation(String releationId) {
        int i = 0;
        if (!StringUtils.isEmpty(releationId)) {
            List<PushTargetDTO> list = pushTargetService.findByRelationIdAndStatus(releationId, PushTargetStatusEnum.UNREAD);
            if (list != null && list.size() > 0) {
                for (PushTargetDTO dto: list) {
                    dto.setStatus(PushTargetStatusEnum.READ.getValue());
                    i += pushTargetService.updateByDTO(dto);
                    this.sendPushTarget(dto);
                }
            }
        }
        return i > 0;
    }

    @Override
    public boolean disabledPushMessage(String id) {
        if (!StringUtils.isEmpty(id)) {
            PushMessage message = findById(id);
            if (message != null && message.getPushTargetList() != null && message.getPushTargetList().size() > 0) {
                for (PushTarget target: message.getPushTargetList()) {
                    target.setStatus(PushTargetStatusEnum.DISABLED.getValue());
                    sendPushTarget(pushTargetService.getDTO(target));
                }
                int row = pushTargetService.update(message.getPushTargetList());
                if (row > 0)
                    return true;
            }
        }
        return false;
    }
    @Override
    public boolean disabledPushMessageByRelationId(String relationId) {
        if (!StringUtils.isEmpty(relationId)) {
            List<PushMessageDTO> list = getDTOList(repository.findByRelationId(relationId));
            if (list != null && !list.isEmpty()) {
                for (PushMessageDTO push: list) {
//					push.setStatus(PushMessageConstants.STATUS_DISABLED);
                    disabledPushMessage(push.getId());
                }
                int row = delete(list);
                if (row > 0)
                    return true;
            }
        }
        return false;
    }
    @Override
    public Page<PushTargetDTO> getUserMessage(String userId, Pageable pageable) {
        return pushTargetService.findByUserIdAndStatus(userId, PushTargetStatusEnum.UNREAD, pageable);
    }

    @Override
    public Page<PushMessageDTO> getNoticeMessage(Pageable pageable) {
        return getDTOPage(repository.findByMessageTypeOrderByCreateTimeDesc(PushMessageTypeEnum.NOTICE.getName(), pageable), pageable);
    }

    @Override
    public void sendPushMessage(PushMessageDTO dto) {
//        PushMessageTypeEnum messageTypeEnum = PushMessageTypeEnum.getEnumByValue(dto.getMessageType());
//		switch (messageTypeEnum) {
//			case WARNING:
        if (dto.getPushTargetList() != null && dto.getPushTargetList().size() > 0) {
            sendPushTarget(dto.getPushTargetList());
        }
//				break;
//			case NOTICE:
//				messageService.sendToAll(WebSocketDestinationEnum.PUSH_MESSAGE, dto);
//				break;
//		}
    }

    @Override
    public void sendPushTarget(PushTargetDTO pushTarget) {
        messageService.sendToUser(pushTarget.getBaseAccount().getId(), WebSocketDestinationEnum.PUSH_MESSAGE, pushTarget);
    }

    @Override
    public void sendPushTarget(List<PushTargetDTO> list) {
        if (list != null && !list.isEmpty()) {
            for (PushTargetDTO dto: list) {
                sendPushTarget(dto);
            }
        }
    }

    @Override
    public boolean checkRepeat(PushTarget pushTarget) {
        PushMessage pushMessage = pushTarget.getPushMessage();
        PushMessage searchParam = new PushMessage();
        searchParam.setRelationId(pushMessage.getRelationId());
        searchParam.setContent(pushMessage.getContent());
        searchParam.setMessageType(pushMessage.getMessageType());
        searchParam.setSysModule(pushMessage.getSysModule());
        List<PushMessage> pushMessages = this.findByEntity(searchParam, true);
        if (pushMessages != null && pushMessages.size() > 0) {
            for (PushMessage message: pushMessages) {
                for (PushTarget target: message.getPushTargetList()) {
                    if (target.getBaseAccount().getId().equals(pushTarget.getBaseAccount().getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
