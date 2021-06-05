package com.general.common.service;

import com.general.common.enums.WebSocketDestinationEnum;
import com.general.logic.base.dto.BaseAccountDTO;

import java.util.List;

/**
 * WebSocket发送消息接口
 */
public interface WebSocketMessageService {
    /**
     * 给所有用户发送消息
     * @param destination
     * @param payload
     * @author fengw
     * @create 2017年2月15日
     */
    void sendToAll(WebSocketDestinationEnum destination, Object payload);
    /**
     * 给指定用户发送消息
     * @param user
     * @param destination
     * @param payload
     * @author fengw
     * @create 2017年2月15日
     */
    void sendToUser(String user, WebSocketDestinationEnum destination, Object payload);

    /**
     * 给指定用户发送消息
     * @param user userID
     * @param destination 请求路径
     * @param payload 数据模型
     * @param sendToApp 是否 到送给app
     */
    void sendToUser(String user, WebSocketDestinationEnum destination, Object payload, Boolean sendToApp);

    /**
     * 给指定用户发送消息
     * @param user
     * @param destination
     * @param payload
     * @param cid 前台回调ID
     */
    void sendToUser(String user, WebSocketDestinationEnum destination, Object payload, String cid);

    /**
     * 给指定用户集合发送消息
     * @param userList
     * @param destination
     * @param payload
     * @author fengw
     * @create 2017年2月15日
     */
    void sendToUser(List<BaseAccountDTO> userList, WebSocketDestinationEnum destination, Object payload);

    /**
     * 给指定用户集合发送消息
     * @param userList
     * @param destination
     * @param payload
     * @param cid 前台回调ID
     */
    void sendToUser(List<BaseAccountDTO> userList, WebSocketDestinationEnum destination, Object payload, String cid);

    /**
     * 给群组发送消息
     * @param destination
     * @param payload
     */
    void sendToGroup(WebSocketDestinationEnum destination, Object payload);
    /**
     * 给指定角色发送消息
     * @param roleId
     * @param destination
     * @param payload
     * @author fengw
     * @create 2017年2月15日
     */
    @Deprecated
    void sendToRole(String roleId, WebSocketDestinationEnum destination, Object payload);
}
