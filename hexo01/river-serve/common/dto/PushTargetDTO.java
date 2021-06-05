package com.general.common.dto;

import com.general.logic.base.dto.BaseAccountItemBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ysj
 * @create 2018-05-29
 **/

@Getter
@Setter
public class PushTargetDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -7462298448179923199L;

    /**
     * 账户基本信息
     */
    private BaseAccountItemBaseDTO baseAccount;

    /**
     * 推送消息
     */
    private PushMessageBaseDTO pushMessage;

    /**
     * 消息状态 （0：未读|1：已读|2：失效）
     */
    private Integer status;

    public PushTargetDTO() {
    }
}
