package com.general.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author ysj
 * @create 2018-06-01
 **/
@Getter
@Setter
public class PushMessageAopDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -4662273038293902605L;

    /**
     * 消息类型<br/>
     * approval: 审批消息
     * notice: 公告|通知
     */
    private String messageType;

    /**
     * 消息目标类型<br/>
     * user: 用户
     * role: 角色
     * all: 所有用户
     */
    private String targetType;

    /**
     * 消息目标对象ID
     */
    private String targetId;

    /**
     * 关联数据ID
     */
    private String relationId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息推送配置的目标
     */
    private List<PushTargetBaseDTO> pushTargetList;
}
