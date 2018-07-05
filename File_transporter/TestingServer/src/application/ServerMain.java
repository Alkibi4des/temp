package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.model.Constants;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		openConnection();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	void openConnection() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				ServerSocket server;
				try {
					System.out.println("Server Start");
					server = new ServerSocket(Constants.SERVER_PORT);
					ExecutorService executorService = Executors.newFixedThreadPool(Constants.CONNECTION_LIMITE);
					while (true) {
						Socket tempSocket;
						tempSocket = server.accept();
						Thread th = new Thread(new Runnable() {
							@Override
							public void run() {
									new Session(tempSocket);
							}
						});
						executorService.execute(th);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}
