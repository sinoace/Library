package net.sinoace.library.net;

public class AcceptRanges extends Field{
	/**
	 * 名字
	 */
	public static final String NAME = "Accept-Ranges";
	/**
	 * 值
	 */
	private String value;
	
	public AcceptRanges(String value) {
		setValue(value);
	}
	
	public AcceptRanges() {
		
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		if(value == null || "".equals(value.trim())){
			value = "bytes";
		}
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
