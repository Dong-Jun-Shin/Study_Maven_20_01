package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class SalesMainController implements Initializable {
	@FXML
	private TabPane salesMainPane;
	@FXML
	private Tab watch;
	@FXML
	private SalesWatchTabController salesWatchTabController;
	@FXML
	private Tab trade;
	@FXML
	private SalesTradeTabController salesTradeTabController;

	private Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		salesWatchTabController.setSttController(salesTradeTabController);
		
		salesMainPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == trade) {
				try {
					salesTradeTabController.setTotalPrice();
				} catch (Exception e) {
					System.out.println("changed() error = " + e.getMessage());
				}
			} 
		});
	}

	public void menuLogout(ActionEvent event) {
		new MenuController().menuLogout(primaryStage);
	}

	public void menuClose(ActionEvent event) {
		MenuController.menuClose();
	}

	public void menuConnectInfo(ActionEvent event) {
		MenuController.menuConnectInfo();
	}

	public void menuProgramInfo(ActionEvent event) {
		MenuController.menuProgramInfo();
	}
}
