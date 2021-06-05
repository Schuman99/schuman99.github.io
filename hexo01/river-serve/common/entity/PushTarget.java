package com.general.common.entity;

import com.general.logic.base.entity.BaseAccount;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 推送信息关联用户
 * @author ysj
 * @create 2018-05-29
 **/
@Getter
@Setter
@Entity
@Table
@DynamicUpdate
public class PushTarget extends  BaseEntity implements Serializable{
    private static final long serialVersionUID = -1193339708562464426L;
    /**
     * 推送消息
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PushMessage pushMessage;

    /**
     * 推送用户
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private  BaseAccount baseAccount;

    /**
     * 消息状态
     * （0：未读|1：已读|2：失效）
     */
    private Integer status;

    public void setStatus(Integer status) {
        if (status == null) {
            this.status = 0;
        }else {
            this.status = status;
        }
    }
}
