package net.sinoace.library.barcode;

import java.util.Hashtable;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;

/**
 * 解码器
 * @author xiaopan
 *
 */
public class Decoder {
	private static final int MESSAGE_DECODE_SUCCESS = 1001;
	private static final int MESSAGE_DECODE_FAIL = 1002;
	private static final String KEY_BAR_CODE = "KEY_BAR_CODE";
	private Camera.Size cameraPreviewSize;	//相机预览尺寸
	private int cameraPreviewFormat;	//相机预览格式的整型表示形式
	private String cameraPreviewFormatString;	//相机预览格式的字符串表示形式
	private Rect scanFrameRectInPreview;	//扫描框相对于预览界面的矩形
	private MultiFormatReader multiFormatReader;	//解码读取器
	private Vector<BarcodeFormat> decodeFormats;	//支持的编码格式
	private ResultPointCallback resultPointCallback;	//结果可疑点回调对象
	private DecodeListener decodeListener;	//解码监听器
	private byte[] sourceData;	//等待解码的数据
	private DecodeThread decodeThread;	//解码线程
	private DecodeHandler decodeHandler;	//解码处理器
	private boolean isPortrait;	//是否是竖屏
	
	public Decoder(Context context, Camera.Parameters cameraParameters, ScanFrameView scanFrameView){
		cameraPreviewSize = cameraParameters.getPreviewSize();
		cameraPreviewFormat = cameraParameters.getPreviewFormat();
		cameraPreviewFormatString = cameraParameters.get("preview-format");
		scanFrameRectInPreview = scanFrameView.getRectInPreview(cameraPreviewSize);
		isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
		
		// 初始化解码对象
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(3);
		setDecodeFormats(new Vector<BarcodeFormat>());
		getDecodeFormats().addAll(DecodeFormatManager.ONE_D_FORMATS);
		getDecodeFormats().addAll(DecodeFormatManager.QR_CODE_FORMATS);
		getDecodeFormats().addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		hints.put(DecodeHintType.POSSIBLE_FORMATS, getDecodeFormats());
//		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
		hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, new ResultPointCallback() {
			@Override
			public void foundPossibleResultPoint(ResultPoint arg0) {
				if(getResultPointCallback() != null){
					getResultPointCallback().foundPossibleResultPoint(arg0);
				}
			}
		});
		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(hints);
		
