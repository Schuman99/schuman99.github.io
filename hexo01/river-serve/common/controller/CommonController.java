package com.general.common.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.general.common.enums.*;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.util.UserUtils;
import com.general.logic.base.dto.*;
import com.general.logic.base.entity.*;
import com.general.logic.base.service.*;
import com.general.logic.dict.dto.*;
import com.general.logic.dict.entity.DictDeviceType;
import com.general.logic.dict.entity.DictIssueType;
import com.general.logic.dict.entity.DictRiverLevel;
import com.general.logic.dict.entity.DictWaterQualityStandards;
import com.general.logic.dict.service.*;
import com.general.logic.message.dto.MessageNewKnowledgeDTO;
import com.general.logic.message.entity.MessageNewKnowledge;
import com.general.logic.message.service.MessageNewKnowledgeService;
import com.general.logic.sys.dto.LoginAccountDTO;
import com.general.logic.sys.dto.SysRoleDTO;
import com.general.logic.sys.entity.SysRole;
import com.general.logic.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author fengw
 */
@Controller
public class CommonController {
        @Autowired
        BaseAccountService accountService;
        @Autowired
        BaseRiverService riverService;
        @Autowired
        BaseStreetService streetService;
        @Autowired
        BaseCommunityService communityService;
        @Autowired
        DictProvincesService provincesService;
        @Autowired
        BasePlatformService platformService;
        @Autowired
        DictIssueTypeService issueTypeService;
        @Autowired
        SysRoleService sysRoleService;
        @Autowired
        DictDeviceTypeService dictDeviceTypeService;
        @Autowired
        DictWaterQualityStandardsService waterQualityStandarsService;
        @Autowired
        DictRiverLevelService riverLevelService;
        @Autowired
        BaseDeviceService baseDeviceService;
        @Autowired
        private BasePolderService basePolderService;
        @Autowired
        MessageNewKnowledgeService messageNewKnowledgeService;

        // Match everything without a suffix (so not a static resource)
        @RequestMapping(value = "/{path:[^\\.]*}")
        public String redirect(HttpServletRequest request, HttpServletResponse response) {
                // Forward to home page so that route is preserved.
                return "forward:/";
        }

        @RequestMapping(value = "/404", produces = "application/json;charset=UTF-8")
        @ResponseBody
        public AjaxResult pageNotFound() {
                return AjaxResult.createErrorResult(MessageCode.PAGE_NOT_FOUND);
        }

