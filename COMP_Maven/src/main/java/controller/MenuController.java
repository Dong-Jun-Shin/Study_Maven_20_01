package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.DataUtil;

public class MenuController {
	/**
	 * menuLogout() : 로그인 창으로 보여준다.
	 * 
	 * @param primaryStage 바뀔 메인 윈도우
	 */
	public void menuLogout(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginMain.fxml"));
			Parent root = loader.load();
			DataUtil.setTheme(root);

			LoginMainController lController = loader.getController();
			lController.setPrimaryStage(primaryStage);
			lController.setRoot(root);

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("btnLogin() error = " + e.getMessage());
		}
	}

	/**
	 * menuClose() : 창을 종료한다.
	 *
	 */
	public static void menuClose() {
		Platform.exit();
	}

	/**
	 * menuConnectInfo() : 문의할 곳에 대한 정보를 보여준다.
	 * 
	 */
	public static void menuConnectInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("문의하기");

		StringBuffer sb = new StringBuffer();
		sb.append("업체명\t: (주)훈민\n");
		sb.append("전화번호\t: 02-3333-1144\n");
		sb.append("이메일\t: progDev@dev2.com\n");
		sb.append("영업시간\t: 오전 9시 ~ 오후 6시");

		alert.setContentText(sb.toString());
		alert.showAndWait();
	}

	/**
	 * menuProgramInfo() : 프로그램의 정보를 보여준다.
	 * 
	 */
	public static void menuProgramInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("프로그램 정보");

		StringBuffer sb = new StringBuffer();
		sb.append("프로그램명\t: Component Order Management Program\n");
		sb.append("버전\t: Ver.1.0\n");

		alert.setContentText(sb.toString());
		alert.showAndWait();
	}
}
