package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.model.Constants;
import application.model.NetworkCall;
import application.view.ExplorerMainViewController;
import application.view.LoginFormController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientMain extends Application {
	ObjectOutputStream oos;
	ObjectInputStream ois;
	InputStream is;
	OutputStream os;
	Socket socket;
	public String rootPath;
	public String currentLocation = "";
	private String id;
	LoginFormController loginFormController;
	ExplorerMainViewController explorerController;
	Stage primaryStage;

	@Override

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initConnectionToServer();
		receiveData();

		primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.setScene(loginFormInit());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	void initConnectionToServer() {
		socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(Constants.SERVER_ADDRESS, Constants.SERVER_PORT));
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(socket.getOutputStream());
			is = socket.getInputStream();
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println("Can't not access to Server. please try later");
			System.out.println("Program will be shutdown.");
			try {
				Thread.sleep(500);
				System.exit(0);
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println(e2.getMessage());
			}
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("...connected...");
	}

	Scene loginFormInit() {
		FXMLLoader loader = new FXMLLoader();
		AnchorPane mainPane = null;
		loader.setLocation(ClientMain.class.getResource("view/LoginForm.fxml"));
		try {
			mainPane = (AnchorPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		loginFormController = loader.getController();
		loginFormController.setMain(this);
		return new Scene(mainPane);
	}

	void sendData(NetworkCall pCall) {
		try {
			System.out.println("client : tranferred");
			oos.writeObject(pCall);
			oos.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	void receiveData() {
		System.out.println("start to receive data from server");
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Thread th = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (true) {
					try {
						// synchronized (ois) {
						NetworkCall tmpCall = (NetworkCall) ois.readObject();
						switch (tmpCall.getType()) {
						case NetworkCall.LOGIN_ACCEPTED:
							onLoginSuccessed(tmpCall.getUserID());
							break;
						case NetworkCall.LOGIN_FAILED:
							onLoginFailed();
							break;
						case NetworkCall.FILE_INFO:
							onFileInfo(tmpCall);
							break;
						case NetworkCall.FILE:
							onFilePartitionDownloaded(tmpCall);
							break;
						default:
							break;
						}
						// }
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}
		};
		executorService.execute(th);
	}

	public void loginRequest(String id, String password) {
		NetworkCall newCall = new NetworkCall();
		newCall.setType(NetworkCall.LOGIN_REQUEST);
		newCall.setUserID(id);
		newCall.setPassword(password);
		sendData(newCall);
	}

	void onLoginSuccessed(String pId) {
		this.id = pId;
		rootPath = "c:\\storage\\" + pId;
		initExplorerMainView();
	}

	void onLoginFailed() {
		System.out.println("Login Failed");
	}

	void onFileInfo(NetworkCall pCall) {
		explorerController.drawTable(pCall.getFIle());
		currentLocation = pCall.getFIle().getPath();
	}

	void onFilePartitionDownloaded(NetworkCall pCall) {
		try {
			File testFile = new File("c:/test" + pCall.getRelativePath());
			if (!pCall.getFIle().isDirectory()) {
				FileOutputStream fos = new FileOutputStream(testFile);
				int readLen = 0;
				byte[] buffer = new byte[1024];
				if (pCall.getSize() != 0) {
					while (true) {
						readLen = is.read(buffer);
						fos.write(buffer, 0, readLen);
						if (readLen < 1024) {
							break;
						}
					}
				}
				fos.close();
				System.out.println("file donwload end");
			} else {
				testFile.mkdirs();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	void requestMyFileInfo() {
		Thread th = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				NetworkCall newCall = new NetworkCall();
				newCall.setType(NetworkCall.FILE_INFO_REQUEST);
				newCall.setUserID(id);
				sendData(newCall);
			}
		};
		th.start();
		try {
			th.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void requestStepInto(String path) {
		Thread th = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				NetworkCall newCall = new NetworkCall();
				newCall.setType(NetworkCall.FILE_INFO_REQUEST);
				newCall.setUserID(id);
				newCall.setFile(new File(path));
				sendData(newCall);
			}
		};
		th.start();
		try {
			th.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	public void requestFileDownload(String path) {
		NetworkCall newCall = new NetworkCall();
		newCall.setType(NetworkCall.FILE_DOWNLOAD_REQUEST);
		newCall.setFile(new File(path));
		sendData(newCall);
	}

	void initExplorerMainView() {
		FXMLLoader loader = new FXMLLoader();
		GridPane explorerPane = null;
		loader.setLocation(ClientMain.class.getResource("view/ExplorerMainView.fxml"));
		try {
			explorerPane = (GridPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.explorerController = loader.getController();
		this.explorerController.setMain(this);
		changeScene(explorerPane);
		requestMyFileInfo();
	}

	void changeScene(GridPane gPane) {
		final GridPane constPane = gPane;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				primaryStage.setScene(new Scene(constPane));
			}
		});
	}

}
