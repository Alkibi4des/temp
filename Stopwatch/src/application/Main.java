package application;

import application.view.WatchUnit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	Scene scene;
	String[] labels = { "case1", "case2" };

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		VBox vbox = new VBox();
		for (int i = 0; i < labels.length; i++) {
			HBox hbox = new WatchUnit().getWatchUnit(labels[i]);
			vbox.getChildren().add(hbox);
		}		
		scene = new Scene(vbox, 250, labels.length*25);
		stage.setScene(scene);
		stage.setTitle("Stopwatch");
		stage.setAlwaysOnTop(true);
		stage.show();
	}
}
