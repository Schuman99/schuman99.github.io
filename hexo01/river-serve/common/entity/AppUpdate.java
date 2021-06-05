package com.general.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ysj
 * @create 2018-08-22
 **/
@Getter
@Setter
@Entity
@Table
@DynamicUpdate
public class AppUpdate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1208123561767659376L;

    /**
     * 版本code用于比较是否更新
     */
    @Column(columnDefinition = " int(11) comment '版本编号' ")
    private  Integer versionCode;

    /**
     * 用于显示
     */
    @Column(columnDefinition = " varchar(500) comment '版本号' ")
    private  String versionNumber;

    /**
     * 该版本描述用于告诉用户修改了什么
     */
    @Column(columnDefinition = " varchar(500) comment '描述' ")
    private  String appDescribe;

    /**
     * apk下载地址
     */
    @Column(columnDefinition = "varchar(500) comment '下载地址' ")
    private String  downloadUrl;

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getAppDescribe() {
        return appDescribe;
    }

    public void setAppDescribe(String appDescribe) {
        this.appDescribe = appDescribe;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
