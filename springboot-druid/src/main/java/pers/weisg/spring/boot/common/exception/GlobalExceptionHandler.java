package pers.weisg.spring.boot.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 异常统一处理
 * @author WEISANGNG
 * @date 2018年5月12日
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Map<String, Object> defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
			Exception e) {
		Map<String, Object> retExMap = new HashMap<>();
		retExMap.put("code", "999999");
		retExMap.put("code", e.getMessage());
		return retExMap;
	}
}
