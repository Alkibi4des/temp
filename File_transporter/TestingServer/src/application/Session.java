package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import application.model.Constants;
import application.model.NetworkCall;

public class Session {
	OutputStream os;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Socket socket;
	String downloadStartLocation;

	public Session(Socket socket) {
		this.socket = socket;
		initStream(this.socket);
		receiveData();

	}

	void initStream(Socket pSocket) {
		try {
			os = pSocket.getOutputStream();
			ois = new ObjectInputStream(pSocket.getInputStream());
			oos = new ObjectOutputStream(pSocket.getOutputStream());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void receiveData() {
		new Thread() {
			NetworkCall call;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (true) {
					try {
						call = (NetworkCall) ois.readObject();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						try {
							// socket.close();
							Thread.currentThread().interrupt();
						} catch (Exception e2) {
							System.out.println(e2.getMessage());
						}
					}
					System.out.println("Server : transferred");
					switch (call.getType()) {
					case NetworkCall.LOGIN_REQUEST:
						loginCheck(call.getUserID(), call.getPassword());
						break;
					case NetworkCall.FILE_INFO_REQUEST:
						sendFileInfo(call);
						break;
					case NetworkCall.FILE_DOWNLOAD_REQUEST:
						setDownloadStartLocation(call.getFIle());
						sendAllFile(call.getFIle());
						System.out.println("download complete");
						break;
					case NetworkCall.FILE_RECEIVE:
						break;
					}
				}
			}
		}.start();
	}

	synchronized void sendData(NetworkCall call) {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					oos.writeObject(call);
					oos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void loginCheck(String id, String password) {
		ResultSet resultSet;
		try {
			DBController dbController = DBController.getInstance();
			PreparedStatement pStatement = dbController.getConnection()
					.prepareStatement("select * from students where id = ? and password =?;");
			pStatement.setString(1, id);
			pStatement.setString(2, password);
			resultSet = pStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("login success : " + id);
				NetworkCall call = new NetworkCall();
				call.setType(NetworkCall.LOGIN_ACCEPTED);
				call.setUserID(id);
				sendData(call);
			} else {
				System.out.println("login Failed : " + id);
				NetworkCall call = new NetworkCall();
				call.setType(NetworkCall.LOGIN_FAILED);
				call.setUserID(id);
				sendData(call);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendFileInfo(NetworkCall pCall) {
		NetworkCall newCall = new NetworkCall();
		newCall.setType(NetworkCall.FILE_INFO);
		if (pCall.getFIle() == null) {
			File tempFile = new File(Constants.STORAGE_PATH + pCall.getUserID());
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			newCall.setFile(tempFile);
		} else {
			File tempFile = new File(pCall.getFIle().getPath());
			newCall.setFile(tempFile);
		}
		sendData(newCall);
		System.out.println("send file info");
	}

	void sendAllFile(File pFile) {
		if (pFile.isDirectory()) {
			NetworkCall newCall = new NetworkCall();
			newCall.setType(NetworkCall.FILE);
			newCall.setFile(pFile);
			System.out.println("rel path : "+getRelativePath(pFile.getPath()));
			newCall.setRelativePath(getRelativePath(pFile.getPath()));
			sendData(newCall);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			File[] list = pFile.listFiles();
			for (File f : list) {
				sendAllFile(f);
			}
		} else {
			sendFile(pFile);
		}
	}

	void sendFile(File pFile) {
		System.out.println(pFile.getName());
		try {
			NetworkCall newCall = new NetworkCall();
			newCall.setType(NetworkCall.FILE);
			newCall.setFile(pFile);
			newCall.setSize(pFile.length());
			System.out.println("rel path : "+getRelativePath(pFile.getPath()));
			newCall.setRelativePath(getRelativePath(pFile.getPath()));
			sendData(newCall);
			Thread.sleep(300);
			FileInputStream fis = new FileInputStream(pFile);
			int readLen;
			byte[] buffer = new byte[1024];
			while (true) {
				readLen = fis.read(buffer);
				if (readLen <= 0) {
					break;
				}
				os.write(buffer, 0, readLen);
			}
			fis.close();
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void setDownloadStartLocation(File pFile) {
		this.downloadStartLocation = pFile.getParentFile().getPath(); //c:\storage\jon1234
	}
	
	String getRelativePath(String path) {
		return path.substring(downloadStartLocation.length());
	}
}
