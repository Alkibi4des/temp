package application.model;

import java.io.File;
import java.io.Serializable;

public class NetworkCall implements Serializable{
	public static final int LOGIN_REQUEST = 10;
	public static final int LOGIN_ACCEPTED =11;
	public static final int LOGIN_FAILED = 12;
	public static final int LOGOUT = 13;
	public static final int FILE_INFO =20;
	public static final int FILE_INFO_REQUEST =21;
	
	public static final int FILE_DOWNLOAD_REQUEST=22;
	public static final int FILE_UPLOAD = 23;
	public static final int FILE = 24;
	public static final int FILE_RECEIVE = 25;
	
	public static final int STEP_INTO = 30;
	public static final int STEP_OUT=31;
	public static final int REFRESH=32;
	
	private String relativePath;
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	private long size;
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	private int type;
	private File file;
	private String userID;
	private String password;
	private byte[] data;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	private int[] extraValue;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setFile(File pFile) {
		this.file = pFile;
	}
	public File getFIle() {
		return this.file;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int[] getExtraValue() {
		return extraValue;
	}
	public void setExtraValue(int[] extraValue) {
		this.extraValue = extraValue;
	}

}
