package net.sinoace.library.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.sinoace.library.io.CheckingUtils;
import net.sinoace.library.io.IOUtils;

/**
 * 上传文件对象Bean
 */
public class UploadFile {
	/** 文件名 */
	private String fileName;
	/** 文件的数据,以字节形式存放 */
	private byte[] fileData;
	/** 参数名称 */
	private String paramName;
	/** 内容类型 */
	private String contentType;
	/** 待上传的文件 */
	private File file;
	/** 已完成长度 */
	private long finishLength;
	

	/**
	 * 直接指定各项字段的值，适用于较小的数据
	 * @param fileName 文件名
	 * @param fileData 文件数据
	 * @param paramName 参数名
	 * @param contentType 内容类型
	 */
	public UploadFile(String fileName, byte[] fileData, String paramName, String contentType){
		setFileName(fileName);
		setFileData(fileData);
		setParamName(paramName);
		setContentType(contentType);
		setFinishLength(0);
	}
	
	/**
	 * 从文件file里获取数据作为一个上传对象
	 * @param file 数据源
	 * @param paramName 参数名
	 * @param contentType 内容类别
	 * @throws IOException file代表的文件不存在，或不是一个文件，或不能读取
	 */
	public UploadFile(File file, String paramName, String contentType) throws IOException{
		setFile(file);
		setFileName(file.getName());
		setParamName(paramName);
		setContentType(contentType);
		setFinishLength(0);
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		CheckingUtils.valiObjectIsNull(fileName, "fileName");
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		CheckingUtils.valiObjectIsNull(fileData, "fileData");
		this.fileData = fileData;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		CheckingUtils.valiObjectIsNull(paramName, "paramName");
		this.paramName = paramName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		CheckingUtils.valiObjectIsNull(contentType, "contentType");
		this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) throws IOException {
		CheckingUtils.valiObjectIsNull(file, "file");
		CheckingUtils.valiFileIsExists(file);
		CheckingUtils.valiFileIsFile(file);
		CheckingUtils.valiFileCanRead(file);
		this.file = file;
	}

	public long getFinishLength() {
		return finishLength;
	}

	public void setFinishLength(long finishLength) {
		this.finishLength = finishLength;
	}

	/**
	 * 返回一个此文件的输入流
	 * @return 当没有指定文件对象并且fileData为null时返回null
	 * @throws IOException
	 */
	public InputStream getFileInput() throws IOException {
		if(file != null && fileData == null){			
			return IOUtils.openBufferedInputStream(file);
		}else if(fileData != null && file == null){
			return new ByteArrayInputStream(fileData);
		}else{
			return null;
		}
	}

	/**
	 * 获取文件的长度
	 * @return 当没有指定文件对象并且fileData不为null时返回-1
	 */
	public long getFileLength() {
		if(file != null && fileData == null){		
			return file.length();
		}else if(fileData != null && file == null){
			return fileData.length;
		}else{
			return -1;
		}
	}
}
