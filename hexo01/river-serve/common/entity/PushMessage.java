package com.general.common.entity;

import com.general.logic.sys.entity.SysModule;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 推送信息
 * @author Spark
 */
@Getter
@Setter
@Entity
@Table
@DynamicUpdate
public class PushMessage extends BaseEntity  implements Serializable {
    private static final long serialVersionUID = 6083060380772051447L;

    /**
     * 消息类型<br/>
     * approval: 审批消息
     * notice: 公告|通知
     * PushMessageTypeEnum 枚举中的类型
     */
    private String messageType;

    /**
     * 消息目标类型<br/>
     * user: 用户0
     * role: 角色1
     * all: 所有用户2
     */
    private String targetType;

    /**
     * 消息目标对象ID
     */
    private String targetId;

    private String relationId;

    /**
     * 关联菜单ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private SysModule sysModule;

    /**
     * 消息内容
     */
    private String content;

    // 2018-07-18 由于 app视频这块 还未完善 故app 的消息 不返回视频预警消息
    // 0 app 1 web
    private String source;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "pushMessage", orphanRemoval = true)
    private List<PushTarget> pushTargetList;

    @PrePersist
    public void onCreate() {
        if (this.targetType == null) {
            this.targetType = "2";
        }
    }

    public PushMessage() {
    }

    public PushMessage(String id) {
        this.setId(id);
    }
}
