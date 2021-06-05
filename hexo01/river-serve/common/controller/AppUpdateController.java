package com.general.common.controller;

import com.general.common.dto.AppUpdateDTO;
import com.general.common.entity.AppUpdate;
import com.general.common.service.AppUpdateService;
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
 *app 更新
 *
 * @author ysj
 * @create 2018-04-26
 **/
@RestController
@RequestMapping("/api/appUpdate")
public class AppUpdateController extends BaseController<AppUpdate> {

    @Autowired
    AppUpdateService service;
    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 查询所有
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findAll")
    public AjaxResult findAll(@RequestBody QueryParam<AppUpdate> queryParam) {
        return  super.findAll(service, queryParam);
    }



    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 分页查询
     * @date 2018/4/27 9:48
     */
    @RequestMapping("/findPage")
    public AjaxResult findPage(@RequestBody PageParam<AppUpdate> pageParam) {
       return super.findPage(service,pageParam);
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
    public AjaxResult save(@RequestBody AppUpdateDTO dto) {
        return super.save(service, dto);
    }

    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 修改多个
     * @date 2018/4/27 9:49
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody AppUpdateDTO dto) {
        return super.update(service, dto);
    }
    /**
     * @return com.general.core.msg.AjaxResult
     * @author ysj
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
     * @return com.general.core.msg.AjaxResult
     * @author ysj
     * @description 删除多个
     * @date 2018/4/27 9:49
     */
    @RequestMapping("/deleteBatch")
    public AjaxResult delete(@RequestBody List<String> ids) {
        return super.delete(service, ids);
    }

    @RequestMapping("/findNew")
    public AjaxResult findNew() {
        try {
            AppUpdateDTO appUpdateDTO = service.findNew();
            if (appUpdateDTO == null) {
                return  AjaxResult.createErrorResult(MessageCode.QUERY_FAILED);
            } else {
                return  AjaxResult.createSuccessResult(appUpdateDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  AjaxResult.createErrorResult(MessageCode.QUERY_FAILED);
        }
    }

}
