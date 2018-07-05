package application.view;

import application.ClientMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginFormController {
	ClientMain mMain;
	@FXML TextField tf_id;
	@FXML TextField tf_pass;
	@FXML Button btn_login;
	@FXML Label label_alarmState;
	AnchorPane mainPane;
	
	public AnchorPane create() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClientMain.class.getResource("view/LoginForm.fxml"));
		try {
		this.mainPane = (AnchorPane) loader.load();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.mainPane;
	}
	public void setMain(ClientMain pMain) {
		this.mMain = pMain;
	}
	
	@FXML private void clickLoginBtn() {
		String userID = tf_id.getText();
		String pass = tf_pass.getText();
		mMain.loginRequest(userID, pass);
	}
}
