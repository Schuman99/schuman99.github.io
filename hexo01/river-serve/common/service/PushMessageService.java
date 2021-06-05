package com.general.common.service;

import com.general.common.constant.ModuleConstants;
import com.general.common.dto.PushMessageDTO;
import com.general.common.dto.PushTargetDTO;
import com.general.common.entity.PushMessage;
import com.general.common.entity.PushTarget;
import com.general.common.enums.PushMessageTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 推送信息Service
 * @author fengw
 * @create 2018年1月1日
 */
public interface PushMessageService extends CommonService<PushMessage, PushMessageDTO> {
    /**
     * 添加目标类型为用户的推送消息
     * @param messageType 消息类型
     * @param userId 用户ID
     * @param content 消息内容
     * @param relationId 相关ID
     * @return
     */
    boolean addPushMessageToUser(PushMessageTypeEnum messageType, String userId, String content, String moduleName, String relationId);

    /**
     * 添加目标类型为用户的推送消息
     * @param messageType 消息类型
     * @param userIds 用户ID
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @return
     */
    boolean addPushMessageToUser(PushMessageTypeEnum messageType, List<String> userIds, String content, String moduleName, String relationId);

    /**
     * 添加目标类型为用户的推送消息
     * @param messageType 消息类型
     * @param userId 用户ID
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @param preventRepeat 防止重复发送
     * @return
     */
    boolean addPushMessageToUser(PushMessageTypeEnum messageType, String userId, String content, String moduleName, String relationId, boolean preventRepeat);

    /**
     * 添加目标类型为用户的推送消息
     * @param messageType 消息类型
     * @param userIds 用户ID
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @param preventRepeat 防止重复发送
     * @return
     */
    boolean addPushMessageToUser(PushMessageTypeEnum messageType, List<String> userIds, String content, String moduleName, String relationId, boolean preventRepeat);

    /**
     * 添加目标类型为角色的推送消息
     * @param messageType 消息类型
     * @param roleName 角色名
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @return
     */
    boolean addPushMessageToRole(PushMessageTypeEnum messageType, String roleName, String content, String moduleName, String relationId);

    /**
     * 添加目标类型为角色的推送消息
     * @param messageType 消息类型
     * @param roleNames 角色名
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @return
     */
    boolean addPushMessageToRole(PushMessageTypeEnum messageType, List<String> roleNames, String content, String moduleName, String relationId);

    /**
     * 添加目标类型为角色的推送消息
     * @param messageType 消息类型
     * @param roleName 角色名
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @param preventRepeat 防止重复发送
     * @return
     */
    boolean addPushMessageToRole(PushMessageTypeEnum messageType, String roleName, String content, String moduleName, String relationId, boolean preventRepeat);

    /**
     * 添加目标类型为角色的推送消息
     * @param messageType 消息类型
     * @param roleNames 角色名
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @param preventRepeat 防止重复发送
     * @return
     */
    boolean addPushMessageToRole(PushMessageTypeEnum messageType, List<String> roleNames, String content, String moduleName, String relationId, boolean preventRepeat);

    /**
     * 添加推送至所有用户的推送消息
     * @param messageType 消息类型
     * @param content 消息内容
     * @param moduleName 模块名
     * @param relationId 相关ID
     * @return
     */
    boolean addPushMessageToAll(PushMessageTypeEnum messageType, String content, String moduleName, String relationId);
    /**
     * 将推送消息改为已读状态
     * @param pushTargetId
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    boolean readPushMessage(String pushTargetId);

    /**
     * 将推送消息改为已读状态
     * @param releationId 相关ID
     * @return
     */
    boolean readPushMessageByReleation(String releationId);
    /**
     * 将推送消息改为失效状态
     * @param id 推送消息ID
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    boolean disabledPushMessage(String id);
    /**
     * 将相关联的审批推送消息改为失效状态
     * @param relationId 相关联内容ID
     * @return
     * @author fengw
     * @create 2017年2月18日
     */
    boolean disabledPushMessageByRelationId(String relationId);
    /**
     * 获取指定用户消息<br>
     * @param userId 用户ID
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    Page<PushTargetDTO> getUserMessage(String userId, Pageable pageable);
    /**
     * 获取公告|通知
     * @return
     * @author fengw
     * @create 2017年2月17日
     */
    Page<PushMessageDTO> getNoticeMessage(Pageable pageable);
    /**
     * 发送推送信息
     * @param dto
     * @author fengw
     * @create 2017年2月18日
     */
    void sendPushMessage(PushMessageDTO dto);

    /**
     * 发送推送消息至用户
     * @param pushTarget
     */
    void sendPushTarget(PushTargetDTO dto);

    /**
     * 发送推送信息
     * @param dto
     * @author fengw
     * @create 2017年2月18日
     */
    void sendPushTarget(List<PushTargetDTO> list);

    /**
     * 判断消息是否已给用户推送过
     * @param pushTarget
     * @return
     */
    boolean checkRepeat(PushTarget pushTarget);
}
