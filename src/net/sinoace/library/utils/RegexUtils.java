package net.sinoace.library.utils;

/**
 * <h2>正则表达式工具类，提供一些常用的正则表达式</h2>
 */
public class RegexUtils {
	/**
	 * 匹配全网IP的正则表达式
	 */
	public static final String IP_ALL_REGEX = "^([01]?\\d?\\d|2[0-4]\\d|25[0-5])(\\.[01]?\\d?\\d|2[0-4]\\d|25[0-5]){3}$";
	/**
	 * 匹配外网IP的正则表达式
	 */
	public static final String IP_OUTER_REGEX = "^(([1-9]\\d|1\\d{2})|2[0-4]\\d|25[0-5])(\\.([01]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$";
	/**
	 * 匹配手机号码的正则表达式
	 * <br>支持130——139、150——153、155——159、180、183、185、186、188、189号段
	 */
	public static final String PHONE_NUMBER_REGEX = "^1{1}(3{1}\\d{1}|5{1}[012356789]{1}|8{1}[035689]{1})\\d{8}$";
	/**
	 * 匹配邮箱的正则表达式
	 * <br>"www."可省略不写
	 */
	public static final String EMAIL_REGEX = "^(www\\.)?\\w+@\\w+\\.\\w+$";
	/**
	 * 匹配汉子的正则表达式，个数限制为一个或多个
	 */
	public static final String CHINESE_REGEX = "^[\u4e00-\u9f5a]+$";
	/**
	 * 匹配数字的正则表达式，个数限制为一个或多个
	 */
	public static final String DIGITAL = "^\\d+$";
	
	/**
	 * 匹配给定的字符串是否是一个邮箱账号，"www."可省略不写
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesEmail(String string){
		return string.matches(EMAIL_REGEX);
	}
	
	/**
	 * 匹配给定的字符串是否是一个手机号码，支持130——139、150——153、155——159、180、183、185、186、188、189号段
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesMobilePhoneNumber(String string){
		return string.matches(PHONE_NUMBER_REGEX);
	}
	
	/**
	 * 匹配给定的字符串是否是一个全网IP
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesIpAll(String string){
		return string.matches(IP_ALL_REGEX);
	}
	
	/**
	 * 匹配给定的字符串是否是一个外网IP
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesIpOuter(String string){
		return string.matches(IP_OUTER_REGEX);
	}
	
	/**
	 * 匹配给定的字符串是否全部由汉子组成
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesChinese(String string){
		return string.matches(CHINESE_REGEX);
	}
	
	/**
	 * 验证给定的字符串是否全部由数字组成
	 * @param string 给定的字符串
	 * @return true：是
	 */
	public static boolean matchesDigital(String string){
		return string.matches(DIGITAL);
	}
}
