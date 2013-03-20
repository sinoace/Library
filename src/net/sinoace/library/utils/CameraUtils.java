package net.sinoace.library.utils;

import java.util.regex.Pattern;

import android.content.Context;
import android.hardware.Camera;

/**
 * 相机工具箱
 * @author xiaopan
 *
 */
public class CameraUtils {
	/**
	 * 用英文逗号分割的正则表达式
	 */
	private static final Pattern COMMA_PATTERN = Pattern.compile(",");
	
	/**
	 * 获取最佳的预览分辨率
	 * @param context 上下文，用来获取屏幕分辨率
	 * @param cameraParameters 相机的参数
	 * @return 最佳的预览分辨率
	 */
	public static Size getBestPreviewSize(Context context, Camera.Parameters cameraParameters) {
		Size bestCameraPreviewSize = null;
		
		//获取当前设备所支持的预览大小，这是一个用','分割的字符串
		String previewSizeValueString = cameraParameters.get("preview-size-values");
		
		// 貌似索尼的手机这个参数是preview-size-value
		if (previewSizeValueString == null) {
			previewSizeValueString = cameraParameters.get("preview-size-value");
		}

		//尝试查找最佳的预览分辨率
		if (previewSizeValueString != null) {
			bestCameraPreviewSize = findBestPreviewSizeValue(context, previewSizeValueString);
		}

		//如果查找失败了
		if (bestCameraPreviewSize == null) {
			Size screenSize = AndroidUtils.getScreenSize(context);
			// 确保相机的分辨率是8的倍数（屏幕可能不是）
			bestCameraPreviewSize = new Size((screenSize.getWidth() >> 3) << 3, (screenSize.getHeight() >> 3) << 3);
		}

		return bestCameraPreviewSize;
	}

	/**
	 * 从一个字符串形似的分辨率集合中根据屏幕的尺寸查找最佳的预览分辨率
	 * @param context 上下文，用来获取屏幕分辨率
	 * @param previewSizeValueString 相机支持的预览尺寸的字符串集合
	 * @return 从相机支持的预览尺寸的字符串集合中查找出的最佳的预览分辨率
	 */
	public static Size findBestPreviewSizeValue(Context context, CharSequence previewSizeValueString) {
		int bestX = 0;
		int bestY = 0;
		int diff = Integer.MAX_VALUE;
		Size screenSize = AndroidUtils.getScreenSize(context);
		for (String previewSize : COMMA_PATTERN.split(previewSizeValueString)) {

			previewSize = previewSize.trim();
			int dimPosition = previewSize.indexOf('x');
			if (dimPosition < 0) {
				continue;
			}

			int newX;
			int newY;
			try {
				newX = Integer.parseInt(previewSize.substring(0, dimPosition));
				newY = Integer.parseInt(previewSize.substring(dimPosition + 1));
			} catch (NumberFormatException nfe) {
				continue;
			}

			int newDiff = Math.abs(newX - screenSize.getWidth()) + Math.abs(newY - screenSize.getHeight());
			if (newDiff == 0) {
				bestX = newX;
				bestY = newY;
				break;
			} else if (newDiff < diff) {
				bestX = newX;
				bestY = newY;
				diff = newDiff;
			}
		}

		if (bestX > 0 && bestY > 0) {
			return new Size(bestX, bestY);
		}
		return null;
	}
}