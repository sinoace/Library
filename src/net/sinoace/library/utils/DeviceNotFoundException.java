package net.sinoace.library.utils;

/**
 * 设备找不到异常
 * @version 1.0 
 * 
 * @date May 30, 2012
 */
public class DeviceNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeviceNotFoundException(){}
	
	public DeviceNotFoundException(String  message){
		super(message);
	}
}
