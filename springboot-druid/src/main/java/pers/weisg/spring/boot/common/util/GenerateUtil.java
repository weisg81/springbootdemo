package pers.weisg.spring.boot.common.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;



/** 
 * @Description: 生成工具类
 * @author WEISANGNG   
 * @date 2018年5月12日   
 */
public class GenerateUtil {
	
	/**
	 * @Description:主键id生成，32位
	 * @return String
	 * @author WEISANGNG
	 * @date 2018年5月12日
	 */
	public static String generateId() {
		String id = StringUtils.replace(UUID.randomUUID().toString(), "-", "").toUpperCase();
		return id;
	}
	
}
