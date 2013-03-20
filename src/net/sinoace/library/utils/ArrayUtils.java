package net.sinoace.library.utils;

import java.util.Stack;

/**
 * <h2>数组工具类，提供一些有关数组的便捷方法</h2>
 * 
 * <br><b>1、增删移动相关</b>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.01)、以无损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向后移动number位：static void backwardByLossless(Object[] objects, int headIndex, int endIndex, int number)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.02)、以无损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向前移动number位：static void forwardByLossless(Object[] objects, int headIndex, int endIndex, int number)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.03)、以有损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向后移动number位：static void backwardLoss(Object[] objects, int headIndex, int endIndex, int number)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.04)、以有损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向前移动number位：static void forwardLoss(Object[] objects, int headIndex, int endIndex, int number)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.05)、以有损的方式在数组objects的索引insertToIndex处插入元素element：static void insert(Object[] objects, int insertToIndex, Object element)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.06)、将数组objects中索引removeIndex出的元素删除：static Object remove(Object[] objects, int removeIndex)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.07)、返回数组objects的字符串表示形式：static String toString(Object[] objects)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.08)、在数组哦objects中搜索元素element：static int search(Object[] objects, Object element)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(1.09)、将数组objects中索引setIndex出的元素用element替换：static Object set(Object[] objects, Object element, int setIndex)
 * <br>
 * <br><b>2、Int数组排序相关</b>
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.01)、使用选择排序法，对数组intArray进行排序：static void SortingByChoose(int[] intArray, int type) 
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.02)、使用插入排序法，对数组intArray进行排序：static void SortingByInsert(int[] intArray, int type)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.03)、使用冒泡排序法，对数组intArray进行排序：static void SortingByBubbling(int[] intArray, int type)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.04)、使用递归快排法，对数组intArray进行排序：static void SortingByFastRecursion(int[] intArray, int start, int end, int type)
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(2.05)、使用栈快排法，对数组intArray进行排序：static void SortingByFastStack(int[] intArray, int type)
 * 
 * @author panpf
 * 
 */

public class ArrayUtils {

