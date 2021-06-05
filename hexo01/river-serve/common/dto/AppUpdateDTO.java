package com.general.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ysj
 * @create 2018-08-22
 **/
@Getter
@Setter
public class AppUpdateDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -7789474310022613005L;
    /**
     * 版本code用于比较是否更新
     */
    private  Integer versionCode;
    /**
     * 用于显示
     */
    private  String versionNumber;
    /**
     * 该版本描述用于告诉用户修改了什么
     */
    private  String appDescribe;
    /**
     * apk下载地址
     */
    private String  downloadUrl;
}
