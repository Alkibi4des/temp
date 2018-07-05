package application.model;

import java.io.File;

public class FileItem {
	private String Path;
	private String fileName;
	private String length;
	private boolean isDirectory;

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public String  getType() {
		return isDirectory?"Directory":"File";
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public FileItem(File pFile) {
		this.fileName = pFile.getName();
		this.Path = pFile.getPath();
		if (pFile.isDirectory()) {
			this.length = "";
			this.isDirectory = true;
		} else {
			 this.length = pFile.length()+" byte";
			 this.isDirectory = false;
		}
	}
}