	/* **************************************************************1、增删移动相关start************************************************************ */
	/**
	 * (1.01)、以无损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向后移动number位
	 * <br>所谓无损就是，在将从索引headIndex处开始到endIndex索引处结束的元素向后移动number位的同时，也会将索引endIndex之后的number个元素移到索引headIndex之前
	 * 
	 * @param objects
	 *            待移动的数组
	 * @param headIndex
	 *            从此处开始，包括此处，取值范围0——objects.length-1
	 * @param endIndex
	 *            从此处结束，包括此处，取值范围headIndex——objects.length-1
	 * @param number
	 *            移动的位数，取值范围1——objects.length-1-endIndex
	 */
	public static void backwardByLossless(Object[] objects, int headIndex, int endIndex, int number) throws NullPointerException {
		CheckingUtils.valiIntValue(headIndex, 0, objects.length - 1, "headIndex");
		CheckingUtils.valiIntValue(endIndex, headIndex, objects.length - 1, "endIndex");
		CheckingUtils.valiIntValue(number, 1, objects.length - 1 - endIndex, "number");
		int geshu = endIndex-headIndex+1;
		if(number == 1){															//如果只移动一位
			for(int w = endIndex+1; w >= headIndex+1; w--){								//把这个数据移到前面去
				Object object = objects[w];
				objects[w] = objects[w-1];
				objects[w-1] = object;
			}
		}else if(geshu == number){													//如果移动的个数跟位数相同
			for(int w = headIndex; w <= endIndex; w++){									//将要移动的数据跟后面的等个数的数据交换
				Object object = objects[w];
				objects[w] = objects[w+number];
				objects[w+number] = object;
			}
		}else if(geshu < number){													//如果移动的个数比位数小
			for(int w = headIndex; w <= endIndex; w++){									//将要移动的数据跟后面的等个数的数据交换
				Object object = objects[w];
				objects[w] = objects[w+number];
				objects[w+number] = object;
			}
			backwardByLossless(objects, headIndex, endIndex, number-geshu);				//递归循环
		}else if(geshu > number){													//如果移动的个数比位数大
			forwardByLossless(objects, endIndex+1, endIndex+number, geshu);				//向前移动
		}
	}
	
	
	/**
	 * (1.02)、以无损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向前移动number位
	 * <br>所谓无损就是，在将从索引headIndex处开始到endIndex索引处结束的元素向前移动number位的同时，也会将索引headIndex之前的number个数据移到索引endIndex之后
	 * 
	 * @param objects
	 *            待移动的数组
	 * @param headIndex
	 *            从此处开始，包括此处，取值范围0——objects.length-1
	 * @param endIndex
	 *            从此处结束，包括此处，取值范围headIndex——objects.length-1
	 * @param number
	 *            移动的位数，取值范围1——headIndex
	 */
	public static void forwardByLossless(Object[] objects, int headIndex, int endIndex, int number) {
		CheckingUtils.valiIntValue(headIndex, 0, objects.length - 1, "headIndex");
		CheckingUtils.valiIntValue(endIndex, headIndex, objects.length - 1, "endIndex");
		CheckingUtils.valiIntValue(number, 1, headIndex, "number");
		int geshu = endIndex-headIndex+1;
		if(number == 1){															//如果只移动一位
			for(int w = headIndex-1; w < endIndex; w++){								//把这个数据移到后面去
				Object object = objects[w];
				objects[w] = objects[w+1];
				objects[w+1] = object;
			}
		}else if(geshu == number){													//如果移动的个数跟位数相同
			for(int w = headIndex; w <= endIndex; w++){									//将要移动的数据跟前面的等个数的数据交换
				Object object = objects[w];
				objects[w] = objects[w-number];
				objects[w-number] = object;
			}
		}else if(geshu < number){													//如果移动的个数比位数小
			for(int w = headIndex; w <= endIndex; w++){									//将要移动的数据跟前面的等个数的数据交换
				Object object = objects[w];
				objects[w] = objects[w-number];
				objects[w-number] = object;
			}
			forwardByLossless(objects, headIndex, endIndex, number-geshu);				//递归循环
		}else if(geshu > number){													//如果移动的个数比位数大
			backwardByLossless(objects, headIndex - number, headIndex-1, geshu);		//向后移
		}
	}
	
	
	/**
	 * (1.03)、以有损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向后移动number位
	 * <br>所谓有损就是，在将从索引headIndex处开始到endIndex索引处结束的元素向后移动number位的同时，会将相关元素覆盖
	 * 
	 * @param objects
	 *            待移动的数组
	 * @param headIndex
	 *            从此处开始，包括此处，取值范围0——objects.length-1
	 * @param endIndex
	 *            从此处结束，包括此处，取值范围headIndex——objects.length-1
	 * @param number
	 *            移动的位数，取值范围1——objects.length-1-endIndex
	 */
	public static void backwardLoss(Object[] objects, int headIndex, int endIndex, int number){
		CheckingUtils.valiIntValue(headIndex, 0, objects.length - 1, "headIndex");
		CheckingUtils.valiIntValue(endIndex, headIndex, objects.length - 1, "endIndex");
		CheckingUtils.valiIntValue(number, 1, objects.length - 1 - endIndex, "number");
		int length = endIndex - headIndex + 1 + number;								//移动的长度
		for(int w = headIndex; w <= endIndex; w++){
			objects[w + length] = objects[w];
			objects[w] = null;  
		}
	}
	
	
	/**
	 * (1.04)、以有损的方式，将数组objects的元素从索引headIndex处开始到endIndex索引处结束的元素，向前移动number位
	 * <br>所谓有损就是，在将从索引headIndex处开始到endIndex索引处结束的元素向前移动number位的同时，会将相关元素覆盖
	 * 
	 * @param objects
	 *            待移动的数组
	 * @param headIndex
	 *            从此处开始，包括此处，取值范围0——objects.length-1
	 * @param endIndex
	 *            从此处结束，包括此处，取值范围headIndex——objects.length-1
	 * @param number
	 *            移动的位数，取值范围1——headIndex
	 */
	public static void forwardLoss(Object[] objects, int headIndex, int endIndex, int number){
		CheckingUtils.valiIntValue(headIndex, 0, objects.length - 1, "headIndex");
		CheckingUtils.valiIntValue(endIndex, headIndex, objects.length - 1, "endIndex");
		CheckingUtils.valiIntValue(number, 1, headIndex, "number");
		int length = endIndex - headIndex + 1 + number;								//移动的长度
		for(int w = headIndex; w <= endIndex; w++){
			objects[w - length] = objects[w];
			objects[w] = null;  
		}
	}
	
	
	/**
	 * (1.05)、以有损的方式在数组objects的索引insertToIndex处插入元素element
	 * @param objects 带操作的数组
	 * @param insertToIndex 插入元素处的索引，范围：0————objects.length-1
	 * @param element 待添加的元素
	 */
	public static void insert(Object[] objects, int insertToIndex, Object element) {
		CheckingUtils.valiIntValue(insertToIndex, 0, objects.length-1, "insertToIndex");
		backwardLoss(objects, insertToIndex, objects.length-2, 1);
		objects[insertToIndex] = element;
	}
	
