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
public class PushTargetBaseDTO extends BaseDTO implements Serializable{
    private static final long serialVersionUID = 5786560349323145242L;

    private BaseAccountItemBaseDTO baseAccount;

    public PushTargetBaseDTO() {
    }
}
