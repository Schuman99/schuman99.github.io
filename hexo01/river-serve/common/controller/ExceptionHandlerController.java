package com.general.common.controller;

import com.general.core.msg.AjaxResult;
import com.general.core.msg.MessageCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理
 * @author fengw
 * @create 2016年11月14日
 */
@ControllerAdvice
public class ExceptionHandlerController {
	/**
	 * 身份验证未通过
	 * @author fengw
	 * @create 2016年11月15日
	 */
	@ExceptionHandler({UnauthenticatedException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public AjaxResult processUnauthenticatedException(UnauthenticatedException e) {
		return AjaxResult.createErrorResult(MessageCode.ACCOUNT_NO_LOGIN);
	}
	/**
	 * 无权限
	 * @author fengw
	 * @create 2016年11月15日
	 */
	@ExceptionHandler({UnauthorizedException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public AjaxResult processUnauthorizedException(UnauthorizedException e) {
		return AjaxResult.createErrorResult(MessageCode.ACCESS_DENIED);
	}
}
