package net.sinoace.library.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.util.Base64;

public class BaseUtil {

	/**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return android.util.Base64.encodeToString(buffer, Base64.DEFAULT);
    }
    /**
     * <p>将文件转成base64 字符串</p>
     * @param file 文件
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(File file) throws Exception {
    	FileInputStream inputFile = new FileInputStream(file);
    	byte[] buffer = new byte[(int)file.length()];
    	inputFile.read(buffer);
    	inputFile.close();
    	return android.util.Base64.encodeToString(buffer, Base64.DEFAULT);
    }
    /**
     * <p>将base64字符解码保存文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte [] baseByte = android.util.Base64.decode(base64Code, Base64.DEFAULT);
        
//        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(baseByte);
        out.close();
    }
    /**
     * <p>将base64字符保存文本文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code,String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

}
