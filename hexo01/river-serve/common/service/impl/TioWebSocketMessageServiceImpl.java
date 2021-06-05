package com.general.common.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.general.common.constant.TioConstants;
import com.general.common.dto.PushMessageBaseDTO;
import com.general.common.dto.PushTargetDTO;
import com.general.common.enums.WebSocketDestinationEnum;
import com.general.common.service.WebSocketMessageService;
import com.general.core.gexin.entity.AppNotify;
import com.general.core.gexin.service.GexinService;
import com.general.core.tio.TioConfigProperties;
import com.general.core.tio.TioWebsocketStarter;
import com.general.logic.base.dto.BaseAccountDTO;
import com.general.logic.base.dto.BaseAccountItemBaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsResponse;

import java.util.List;

/**
 * Tio WebSocket消息推送实现
 */
@Service
public class TioWebSocketMessageServiceImpl implements WebSocketMessageService {
    @Autowired
    TioWebsocketStarter tioWebsocketStarter;
    @Autowired
    TioConfigProperties properties;
    @Autowired
    GexinService gexinService;
    @Override
    public void sendToAll(WebSocketDestinationEnum destination, Object payload) {
        if (properties.isEnable()) {
            WsResponse response = WsResponse.fromText(getResponseData(destination, payload), TioConstants.CHARSET);
            Tio.sendToAll(getServerGroupContext(), response);
        }
    }

    @Override
    public void sendToUser(String userId, WebSocketDestinationEnum destination, Object payload) {
        if (properties.isEnable()) {
            WsResponse response = WsResponse.fromText(getResponseData(destination, payload), TioConstants.CHARSET);
            Tio.sendToUser(getServerGroupContext(), userId, response);
            if (payload instanceof PushTargetDTO) {
                gexinService.pushMessageToSingle(userId,getAppNotify(payload));
            }
        }
    }

    @Override
    public void sendToUser(String user, WebSocketDestinationEnum destination, Object payload, Boolean sendToApp) {
        sendToUser(user,destination,payload);
        if (sendToApp) {
            if (payload instanceof PushTargetDTO) {
                gexinService.pushMessageToSingle(user,getAppNotify(payload));
            }
        }
    }

    @Override
    public void sendToUser(String userId, WebSocketDestinationEnum destination, Object payload, String cid) {
        if (properties.isEnable()) {
            WsResponse response = WsResponse.fromText(getResponseData(destination, payload, cid), TioConstants.CHARSET);
            Tio.sendToUser(getServerGroupContext(), userId, response);
        }
    }

    @Override
    public void sendToUser(List<BaseAccountDTO> userList, WebSocketDestinationEnum destination, Object payload) {
        if (properties.isEnable()) {
            if (userList != null && userList.size() > 0) {
                for (BaseAccountDTO account: userList) {
                    sendToUser(account.getId(), destination, payload);
                }
            }
        }
    }

    @Override
    public void sendToUser(List<BaseAccountDTO> userList, WebSocketDestinationEnum destination, Object payload, String cid) {
        if (properties.isEnable()) {
            if (userList != null && userList.size() > 0) {
                for (BaseAccountDTO account: userList) {
                    sendToUser(account.getId(), destination, payload, cid);
                }
            }
        }
    }

    @Override
    public void sendToGroup(WebSocketDestinationEnum destination, Object payload) {
        if (properties.isEnable()) {
            WsResponse response = WsResponse.fromText(getResponseData(destination, payload), TioConstants.CHARSET);
            Tio.sendToGroup(getServerGroupContext(), destination.getValue(), response);
        }
    }

    @Override
    public void sendToRole(String roleId, WebSocketDestinationEnum destination, Object payload) {

    }

    private ServerGroupContext getServerGroupContext() {
        return tioWebsocketStarter.getServerGroupContext();
    }

    private String getResponseData(WebSocketDestinationEnum destination, Object payload) {
        return getResponseData(destination, payload, null);
    }


    private String getResponseData(WebSocketDestinationEnum destination, Object payload, String cid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("destination", destination.getValue());
        jsonObject.put("data", payload);
        if (!StringUtils.isEmpty(cid)) {
            jsonObject.put("cid", cid);
        }
        return jsonObject.toJSONString();
    }

    /**
     *  获取 app 推送的内容
     * @param payload
     * @return
     */
    private AppNotify getAppNotify(Object payload) {
        if (payload instanceof PushTargetDTO) {
            PushTargetDTO dto = (PushTargetDTO) payload;
            AppNotify appNotify = new AppNotify();
            PushMessageBaseDTO messageDTO = dto.getPushMessage();
            BaseAccountItemBaseDTO accountMinDTO = dto.getBaseAccount();
            appNotify.setContent(messageDTO.getContent());
            appNotify.setTitle(messageDTO.getContent());
            return  appNotify;
        }
        return  null;
    }
}
