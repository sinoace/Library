package net.sinoace.library.utils;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

/**
 * 相机管理器
 * @author xiaopan
 */
public class CameraManager implements Camera.AutoFocusCallback, Camera.PreviewCallback{
	private Camera camera;
	private Size previewSize;//预览大小
	private Size outPictureSize;//输出图片大小
	private Listener listener;
	private JpegPictureCallback jpegPictureCallback;
	private RawPictureCallback rawPictureCallback;
	private MyShutterCallback myShutterCallback;
	
	public CameraManager(){
		jpegPictureCallback = new JpegPictureCallback();
		rawPictureCallback = new RawPictureCallback();
		myShutterCallback = new MyShutterCallback();
	}
	
	/**
	 * 打开相机
	 */
	public void openCamera(SurfaceHolder surfaceHolder){
		try {
			camera = Camera.open();
			Parameters parameters = camera.getParameters();
			if(getOutPictureSize() != null){
				parameters.setPictureSize(getOutPictureSize().getWidth(), getOutPictureSize().getHeight());
			}
			if(getPreviewSize() != null){
				parameters.setPreviewSize(getPreviewSize().getWidth(), getPreviewSize().getHeight());
			}
			camera.setParameters(parameters);
			camera.setPreviewDisplay(surfaceHolder);
			camera.setPreviewCallback(this);
			if(getListener() != null){
				getListener().onInitCamera(camera);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(camera != null){
				camera.release();
			}
			if(getListener() != null){
				getListener().onException(e);
			}
		}
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if(getListener() != null){
			getListener().onAutoFocus(success, camera);
		}
	}
	
	/**
	 * 开始预览
	 */
	public void startPreview(){
		if(camera != null){
			camera.startPreview();
		}
	}
	
	/**
	 * 停止预览
	 */
	public void stopPreview(){
		if(camera != null){
			camera.stopPreview();
		}
	}
	
	/**
	 * 释放
	 */
	public void release(){
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}
	
	/**
	 * 自动对焦
	 */
	public void autoFocus(){
		if(camera != null){
			camera.autoFocus(this);
		}
	}
	
	/**
	 * 拍照
	 */
	public void takePicture(){
		if(camera != null){
			camera.takePicture(myShutterCallback, rawPictureCallback, jpegPictureCallback);
		}
	}
	
	/**
	 * 设置闪光模式
	 * @param newFlashMode
	 */
	public void setFlashMode(String newFlashMode){
		stopPreview();
		Parameters parameters = camera.getParameters();
		parameters.setFlashMode(newFlashMode);
		camera.setParameters(parameters);
		startPreview();
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if(getListener() != null){
			getListener().onPreviewFrame(data, camera);
		}
	}

	/**
	 * 快门回调函数
	 * @author xiaopan
	 *
	 */
	private class MyShutterCallback implements Camera.ShutterCallback{
		@Override
		public void onShutter() {
			if(getListener() != null){
				getListener().onShutter();
			}
		}
	}
	
	/**
	 * RAW图片回调函数
	 * @author xiaopan
	 *
	 */
	private class RawPictureCallback implements Camera.PictureCallback{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			if(getListener() != null){
				getListener().onPictureTakenRaw(data, camera);
			}
		}
	}

	/**
	 * JPEG图片回调函数
	 * @author xiaopan
	 *
	 */
	private class JpegPictureCallback implements Camera.PictureCallback{
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			if(getListener() != null){
				getListener().onPictureTakenJpeg(data, camera);
			}
		}
	}
	
	/**
	 * 监听器
	 * @author xiaopan
	 *
	 */
	public interface Listener{
		/**
		 * 当设置参数
		 * @param camera
		 */
		public void onInitCamera(Camera camera);
		
		/**
		 * 当对焦结束
		 * @param success
		 * @param camera
		 */
		public void onAutoFocus(boolean success, Camera camera);
		
		/**
		 * 当捕获到预览的帧数据
		 * @param data
		 * @param camera
		 */
		public void onPreviewFrame(byte[] data, Camera camera);
		
		/**
		 * 当按下快门
		 */
		public void onShutter();
		
		/**
		 * 当拍摄到RAW照片
		 * @param data
		 * @param camera
		 */
		public void onPictureTakenRaw(byte[] data, Camera camera);
		
		/**
		 * 当拍摄到JPEG照片
		 * @param data
		 * @param camera
		 */
		public void onPictureTakenJpeg(byte[] data, Camera camera);
		
		/**
		 * 当发生异常
		 */
		public void onException(Exception e);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Size getPreviewSize() {
		return previewSize;
	}

	public void setPreviewSize(Size previewSize) {
		this.previewSize = previewSize;
	}

	public Size getOutPictureSize() {
		return outPictureSize;
	}

	public void setOutPictureSize(Size outPictureSize) {
		this.outPictureSize = outPictureSize;
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
}