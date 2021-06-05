package com.general.common.controller;

import com.general.core.config.shiro.util.AccountNoRoleException;
import com.general.core.config.shiro.util.WeburlConfig;
import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import com.general.core.util.UserUtils;
import com.general.logic.base.dto.BaseAccountBaseDTO;
import com.general.logic.base.service.*;
import com.general.logic.dict.service.*;
import com.general.logic.message.service.MessageNewKnowledgeService;
import com.general.logic.sys.dto.LoginAccountDTO;
import com.general.logic.sys.service.SysRoleService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 其他平台单点登录对接
 *
 * @author fengw
 * @date 2020-11-05
 */
@Controller
public class SSOController {
        private static final Logger logger = LogManager.getLogger(SSOController.class);
        @Autowired
        BaseAccountService accountService;
        @Autowired
        WeburlConfig weburlConfig;
        // Match everything without a suffix (so not a static resource)

        /**
         * 单点登陆
         * cid : 登陆的用户名
         * ms : 10位系统时间
         * ha : cid+ms+混淆码通过md5加密截取前15位
         *
         * @return String
         * @throws java.io.IOException
         */
        @RequestMapping(value = "/singleLogin", method = RequestMethod.GET)
        public String singleLogin(HttpServletRequest request, @RequestParam String cid, @RequestParam String ms, @RequestParam String ha) {
                String loginUrl = "redirect:" + weburlConfig.getPublicUrl() + "/#/login";
                //5分钟超时
                if (StringUtils.isEmpty(ms) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(ha)) {
                        return loginUrl;
                }
                Integer now = (int) (System.currentTimeMillis() / 1000);
                String ptco = "5R3%3r@4TY5#1ft";
                String key = cid + ms + ptco;
                //String value1 = CodeUtil.encode(key,CodeUtil.MD5).substring(0,15);
                // String value = value1.toUpperCase();
                String value = DigestUtils.md5Hex(key).substring(0, 15).toUpperCase();
                //匹配失败
                if (!value.equals(ha.toUpperCase())) {
                        logger.warn("大数据平台登录-匹配失败");
                        return loginUrl;
                }
                if (now - Integer.parseInt(ms) > 300) {
                        logger.warn("大数据平台登录-校验码超时");
                        return loginUrl;
                }
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(cid, key);
                try {
                        token.setRememberMe(true);
                        subject.login(token);
                        LoginAccountDTO loginAccountDTO = UserUtils.getLoginAccountDTO();
                        if (loginAccountDTO != null && loginAccountDTO.getBelongPlatform() != null
                                && loginAccountDTO.getBelongPlatform().getPlatformName().equals("大数据平台")) {
                                logger.warn("大数据平台登录：" + cid + "未设置平台");
                                return loginUrl;
                        } else {
                                logger.info("大数据平台登陆成功：" + cid);
                                return "redirect:" + weburlConfig.getPublicUrl() + "/#/main?key=" + WebUtils.getHttpResponse(subject).getHeader("RememberMe");
                        }
                } catch (UnknownAccountException | IncorrectCredentialsException e) {
                        logger.warn("大数据平台登录-未检索到账户");
                        return loginUrl;
                } catch (LockedAccountException e) {
                        return loginUrl;
                } catch (AccountNoRoleException e) {
                        return loginUrl;
                } catch (AuthenticationException e) {
                        e.printStackTrace();
                        return loginUrl;
                }
        }
}
