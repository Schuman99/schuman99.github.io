package com.general.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.general.common.service.PlatformService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author An
 * @create 2018-06-12
 **/
@RestController
@RequestMapping(value = "/api/platform")
public class PlatformController {
    private Logger logger = LogManager.getLogger(PlatformController.class);
    @Autowired
    private PlatformService service;

    /**
     * 传感器数据接收
     */
    @PostMapping("/pushData")
    public String pushData(HttpServletRequest request){
        String data = request.getParameter("data");
        JSONObject result = JSONObject.parseObject(data);
        if (result == null) {
            return "error";
        } else {
            logger.info("传感器数据为:" + result);
            return service.addPlatForm(result);
        }
    }

    /**
     * 无效数据接收
     */
    @PostMapping("/invalidData")
    public String invalidData(HttpServletRequest request){
        String data = request.getParameter("data");
        JSONObject result = JSONObject.parseObject(data);
        if (result == null) {
            return "error";
        } else {
            logger.info("无效数据为:" + result);
            return service.addInvalidData(result);
        }
    }
}
