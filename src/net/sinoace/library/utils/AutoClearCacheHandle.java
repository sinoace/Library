package net.sinoace.library.utils;

class AutoClearCacheHandle implements Runnable{
	@Override
	public void run() {
		ImageLoader.clearCache();
	}
}