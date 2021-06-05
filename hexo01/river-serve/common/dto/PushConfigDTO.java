package com.general.common.dto;

import com.general.logic.base.dto.BaseAccountBaseDTO;
import com.general.logic.base.dto.BaseAccountItemBaseDTO;
import com.general.logic.sys.dto.SysModuleBaseDTO;
import com.general.logic.sys.dto.SysRoleBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 消息推送配置
 * @author ysj
 * @create 2018-06-01
 **/
@Getter
@Setter
public class PushConfigDTO extends BaseDTO implements Serializable{

    /**
     * 目标类型 0用户 1角色 2所有
     */
    private String targetType;

    /**
     * 推送用户
     */
    private List<BaseAccountItemBaseDTO> baseAccounts;

    /**
     * 推送角色
     */
    private List<SysRoleBaseDTO> sysRoles;

    public PushConfigDTO() {
    }

    public PushConfigDTO(String id) {
        this.setId(id);
    }

}
