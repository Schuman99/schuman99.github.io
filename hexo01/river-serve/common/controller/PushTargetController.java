package com.general.common.controller;

import com.general.common.dto.PushTargetDTO;
import com.general.common.entity.PushTarget;
import com.general.common.enums.MessageStateEnum;
import com.general.common.service.PushTargetService;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.page.PageParam;
import com.general.core.query.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 河道信息管理
 *
 * @author ysj
 * @create 2018-04-26
 **/
@RestController
@RequestMapping("/api/pushTarget")
public class PushTargetController extends BaseController<PushTarget> {

    @Autowired
    PushTargetService service;
    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 查询所有
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findAll")
    public AjaxResult findAll(@RequestBody QueryParam<PushTarget> queryParam) {
        return super.findAll(service, queryParam);
    }

    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 分页查询
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findPage")
    public AjaxResult findPage(@RequestBody PageParam<PushTarget> pageParam) {
       return super.findPage(service, pageParam);
    }

    /**
     * BaseRiver4GisDTO
     *
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 查询单个
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findOne")
    public AjaxResult findOne(String id) {
      return super.findOne(service, id);
    }
    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 保存一个
     * @date 2018/4/27 9:49
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody PushTargetDTO dto) {
        return super.save(service, dto);
    }

    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 修改单个
     * @date 2018/4/27 9:49
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody PushTargetDTO dto) {
        return super.update(service, dto);
    }

    /**
     * 修改消息状态为已读
     * @param id
     * @return
     */
    @GetMapping("/updateRead")
    public AjaxResult updateRead(String id) {
        if (StringUtils.isEmpty(id)) {
            return AjaxResult.createErrorResult(MessageCode.UPDATE_FAILED);
        } else  {
            PushTargetDTO dto = service.findDTOById(id);
            dto.setStatus(MessageStateEnum.READ.getType());
            return super.update(service, dto);
        }
    }

    /**
     * 删除单个
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public AjaxResult deleteOne(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);
        return super.delete(service, list);
    }

    /**
     * 删除多个
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    public AjaxResult delete(@RequestBody List<String> ids) {
        return super.delete(service, ids);
    }

}
