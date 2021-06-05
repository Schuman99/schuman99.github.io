package com.general.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hmily
 * @create 2018-04-25
 **/
@Getter
@Setter
@MappedSuperclass
public class BaseEntity{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",	strategy = "uuid")
    @Column(columnDefinition = "varchar(32) comment 'ID'")
    private String id;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss,SSS")
    private Date createTime;
    @Transient
    private Date startCreateTime;
    @Transient
    private Date endCreateTime;


    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @JSONField(format = "yyyy-MM-dd HH:mm:ss,SSS")
    @Column(columnDefinition = "TIMESTAMP comment '修改时间'")
    private Date updateTime;

    /**
     * 是否删除
     */
    @Column(columnDefinition = "bit(1) default false comment '是否删除'")
    private Boolean isDel;
}
