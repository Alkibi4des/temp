package application.view;

import java.util.Calendar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class WatchUnit {
	VBox vBox;
	HBox hBox;
	Label label;
	Button sButton, rButton;
	Text text;
	Timeline timeline;
	int today =  Calendar.getInstance().get(Calendar.DATE);

	int hours = 0, mins = 0, secs = 0, millis = 0;
	boolean sos = true;

	void change(Text text) {
		if(today!=Calendar.getInstance().get(Calendar.DATE)) {
			today = Calendar.getInstance().get(Calendar.DATE);
			millis = 0;
			secs = 0;
			hours = 0;
			mins = 0;
		}
		if (millis == 1000) {
			secs++;
			millis = 0;
		}
		if (secs == 60) {
			mins++;
			secs = 0;
			application.Saver.Save(label.getText(), hours, mins, secs, millis);
		}
		if (mins == 60) {
			hours++;
			mins = 0;
		}
		text.setText((((hours / 10) == 0) ? "0" : "") + hours + ":" + (((mins / 10) == 0) ? "0" : "") + mins + ":"
				+ (((secs / 10) == 0) ? "0" : "") + secs + ":"
				+ (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis++);
	}

	public HBox getWatchUnit(String pLabel) {
		text = new Text("00:00:00:000");

		timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				change(text);
			}
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		sButton = new Button("Start");
		sButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (sos) {
					timeline.play();
					sos = false;
					sButton.setText("Stop");
				} else {
					application.Saver.Save(pLabel, hours, mins, secs, millis);
					timeline.pause();
					sos = true;
					sButton.setText("Start");
				}
			}
		});
		String timeString = application.Saver.load(pLabel);
		if (timeString != null) {
			String[] timeSplit = timeString.split(":");
			this.millis = Integer.valueOf(timeSplit[3]);
			this.secs = Integer.valueOf(timeSplit[2]);
			this.mins = Integer.valueOf(timeSplit[1]);
			this.hours = Integer.valueOf(timeSplit[0]);
		}
		label = new Label(pLabel);
		hBox = new HBox(30);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(label, text, sButton);
		return hBox;
	}
}
