package application.view;

import java.io.File;

import application.ClientMain;
import application.model.FileItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExplorerMainViewController {
	ClientMain mMain;
	FileItem selectedItem;
	@FXML
	private TableView<FileItem> tv_fileList;
	@FXML
	public Button btn_download;
	@FXML
	public Button btn_step_into;

	public void setMain(ClientMain pMain) {
		this.mMain = pMain;
	}

	public void drawTable(File file) {
		TableColumn nameColumn = tv_fileList.getColumns().get(0);
		nameColumn.setCellValueFactory(new PropertyValueFactory("fileName"));

		TableColumn lengthColumn = tv_fileList.getColumns().get(1);
		lengthColumn.setCellValueFactory(new PropertyValueFactory("length"));

		TableColumn typeColumn = tv_fileList.getColumns().get(2);
		typeColumn.setCellValueFactory(new PropertyValueFactory("Type"));

		tv_fileList.setItems(makeFileItem(file));
		tv_fileList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FileItem>() {
			@Override
			public void changed(ObservableValue<? extends FileItem> observable, FileItem oldValue, FileItem newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					selectedItem = newValue;
				}
			}
		});
	}

	ObservableList<FileItem> makeFileItem(File pFile) {
		ObservableList<FileItem> oList = FXCollections.observableArrayList();
		File[] fileList = pFile.listFiles();
		for (File f : fileList) {
			oList.add(new FileItem(f));
		}
		return oList;
	}

	@FXML
	public void onDownloadBtnClick() {
		this.mMain.requestFileDownload(selectedItem.getPath());
	}

	@FXML
	public void onStepIntoBtnClicked() {
		if (!selectedItem.getType().equalsIgnoreCase("file")) {
			mMain.requestStepInto(selectedItem.getPath());
		}
	}

	@FXML
	public void onStepOutBtnClicked() {
		String path = mMain.currentLocation;
		String rootPath = mMain.rootPath;
		System.out.println(path+", "+rootPath);
		if (!rootPath.equalsIgnoreCase(mMain.currentLocation)) {
			mMain.requestStepInto(path.substring(0, path.lastIndexOf(File.separator)));
		}
	}
}
