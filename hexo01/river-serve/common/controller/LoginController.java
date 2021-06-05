package com.general.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.general.common.enums.AttachTypeEnum;
import com.general.core.config.shiro.util.AccountNoRoleException;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.util.UserUtils;
import com.general.logic.base.dto.BaseAccountBaseDTO;
import com.general.logic.base.dto.BaseAttachDTO;
import com.general.logic.base.service.BaseAttachService;
import com.general.logic.sys.dto.LoginAccountDTO;
import com.general.logic.base.service.BaseAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录Controller
 * @author Spark
 */
@Api(value = "登录服务")
@RestController
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    @Autowired
    public BaseAccountService baseAccountService;
    @Autowired
    public  BaseAttachService baseAttachService;
    /**
     * \/login POST请求，处理登录请求
     * @param request
     * @return
     * @author fengw
     * @create 2016年11月16日
     */
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "String"),
            @ApiImplicitParam(name = "remeberme", value = "自动登录", paramType = "Boolean")
    })
    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResult loginIn(HttpServletRequest request, @RequestBody JSONObject param) {
        String username = param.getString("phone");
        String password = param.getString("password");
        String rememberMe = param.getString("rememberMe");
        String platform = param.getString("platform");
        rememberMe = rememberMe == null ? "true" : rememberMe;

        AjaxResult ajaxResult = null;
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                if (!StringUtils.isEmpty(rememberMe) && "true".equals(rememberMe)) {
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
                subject.login(token);
                if (platform != null) {
                    BaseAccountBaseDTO loginAccountDTO = UserUtils.getSinglePointAccountDTO();
                    ajaxResult = AjaxResult.createSuccessResult(loginAccountDTO, "登录成功");
                    logger.info(platform + "登录!");
                } else {
                    LoginAccountDTO loginAccountDTO = UserUtils.getLoginAccountDTO();
                    if (loginAccountDTO != null && loginAccountDTO.getBelongPlatform() == null) {
                        ajaxResult = AjaxResult.createSuccessResult(loginAccountDTO, "登录成功");
                        logger.info("账号密码登录：" + username);
                    } else {
                        ajaxResult = AjaxResult.createErrorResult(MessageCode.ACCOUNT_UNKNOWN_ERROR, "非本平台用户");
                        logger.info("登陆平台错误：" + username);
                    }
                }
            } catch (UnknownAccountException | IncorrectCredentialsException e) {
                ajaxResult = AjaxResult.createErrorResult(MessageCode.ACCOUNT_INFO_ERROR);
            } catch (LockedAccountException e) {
                ajaxResult = AjaxResult.createErrorResult(MessageCode.ACCOUNT_LOCKED);
            } catch (AccountNoRoleException e) {
                ajaxResult = AjaxResult.createErrorResult(MessageCode.ACCOUNT_NO_ROLE);
            } catch (AuthenticationException e) {
                ajaxResult = AjaxResult.createErrorResult(MessageCode.ACCOUNT_UNKNOWN_ERROR);
                e.printStackTrace();
            }
        }
        return ajaxResult;
    }
    /**
     * 登出
     * @return
     * @author fengw
     * @create 2016年11月16日
     */
    @RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public AjaxResult logout() {
        SecurityUtils.getSubject().logout();
        return AjaxResult.createSuccessResultWithCode(MessageCode.ACCOUNT_LOGOUT);
    }
    /**
     * 获取当前用户的账户信息
     * @return
     * @author fengw
     * @create 2016年11月30日
     */
    @RequestMapping("/api/getUserinfo")
    @ResponseBody
    public AjaxResult getUserinfo() {
        LoginAccountDTO dto = UserUtils.getLoginAccountDTO();
//        if (dto !=null) {
//            List<BaseAttachDTO> attachs =  baseAttachService.findAttach(dto.getId() ,AttachTypeEnum.TOU_XIANG.getType());
//            if (attachs != null && !attachs.isEmpty()) {
//                dto.setBaseAttachs(attachs);
//            }
//        }
        if (dto != null) {
            return AjaxResult.createSuccessResult(dto);
        }
        return AjaxResult.createErrorResult(MessageCode.ACCOUNT_NO_LOGIN);
    }
    /**
     * 校验令牌
     * @return
     * @author wangqiang
     * @create 2019年12月24日
     */
    @RequestMapping("/validateToken")
    @ResponseBody
    public AjaxResult validateToken() {
        LoginAccountDTO dto = UserUtils.getLoginAccountDTO();
        if (dto != null) {
            return AjaxResult.createSuccessResult(null, "令牌有效");
        }
        return AjaxResult.createErrorResult(MessageCode.ACCOUNT_NO_LOGIN, "令牌无效");
    }
}
