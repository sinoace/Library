package net.sinoace.library.net;

public class CacheControl extends Field{
	/**
	 * 名字
	 */
	public static final String NAME = "Cache-Control";
	/**
	 * 值- 私有的
	 */
	public static final String VALUE_PRIVATE = "private"; 
	/**
	 * 值 - 不缓存
	 */
	public static final String VALUE_NO_CACHE_MUST_REVALIDATE = "no-cache, must-revalidate"; 
	/**
	 * 值
	 */
	private String value;
	
	public CacheControl(String value) {
		setValue(value);
	}
	
	public CacheControl() {
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		if(value == null || "".equals(value.trim())){
			value = VALUE_PRIVATE;
		}
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
