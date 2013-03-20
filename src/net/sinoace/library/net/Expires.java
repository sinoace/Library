package net.sinoace.library.net;

public class Expires extends Field{
	/**
	 * 名字
	 */
	public static final String NAME = "Expires";
	/**
	 * 值
	 */
	private String value;
	
	public Expires(String value) {
		setValue(value);
	}
	
	public Expires() {
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
