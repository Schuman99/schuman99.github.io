package com.general.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.general.logic.base.entity.BaseAccount;
import com.general.logic.sys.entity.SysModule;
import com.general.logic.sys.entity.SysRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 推送信息配置
 * @author ysj
 * @create 2018-06-01
 **/
@Getter
@Setter
@Entity
@Table
@DynamicUpdate
public class PushConfig extends  BaseEntity implements Serializable{
    private static final long serialVersionUID = -5729776998096543760L;

    /**
     * 推送目标类型 0用户 1角色 2所有
     */
    @Column(columnDefinition = "varchar(1) comment '目标类型'")
    private String targetType;

    /**
     * 推送用户
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "push_config_account",
            joinColumns = {@JoinColumn(name = "push_config_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")})
    @JSONField(serialize = false)
    private List<BaseAccount> baseAccounts;

    /**
     * 推送角色
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "push_config_role",
            joinColumns = {@JoinColumn(name = "push_config_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @JSONField(serialize = false)
    private List<SysRole> sysRoles;

}
