package com.general.common.controller;

import com.general.common.dto.PushConfigDTO;
import com.general.common.entity.PushConfig;
import com.general.common.service.PushConfigService;
import com.general.core.msg.AjaxResult;
import com.general.core.page.PageParam;
import com.general.core.query.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 评论
 * @author nikai
 * @create 2018/6/4
 */
@RestController
@RequestMapping("/api/pushconfig")
public class PushConfigController extends BaseController<PushConfig> {

    @Autowired
    private PushConfigService service;

    /**
     * 查询列表
     */
    @PostMapping("/findAll")
    public AjaxResult findAll(@RequestBody QueryParam<PushConfig> queryParam) {
        return super.findAll(service, queryParam);
    }

    /**
     * 分页查询
     */
    @PostMapping("/findPage")
    public AjaxResult findPage(@RequestBody PageParam<PushConfig> pageParam) {
        return super.findPage(service, pageParam);
    }

    /**
     * 添加一个
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody PushConfigDTO dto) {
        return super.save(service, dto);
    }

    /**
     * 详情
     */
    @RequestMapping("/findOne")
    public AjaxResult findOne(String id) {
        return super.findOne(service, id);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody PushConfigDTO dto) {
        dto.getBaseAccounts();
        dto.getSysRoles();
        return super.update(service, dto);

    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody List<String> ids) {
        return super.delete(service, ids);
    }
}