	/**
	 * (1.06)、将数组objects中索引removeIndex出的元素删除
	 * @param objects 待操作的数组
	 * @param removeIndex 待删除的元素的索引，范围：0————objects.length-1
	 * @return 被删除的元素
	 */
	public static Object remove(Object[] objects, int removeIndex) {
		CheckingUtils.valiIntValue(removeIndex, 0, objects.length-1, "insertToIndex");
		Object object = objects[removeIndex];
		forwardLoss(objects, removeIndex-1, objects.length-1, 1);
		return object;
	}
	
	
	/**
	 * (1.07)、返回数组objects的字符串表示形式
	 * @param objects 待操作的数组
	 * @return 字符串表示形式
	 */
	public static String arrayToString(Object[] objects) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int w = 0; w < objects.length-1; w++){
			if (objects[w] == null){
				sb.append("null");
			}else{
				sb.append(objects[w].toString());
			}
			sb.append(',');
		}

		if (objects[objects.length - 1] == null){
			sb.append("null");
		}else{
			sb.append(objects[objects.length - 1].toString());
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	/**
	 * (1.08)、在数组objects中搜索元素element
	 * @param objects 待操作的数组
	 * @param element 待匹配的元素
	 * @return 索引，如不存在，-1
	 */
	public static int search(Object[] objects, Object element) {
		int e = -1;
		for (int w = 0; w < objects.length; w++) {
			if (!element.equals(objects[w])){
				continue;
			}else{
				e = w;
				break;
			}
		}
		return e;
	}
	/* **************************************************************1、增删移动相关over************************************************************ */
	
	
	
	
	/* **************************************************************2、Int数组排序相关start************************************************************ */
	/**
	 * (2.01)、使用选择排序法，对数组intArray进行排序
	 * @param intArray 待排序的数组
	 * @param ascending 升序
	 */
	public static void sortingByChoose(int[] intArray, boolean ascending) {
		for (int cankaozhi = 0; cankaozhi < intArray.length - 1; cankaozhi++) {
			int zhongjian = intArray[cankaozhi];
			int zuixiao = 0;
			for (int zujian = cankaozhi + 1; zujian <= intArray.length - 1; zujian++) {
				boolean typee = true;
				if (ascending){
					typee = zhongjian > intArray[zujian];
				}else{
					typee = zhongjian < intArray[zujian];
				}
				if (typee) {
					zhongjian = intArray[zujian];
					zuixiao = zujian;
				}
			}

			if (zuixiao != 0) {
				int f = intArray[zuixiao];
				intArray[zuixiao] = intArray[cankaozhi];
				intArray[cankaozhi] = f;
			}
		}
	}

	/**
	 * (2.02)、使用插入排序法，对数组intArray进行排序
	 * @param intArray 待排序的数组
	 * @param ascending 升序
	 */
	public static void sortingByInsert(int[] intArray, boolean ascending) {
		for (int i = 1; i < intArray.length; i++) {
			int t = intArray[i];
			int y = -1;
			for (int j = i - 1; j >= 0; j--) {
				boolean typee = true;
				if (ascending){
					typee = t < intArray[j];
				}else{
					typee = t > intArray[j];
				}
				if (!typee)
					break;
				intArray[j + 1] = intArray[j];
				y = j;
			}

			if (y > -1)
				intArray[y] = t;
		}

	}
	
	/**
	 * (2.03)、使用冒泡排序法，对数组intArray进行排序
	 * @param intArray 待排序的数组
	 * @param ascending 升序
	 */
	public static void sortingByBubbling(int[] intArray, boolean ascending) {
		for (int e = 0; e < intArray.length - 1; e++) {
			for (int r = 0; r < intArray.length - 1; r++) {
				boolean typee = true;
				if (ascending){
					typee = intArray[r] > intArray[r + 1];
				}else{
					typee = intArray[r] < intArray[r + 1];
				}
				if (typee) {
					int t = intArray[r];
					intArray[r] = intArray[r + 1];
					intArray[r + 1] = t;
				}
			}
		}
	}

	/**
	 * (2.04)、使用递归快排法，对数组intArray进行排序
	 * @param intArray 待排序的数组
	 * @param ascending 排序的方式，用本类中的静态字段指定
	 */
	public static void sortingByFastRecursion(int[] intArray, int start, int end, boolean ascending) {
		int tmp = intArray[start];
		int i = start;
		
		if(ascending){
			for (int j = end; i < j;) {
				while (intArray[j] > tmp && i < j)j--;
				if(i < j){
					intArray[i] = intArray[j];
					i++;
				}
				for (; intArray[i] < tmp && i < j; i++);
				if(i < j){
					intArray[j] = intArray[i];
					j--;
				}
			}
		}else{
			for (int j = end; i < j;) {
				while (intArray[j] < tmp && i < j)j--;
				if(i < j){
					intArray[i] = intArray[j];
					i++;
				}
				for (; intArray[i] > tmp && i < j; i++);
				if(i < j){
					intArray[j] = intArray[i];
					j--;
				}
			}
		}
		
		intArray[i] = tmp;
		if (start < i - 1){
			sortingByFastRecursion(intArray, start, i - 1, ascending);
		}
		if (end > i + 1){
			sortingByFastRecursion(intArray, i + 1, end, ascending);
		}
	}

	/**
	 * (2.05)、使用栈快排法，对数组intArray进行排序
	 * @param intArray 待排序的数组
	 * @param ascending 升序
	 */
	public static void sortingByFastStack(int[] intArray, boolean ascending) {
		Stack<Integer> sa = new Stack<Integer>();
		sa.push(0);
		sa.push(intArray.length - 1);
		while (!sa.isEmpty()) {
			int end = ((Integer) sa.pop()).intValue();
			int start = ((Integer) sa.pop()).intValue();
			int i = start;
			int j = end;
			int tmp = intArray[i];
			if(ascending){
				while (i < j) {
					while (intArray[j] > tmp && i < j)
						j--;
					if (i < j) {
						intArray[i] = intArray[j];
						i++;
					}
					for (; intArray[i] < tmp && i < j; i++)
						;
					if (i < j) {
						intArray[j] = intArray[i];
						j--;
					}
				}
			}else{
				while (i < j) {
					while (intArray[j] < tmp && i < j)
						j--;
					if (i < j) {
						intArray[i] = intArray[j];
						i++;
					}
					for (; intArray[i] > tmp && i < j; i++)
						;
					if (i < j) {
						intArray[j] = intArray[i];
						j--;
					}
				}
			}
			
			intArray[i] = tmp;
			if (start < i - 1) {
				sa.push(Integer.valueOf(start));
				sa.push(Integer.valueOf(i - 1));
			}
			if (end > i + 1) {
				sa.push(Integer.valueOf(i + 1));
				sa.push(Integer.valueOf(end));
			}
		}
	}
	/* **************************************************************2、Int数组排序相关over************************************************************ */

	/**
	 * 将数组颠倒
	 */
	public static Object[] upsideDown(Object[] objects){
		int length = objects.length;
		Object tem;
		for(int w = 0; w < length/2; w++){
			tem = objects[w];
			objects[w] = objects[length-1-w];
			objects[length-1-w] = tem;
			tem = null;
		}
		return objects;
	}
	
	/**
	 * Inteher数组转换成int数组
	 * @param integers
	 * @return
	 */
	public static int[] integersToInts(Integer[] integers){
		int[] ints = new int[integers.length];
		for(int w = 0; w < integers.length; w++){
			ints[w] = integers[w];		
		}
		return ints;
	}
	
	/**
	 * 将给定的数组转换成字符串
	 * @param objects 给定的数组
	 * @param startSymbols 开始符号
	 * @param separator 分隔符
	 * @param endSymbols 结束符号
	 * @return 例如开始符号为"{"分隔符为", "结束符号为"}"那么结果为：{1, 2, 3}
	 */
	public static String toString(int[] objects, String startSymbols, String separator, String endSymbols){
		boolean addSeparator = false;
		StringBuffer sb = new StringBuffer();
		//如果开始符号不为null且不空
		if(StringUtils.isNotNullAndEmpty(startSymbols)){
			sb.append(startSymbols);
		}
		
		//循环所有的对象
		for(int object : objects){
			//如果需要添加分隔符
			if(addSeparator){
				sb.append(separator);
				addSeparator = false;
			}
			sb.append(object);
			addSeparator = true;
		}
		
		//如果结束符号不为null且不空
		if(StringUtils.isNotNullAndEmpty(endSymbols)){
			sb.append(endSymbols);
		}
		return sb.toString();
	}
	
	/**
	 * 将给定的数组转换成字符串
	 * @param objects 给定的数组
	 * @param separator 分隔符
	 * @return 例如分隔符为", "那么结果为：1, 2, 3
	 */
	public static String toString(int[] objects, String separator){
		return toString(objects, null, separator, null);
	}
}