        /**
         * 获取参数列表，一般用于获取下拉选项
         * <p>注意通过service查询的TO列表不要包含太多数据</Dp>
         *
         * @param params
         * @return
         * @author 张渊
         */
        @PostMapping("/api/getParams")
        @ResponseBody
        public JSONObject getParams(@RequestBody List<JSONObject> params) {
                Map<String, List<Object[]>> options = new HashMap<>();
                int pageSize = 30;
                Pageable pageable = null;
                for (JSONObject param : params) {
                        String type = param.getString("type");
                        JSONObject query = null;
                        String queryString = null;
                        if (param.containsKey("param")) {
                                if (param.get("param") instanceof String) {
                                        queryString = param.getString("param");
                                } else {
                                        query = param.getJSONObject("param");
                                }
                        }
                        // 是否只查询ids数据，常用于只读下拉
                        boolean onlySelectIds = param.containsKey("onlySelectIds") ? param.getBoolean("onlySelectIds") : false;
                        List<Object[]> list = new ArrayList<>();
                        // 用户列表
                        if ("BaseAccount".equals(type)) {
                                List<BaseAccountBaseDTO> dtoList;
                                if (!onlySelectIds) {
                                        BaseAccount searchParam = query == null ? new BaseAccount() : query.toJavaObject(BaseAccount.class);
                                        Sort sort = new Sort(Sort.Direction.ASC, "username");
                                        pageable = PageRequest.of(0, pageSize, sort);
                                        Page<BaseAccountBaseDTO> page = accountService.findDTOPage(searchParam, pageable, BaseAccountBaseDTO.class);
                                        dtoList = page.getContent();
                                } else {
                                        dtoList = new ArrayList<>();
                                }
                                // 查询 不存在的 id 返回给前台
                                JSONArray idArray = param.getJSONArray("ids");
                                if (idArray != null) {
                                        List<String> ids = idArray.toJavaList(String.class);
                                        List<BaseAccountBaseDTO> baseDTOS = accountService.findDTOByIds(ids, BaseAccountBaseDTO.class);
                                        if (baseDTOS != null && baseDTOS.size() > 0) {
                                                HashSet<BaseAccountBaseDTO> dtos = new HashSet<>(dtoList);
                                                dtos.addAll(baseDTOS);
                                                dtoList = new ArrayList<>(dtos);
                                        }
                                }
                                for (BaseAccountBaseDTO dto : dtoList) {
                                        if (dto != null) {
                                                list.add(new Object[]{dto.getUsername(), dto.getId(), dto});
                                        }
                                }
                        } else if ("BaseRiver".equals(type)) {
                                List<BaseRiverBaseDTO> dtoList = null;
                                if (!UserUtils.isAdmin()) {
                                        dtoList = UserUtils.getCurrentUserDTO().getBaseRiverList();
                                } else if (!onlySelectIds) {
                                        BaseRiver searchParam = query == null ? new BaseRiver() : query.toJavaObject(BaseRiver.class);
                                        // 选择河道
                                        Sort sort = new Sort(Sort.Direction.ASC, "riverName");
                                        pageable = PageRequest.of(0, pageSize, sort);
                                        Page<BaseRiverBaseDTO> page = riverService.findDTOPage(searchParam, pageable, BaseRiverBaseDTO.class);
                                        dtoList = page.getContent();
                                } else {
                                        dtoList = new ArrayList<>();
                                }
                                // 查询不存在的 id 返回给前台
                                JSONArray idArray = param.getJSONArray("ids");
                                if (idArray != null) {
                                        List<String> ids = idArray.toJavaList(String.class);
                                        List<BaseRiverBaseDTO> baseDTOS = riverService.findDTOByIds(ids, BaseRiverBaseDTO.class);
                                        if (baseDTOS != null && baseDTOS.size() > 0) {
                                                HashSet<BaseRiverBaseDTO> dtos = new HashSet<>(dtoList);
                                                dtos.addAll(baseDTOS);
                                                dtoList = new ArrayList<>(dtos);
                                        }
                                }
                                for (BaseRiverBaseDTO dto : dtoList) {
                                        if (dto != null) {
                                                list.add(new Object[]{dto.getRiverName(), dto.getId(), dto});
                                        }
                                }
                        } else if ("BaseRiverMin".equals(type)) {
                                String deviceType = query.getString("deviceType");
                                List<BaseRiverBaseDTO> dtoList = riverService.findAllRiverByDeviceType(deviceType);
                                JSONArray idArray = param.getJSONArray("ids");
                                if (idArray != null) {
                                        List<String> ids = idArray.toJavaList(String.class);
                                        List<BaseRiverBaseDTO> baseDTOS = riverService.findDTOByIds(ids, BaseRiverBaseDTO.class);
                                        if (baseDTOS != null && baseDTOS.size() > 0) {
                                                HashSet<BaseRiverBaseDTO> dtos = new HashSet<>(dtoList);
                                                dtos.addAll(baseDTOS);
                                                dtoList = new ArrayList<>(dtos);
                                        }
                                }
                                for (BaseRiverBaseDTO dto : dtoList) {
                                        if (dto != null) {
                                                list.add(new Object[]{dto.getRiverName(), dto.getId(), dto});
                                        }
                                }
                        } else if ("BaseRiverAll".equals(type)) {
                                BaseRiver searchParam = query == null ? new BaseRiver() : query.toJavaObject(BaseRiver.class);
                                Sort sort = new Sort(Sort.Direction.ASC, "riverName");
                                List<BaseRiverBaseDTO> dtoList = riverService.findDTOByEntity(searchParam, sort, BaseRiverBaseDTO.class);
                                for (BaseRiverBaseDTO dto : dtoList) {
                                        if (dto != null) {
                                                list.add(new Object[]{dto.getRiverName(), dto.getId(), dto});
                                        }
                                }
                        } else if ("BaseStreet".equals(type)) {
                                BaseStreet searchParam = query == null ? new BaseStreet() : query.toJavaObject(BaseStreet.class);
                                // 选择街道
                                Sort sort = new Sort(Sort.Direction.ASC, "streetName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                Page<BaseStreetBaseDTO> page = streetService.findDTOPage(searchParam, pageable, BaseStreetBaseDTO.class);
                                for (BaseStreetBaseDTO dto : page.getContent()) {
                                        list.add(new Object[]{dto.getStreetName(), dto.getId(), dto});
                                }
                        } else if ("BasePolder".equals(type)) {
                                BasePolder searchParam = query == null ? new BasePolder() : query.toJavaObject(BasePolder.class);
                                // 选择圩区
                                Sort sort = new Sort(Sort.Direction.ASC, "polderName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                Page<BasePolderDTO> page = basePolderService.findDTOPage(searchParam, pageable, BasePolderDTO.class);
                                for (BasePolderDTO dto : page.getContent()) {
                                        list.add(new Object[]{dto.getPolderName(), dto.getId(), dto});
                                }
                        } else if ("BaseCommunity".equals(type)) {
                                BaseCommunity searchParam = query == null ? new BaseCommunity() : query.toJavaObject(BaseCommunity.class);
                                // 选择社区
                                Sort sort = new Sort(Sort.Direction.ASC, "communityName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                Page<BaseCommunityBaseDTO> page = communityService.findDTOPage(searchParam, pageable, BaseCommunityBaseDTO.class);
                                for (BaseCommunityBaseDTO dto : page.getContent()) {
                                        list.add(new Object[]{dto.getCommunityName(), dto.getId(), dto});
                                }
                        } else if ("BasePlatform".equals(type)) {
                                // 选择角色
                                Sort sort = new Sort(Sort.Direction.ASC, "platformName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                Page<BasePlatformDTO> page = platformService.findDTOPage(new BasePlatform(), pageable, BasePlatformDTO.class);
                                for (BasePlatformDTO dto : page.getContent()) {
                                        list.add(new Object[]{dto.getPlatformName(), dto.getId(), dto});
                                }
                        } else if ("BaseRole".equals(type)) {
                                // 选择角色
                                Sort sort = new Sort(Sort.Direction.ASC, "roleName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                Page<SysRoleDTO> page = sysRoleService.findDTOPage(new SysRole(), pageable, SysRoleDTO.class);
                                for (SysRoleDTO dto : page.getContent()) {
                                        list.add(new Object[]{dto.getRoleName(), dto.getId(), dto});
                                }
                        } else if ("TagetAccount".equals(type)) {
                                // 事项的的目标用户  根据  上报 交办 类型 来返回  不同的用户
                                // 上报 2   交办 1
                                String selectType = query.getString("type");
                                List<BaseAccountDTO> dtoList = new ArrayList<>();
                                LoginAccountDTO dto = UserUtils.getLoginAccountDTO();
                                if (dto != null) {
                                        String userId = dto.getId();
                                        if ("1".equals(selectType)) {
                                                dtoList = accountService.getDownRoleAccount(userId, 2);
                                        } else if ("2".equals(selectType)) {
                                                dtoList = accountService.getUpRoleAccount(userId, 2);
                                        }
                                }
                                for (BaseAccountDTO accountDTO : dtoList) {
                                        if (accountDTO != null) {
                                                list.add(new Object[]{accountDTO.getUsername(), accountDTO.getId(), dto});
                                        }
                                }
                        } else if ("DeviceType".equals(type)) {
                                // 设备类型
                                DictDeviceType searchParam = query == null ? new DictDeviceType() : query.toJavaObject(DictDeviceType.class);
                                Sort sort = new Sort(Sort.Direction.ASC, "createTime");
                                pageable = PageRequest.of(0, pageSize, sort);
                                List<DictDeviceTypeDTO> dtoList = dictDeviceTypeService.findDTOPage(searchParam, pageable, DictDeviceTypeDTO.class).getContent();
                                for (DictDeviceTypeDTO dto : dtoList) {
                                        list.add(new Object[]{dto.getTypeName(), dto.getId(), dto});
                                }
                        } else if ("BaseDevice".equals(type)) {
                                // 设备
                                BaseDevice searchParam = query == null ? new BaseDevice() : query.toJavaObject(BaseDevice.class);
                                Sort sort = new Sort(Sort.Direction.ASC, "deviceName");
                                pageable = PageRequest.of(0, pageSize, sort);
                                List<BaseDeviceBaseDTO> dtoList = baseDeviceService.findDTOPage(searchParam, pageable, BaseDeviceBaseDTO.class).getContent();
                                // 查询 不存在的 id 返回给前台
                                JSONArray idArray = param.getJSONArray("ids");
                                if (idArray != null) {
                                        List<String> ids = idArray.toJavaList(String.class);
                                        List<BaseDeviceBaseDTO> baseDTOS = baseDeviceService.findDTOByIds(ids, BaseDeviceBaseDTO.class);
                                        if (baseDTOS != null && baseDTOS.size() > 0) {
                                                HashSet<BaseDeviceBaseDTO> dtos = new HashSet<>(dtoList);
                                                dtos.addAll(baseDTOS);
                                                dtoList = new ArrayList<>(dtos);
                                        }
                                }
                                for (BaseDeviceBaseDTO dto : dtoList) {
                                        list.add(new Object[]{dto.getDeviceName(), dto.getId(), dto});
                                }
                        } else if ("DictIssueType".equals(type)) {
                                DictIssueType searchParam = query == null ? new DictIssueType() : query.toJavaObject(DictIssueType.class);
                                // 选择问题类型
                                List<DictIssueTypeDTO> dtoList = issueTypeService.findDTOByEntity(searchParam, null, DictIssueTypeDTO.class);
                                for (DictIssueTypeDTO dto : dtoList) {
                                        list.add(new Object[]{dto.getIssueName(), dto.getId(), dto});
                                }
                        } else if ("DictWaterQualityStandards".equals(type)) {
                                // 选择水质标准参数
                                List<DictWaterQualityStandardsBaseDTO> dtoList = waterQualityStandarsService.findDTOByEntity(new DictWaterQualityStandards(), null, DictWaterQualityStandardsBaseDTO.class);
                                for (DictWaterQualityStandardsBaseDTO dto : dtoList) {
                                        list.add(new Object[]{dto.getParamName(), dto.getId(), dto});
                                }
                        } else if ("DictRiverLevel".equals(type)) {
                                // 选择河道级别
                                List<DictRiverLevelDTO> dtoList = riverLevelService.findDTOByEntity(new DictRiverLevel(), null, DictRiverLevelDTO.class);
                                for (DictRiverLevelDTO dto : dtoList) {
                                        list.add(new Object[]{dto.getLevelName(), dto.getId(), dto});
                                }
                        } else if ("AssessmentTarget".equals(type)) {
                                // 考核目标/现状类别
                                for (AssessmentTargetEnum dto : AssessmentTargetEnum.values()) {
                                        list.add(new Object[]{dto.getLabel(), dto.getValue()});
                                }
                        } else if ("PushTargetTypeEnum".equals(type)) {
//                推送目标类型
                                for (PushTargetTypeEnum dto : PushTargetTypeEnum.values()) {
                                        list.add(new Object[]{dto.getName(), dto.getType()});
                                }
                        } else if ("PatrolMessageStateEnum".equals(type)) {
//                巡查状态
                                for (PatrolMessageStateEnum dto : PatrolMessageStateEnum.values()) {
                                        list.add(new Object[]{dto.getName(), dto.getType()});
                                }
                        } else if ("SymbolEnum".equals(type)) {
                                // 考核目标/现状类别
                                for (SymbolEnum dto : SymbolEnum.values()) {
                                        list.add(new Object[]{dto.getCode(), dto.getCode()});
                                }
                        } else if ("ItemLevel".equals(type)) {
                                // 事项等级
                                for (ItemLevelEnum dto : ItemLevelEnum.values()) {
                                        list.add(new Object[]{dto.getLevelName(), dto.getLevel()});
                                }
                        } else if ("ItemState".equals(type)) {
                                // 事项状态
                                for (ItemStateEnum dto : ItemStateEnum.values()) {
                                        // lable, value, dto
                                        list.add(new Object[]{dto.getName(), dto.getState()});
                                }
                        } else if ("ItemSourceTypeEnum".equals(type)) {
                                // 事项追踪来源类型
                                for (ItemTraceSourceTypeEnum dto : ItemTraceSourceTypeEnum.values()) {
                                        list.add(new Object[]{dto.getName(), dto.getState()});
                                }
                        } else if ("ToItemSourceTypeEnum".equals(type)) {
                                // 事项来源类型
                                for (ItemManageSourceTypeEnum dto : ItemManageSourceTypeEnum.values()) {
                                        list.add(new Object[]{dto.getName(), dto.getState()});
                                }
                        } else if ("Province,City,District".contains(type)) {
                                List<DictProvincesWithParentDTO> dtoList;
                                // 查询省市区
                                if (queryString != null) {
                                        dtoList = provincesService.findByParent(queryString, DictProvincesWithParentDTO.class);
                                } else {
                                        ProvincesTypeEnum provincesType = null;
                                        switch (type) {
                                                case "Province":
                                                        provincesType = ProvincesTypeEnum.PROVINCE;
                                                        break;
                                                case "City":
                                                        provincesType = ProvincesTypeEnum.CITY;
                                                        break;
                                                case "District":
                                                        provincesType = ProvincesTypeEnum.DISTRICT;
                                                        break;
                                                default:
                                        }
                                        dtoList = provincesService.findByType(provincesType, DictProvincesWithParentDTO.class);
                                }
                                if (dtoList != null) {
                                        for (DictProvincesWithParentDTO dto : dtoList) {
                                                list.add(new Object[]{dto.getName(), dto.getId(), dto});
                                        }
                                }
                        } else if ("MessageNewKnowledge".equals(type)) {
                                // 知识库建议
                                MessageNewKnowledge knowledge = new MessageNewKnowledge();
                                knowledge.setType("1");
                                List<MessageNewKnowledgeDTO> dtoList = messageNewKnowledgeService.findDTOByEntity(new MessageNewKnowledge(), null, MessageNewKnowledgeDTO.class);
                                for (MessageNewKnowledgeDTO dto : dtoList) {
                                        if (dto.getType().equals("1")) {
                                                list.add(new Object[]{dto.getTitle(), dto.getId(), dto});
                                        }
                                }
                        }
                        options.put(type, list);
                }
                return (JSONObject) JSONObject.toJSON(AjaxResult.createSuccessResult(options));
        }
}
