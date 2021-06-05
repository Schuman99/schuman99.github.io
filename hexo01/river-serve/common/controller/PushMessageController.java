package com.general.common.controller;

import com.general.common.dto.PushMessageDTO;
import com.general.common.entity.PushMessage;
import com.general.common.service.PushMessageService;
import com.general.core.msg.AjaxResult;
import com.general.core.page.PageParam;
import com.general.core.query.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 河道信息管理
 * @author ysj
 * @create 2018-04-26
 **/
@RestController
@RequestMapping("/api/pushMessage")
public class PushMessageController extends BaseController<PushMessage> {

    @Autowired
    PushMessageService service;

    /**
     * @description 查询所有
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findAll")
    public AjaxResult findAll(@RequestBody QueryParam<PushMessage> queryParam) {
        return  super.findAll(service, queryParam);
    }

    /**
     * @description 分页查询
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findPage")
    public AjaxResult findPage(@RequestBody PageParam<PushMessage> pageParam) {
        return super.findPage(service, pageParam);
    }

    /**
     * @description 查询单个
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findOne")
    public AjaxResult findOne(String id) {
      return super.findOne(service, id);
    }
    /**
     * @description 保存一个
     * @date 2018/4/27 9:49
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody PushMessageDTO dto) {
        return super.save(service, dto);
    }

    /**
     * @description 修改多个
     * @date 2018/4/27 9:49
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody PushMessageDTO dto) {
        return super.update(service, dto);
    }

    /**
     * @description 删除多个
     * @date 2018/4/27 9:49
     */
    @RequestMapping("/delete")
    public AjaxResult deleteOne(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        return super.delete(service, list);
    }

    /**
     * @description 删除多个
     * @date 2018/4/27 9:49
     */
    @RequestMapping("/deleteBatch")
    public AjaxResult delete(@RequestBody List<String> ids) {
        return super.delete(service, ids);
    }

}
