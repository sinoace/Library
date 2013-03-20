package net.sinoace.library.net;

/**
 * 接受的文件类型
 */
public class Accept extends Field {
	/**
	 * 名字
	 */
	public static final String NAME = "Accept";
	/**
	 * 值
	 */
	private String value;
	
	public Accept(String value) {
		setValue(value);
	}
	
	public Accept() {
		
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		if(value == null || "".equals(value.trim())){
			value = "text/html, application/xml;q=0.9, application/xhtml xml, image/png, image/jpeg, image/gif, image/x-xbitmap, */*;q=0.1";
		}
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