		decodeHandler = new DecodeHandler();
	}
	
	/**
	 * 尝试解码，如果解码器正在运行，并且处于空闲状态，就开始解码，否则忽略本次解码请求
	 * @param sourceData 源数据
	 */
	public void tryDecode(byte[] sourceData) {
		if(this.sourceData == null){
			this.sourceData = sourceData;
			if(decodeThread == null){
				decodeThread = new DecodeThread();
				decodeThread.start();
			}
		}
	}
	
	/**
	 * 解码
	 * @param data
	 */
	private void decode(){
		//如果不空
		if(sourceData != null){
			Result result = null;
			
			int previewWidth = cameraPreviewSize.width;
			int previewHeight = cameraPreviewSize.height;
			
			// 如果是竖屏就旋转图片
			if (isPortrait) {
				//将横屏的源图片转换成竖屏的
				sourceData = yuvLandscapeToPortrait(sourceData, previewWidth, previewHeight);
				//高宽互换
				int tmp = previewWidth;
				previewWidth = previewHeight;
				previewHeight = tmp;
			}
			
			//创建YUV格式的源图片
			PlanarYUVLuminanceSource source = createLuminanceSource(sourceData, previewWidth, previewHeight);
			//转换成二进制图片
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			//解码
			try {
				result = multiFormatReader.decodeWithState(bitmap);
			} catch (ReaderException re) {
				re.printStackTrace();
			} finally {
				multiFormatReader.reset();
			}
			
			//如果解码成功
			if (result != null) {
				pause();
				Bundle bundle = new Bundle();
				bundle.putParcelable(KEY_BAR_CODE, source.renderCroppedGreyscaleBitmap());
				Message message = decodeHandler.obtainMessage(MESSAGE_DECODE_SUCCESS, result);
				message.setData(bundle);
				message.sendToTarget();
			} else {
				Message message = decodeHandler.obtainMessage(MESSAGE_DECODE_FAIL);
				message.sendToTarget();
			}
			
			//清除源数据
			sourceData = null;
		}
	}
	
	/**
	 * 暂停解码
	 */
	public void pause(){
		if(decodeThread != null && decodeThread.isAlive()){
			decodeThread.finish();
		}
		decodeThread = null;
	}
	
	/**
	 * 创建YUV源文件
	 */
	private PlanarYUVLuminanceSource createLuminanceSource(byte[] data, int previewWidth, int previewHeight) {
		switch (cameraPreviewFormat) {
		// This is the standard Android format which all devices are REQUIRED to
		// support.
		// In theory, it's the only one we should ever care about.
		case PixelFormat.YCbCr_420_SP:
			// This format has never been seen in the wild, but is compatible as
			// we only care
			// about the Y channel, so allow it.
		case PixelFormat.YCbCr_422_SP:
			return new PlanarYUVLuminanceSource(data, previewWidth, previewHeight, scanFrameRectInPreview.left, scanFrameRectInPreview.top, scanFrameRectInPreview.width(), scanFrameRectInPreview.height());
		default:
			// The Samsung Moment incorrectly uses this variant instead of the
			// 'sp' version.
			// Fortunately, it too has all the Y data up front, so we can read
			// it.
			if ("yuv420p".equals(cameraPreviewFormatString)) {
				return new PlanarYUVLuminanceSource(data, previewWidth, previewHeight, scanFrameRectInPreview.left, scanFrameRectInPreview.top, scanFrameRectInPreview.width(), scanFrameRectInPreview.height());
			}
		}
		throw new IllegalArgumentException("Unsupported picture format: " + cameraPreviewFormat + '/' + cameraPreviewFormatString);
	}

	/**
	 * 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注意：将源图片的宽高互换一下就是新图片的宽高
	 * @param sourceData YUV格式的图片的源数据
	 * @param width 源图片的宽
	 * @param height 源图片的高
	 * @return 
	 */
	private static final byte[] yuvLandscapeToPortrait(byte[] sourceData, int width, int height){
		byte[] rotatedData = new byte[sourceData.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = sourceData[x + y * width];
		}
		return rotatedData;
	}

	/**
	 * 解码监听器
	 * @author xiaopan
	 *
	 */
	public interface DecodeListener {
		/**
		 * 当解码成功
		 * @param result 结果
		 * @param barcode 条码图片
		 */
		public void onDecodeSuccess(Result result, Bitmap barcode);
		
		/**
		 * 当解码失败
		 */
		public void onDecodeFail();
	}
	
	/**
	 * 解码线程
	 * @author xiaopan
	 *
	 */
	private class DecodeThread extends Thread{
		private boolean isRunning;
		
		@Override
		public void run(){
			isRunning = true;
			while(isRunning){
				decode();
			}
		}
		
		public void finish(){
			isRunning = false;
		}
	}
	
	/**
	 * 解码处理器
	 * @author xiaopan
	 *
	 */
	@SuppressLint("HandlerLeak")
	private class DecodeHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_DECODE_SUCCESS:
				if(getDecodeListener() != null){
					getDecodeListener().onDecodeSuccess((Result) msg.obj, (Bitmap) msg.getData().getParcelable(KEY_BAR_CODE));
				}
				break;
			case MESSAGE_DECODE_FAIL:
				if(getDecodeListener() != null){
					getDecodeListener().onDecodeFail();
				}
				break;
			default:
				break;
			}
		}
	}

	public Vector<BarcodeFormat> getDecodeFormats() {
		return decodeFormats;
	}

	public void setDecodeFormats(Vector<BarcodeFormat> decodeFormats) {
		this.decodeFormats = decodeFormats;
	}

	public ResultPointCallback getResultPointCallback() {
		return resultPointCallback;
	}

	public void setResultPointCallback(ResultPointCallback resultPointCallback) {
		this.resultPointCallback = resultPointCallback;
	}

	public DecodeListener getDecodeListener() {
		return decodeListener;
	}

	public void setDecodeListener(DecodeListener decodeListener) {
		this.decodeListener = decodeListener;
	}
}