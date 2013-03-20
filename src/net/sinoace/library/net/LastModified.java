package net.sinoace.library.net;

public class LastModified extends Field{
	/**
	 * 名字
	 */
	public static final String NAME = "Last-Modified";
	/**
	 * 值
	 */
	private String value;
	
	public LastModified(String value) {
		setValue(value);
	}
	
	public LastModified() {
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		if(value == null || "".equals(value.trim())){
			value = "";
		}
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
