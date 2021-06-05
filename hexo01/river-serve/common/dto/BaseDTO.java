package com.general.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 基础DTO
 * @author Spark
 * @create 2018-04-25
 **/
@Getter
@Setter
public class BaseDTO {
    private String id;

    /**
     * 是否删除
     */
    private Boolean isDel;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;
}
