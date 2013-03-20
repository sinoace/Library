package net.sinoace.library.net;

public class Range extends Field {
	/**
	 * 名字
	 */
	public static final String NAME = "Range";
	/**
	 * 值
	 */
	private String value;
	/**
	 * 开始位置
	 */
	private long startLocation;
	/**
	 * 结束位置
	 */
	private long endLocation;
	
	public Range(long startLocation, long endLocation) {
		setStartLocation(startLocation);
		setEndLocation(endLocation);
	}
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		if(value == null || "".equals(value.trim())){
			value = "bytes=" + getStartLocation() + "-"+ getEndLocation();
		}
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	public long getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(long startLocation) {
		this.startLocation = startLocation;
	}

	public long getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(long endLocation) {
		this.endLocation = endLocation;
	}
}