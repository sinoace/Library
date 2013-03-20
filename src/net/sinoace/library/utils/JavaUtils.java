package net.sinoace.library.utils;

import java.util.List;
import java.util.Map;

/**
 * 其它工具类
 */

public class JavaUtils {
	
	/**
	 * 获取指定列表的字符串表示形式列表
	 * @param list 指定的列表
	 * @param 字符串表示形式
	 */
	public static String printList(List<?> list){
		StringBuilder sb = new StringBuilder();
		sb.append("【");
		for(int w = 0; w < list.size()-1; w++){
			if (list.get(w) == null){
				sb.append("{null}, ");
			}else{
				sb.append("{"+list.get(w).toString() + "}, ");
			}
		}

		if (list.get(list.size() - 1) == null){
			sb.append("{null}】");
		}else{
			sb.append("{"+list.get(list.size() - 1).toString() + "}】\n");
		}
		return sb.toString();
	}
	
	/**
	 * 打印content，然后换行
	 * @param content 要打印的内容
	 */
	public static void println(String content){
		System.out.println(content);
	}
	
	/**
	 * 将Map对象转换成JSON字符串
	 * @param map
	 * @return
	 */
	public static String transformJSONFromMap(Map<String, String> map){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(Map.Entry<String, String> entry : map.entrySet()){
			sb.append("\""+entry.getKey()+"\"");
			sb.append(":");
			sb.append("\""+entry.getValue()+"\",");
		}
		//如果长度大于1，说明需要截取最后的一个逗号
		if(sb.length() > 1){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 计算斐波那契数列第N个元素的值
	 * @param number 第N个元素
	 * @return 第几个元素的值
	 */
	public static long countFBNQ(long number){
		long one = 0;
		long two = 1;
		long three = -1;
		for(long w = 2; w < number; w++){
			three = one + two;
			one = two;
			two = three;
		}
		return one + two;
	}
}
