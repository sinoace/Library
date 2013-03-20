package net.sinoace.library.io;

/**
 * 长度太大异常
 * 
 *
 */
public class LengthTooBigException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public LengthTooBigException(String message) {
		super(message);
    }
}