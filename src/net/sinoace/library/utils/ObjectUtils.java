package net.sinoace.library.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <h2>Object工具类，提供一些有关Object的便捷方法</h2>
 * 
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(01)、将指定的Object序列化成字节：static byte[] objectToByte(Object object) 
 * <br>&nbsp;&nbsp;&nbsp;&nbsp;(02)、将指定的字节反序列化成一个Object：static Object byteToObject(byte[] bytes)
 */

public class ObjectUtils {
	
	/**
	 * (01)、将指定的Object序列化成字节
	 * @param object 指定的Object
	 * @return 经过序列化得到的字节数据
	 * @throws IOException i/o异常
	 */
	public static byte[] objectToByte(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
		return baos.toByteArray();
	}
	
	
	/**
	 * (02)、将指定的字节反序列化成一个Object
	 * @param bytes 指定的字节
	 * @return 经过反序列化得到的Object
	 * @throws IOException i/o异常
	 * @throws ClassNotFoundException 字节数组中不存在对象
	 */
	public static Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object object = null;
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
		object = ois.readObject();
		ois.close();
		return object;
	}
}
