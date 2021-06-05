package com.general.common.dto;

import com.general.logic.sys.dto.SysModuleBaseDTO;
import com.general.logic.sys.dto.SysModuleDTO;
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
public class PushMessageBaseDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 4310499124068735401L;

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
     * 模块信息
     */
    private SysModuleBaseDTO sysModule;

    /**
     * 模块路径
     */
    private String  path;

    /**
     * 2018-07-18 由于 app视频这块 还未完善 故app 的消息 不返回视频预警消息
     * 0 app 1 web
     */
    private String source;

    public PushMessageBaseDTO() {
        super();
    }

    public PushMessageBaseDTO(String messageType, String targetType, String targetId, String relationId, String content) {
        super();
        this.messageType = messageType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.relationId = relationId;
        this.content = content;
    }

    public PushMessageBaseDTO(String id, String content,String messageType) {
        this.setId(id);
        this.content = content;
        this.messageType = messageType;
    }

    public String getPath() {
        if ( this.sysModule!=null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder =  new StringBuilder("/" +  sysModule.getModuleAlias());
            if (sysModule.getParentModule()!= null) {
                stringBuilder = new StringBuilder("/" +   sysModule.getParentModule().getModuleAlias() + stringBuilder.toString() );
                if (sysModule.getParentModule().getParentModule() !=null) {
                    stringBuilder =  new StringBuilder("/" + sysModule.getParentModule().getParentModule().getModuleAlias() + stringBuilder.toString() );
                }
            }
            path = stringBuilder.toString();
        }
        return path;
    }
}
