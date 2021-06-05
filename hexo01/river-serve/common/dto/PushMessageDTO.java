package com.general.common.dto;

import com.general.logic.sys.dto.SysModuleMessageBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 推送信息DTO
 * @author Spark
 */
@Getter
@Setter
public class PushMessageDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 4310499124068735401L;

    /**
     * 消息类型<br/>
     * approval: 审批消息
     * notice: 公告|通知
     *
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
     * 关联菜单id
     */
    private SysModuleMessageBaseDTO sysModule;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息推送配置的目标
     */
    private List<PushTargetDTO> pushTargetList;

    public PushMessageDTO() {
        super();
    }

    public PushMessageDTO(String id) {
        this.setId(id);
    }

    public PushMessageDTO(String messageType, String targetType, String targetId, String relationId, String content) {
        super();
        this.messageType = messageType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.relationId = relationId;
        this.content = content;
    }
}
