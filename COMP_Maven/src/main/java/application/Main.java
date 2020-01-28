package application;

import controller.LoginMainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginMain.fxml"));
			Parent root = loader.load();

			LoginMainController controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
			controller.setRoot(root);

			new JMetro(root, Style.LIGHT);
			root.setStyle("-fx-background-color:#EFF8FF;");
			
			Scene scene = new Scene(root);

			primaryStage.setTitle("COMP(Component Order Management Program)[Ver1.0]");
			primaryStage.getIcons().add(new Image("/image/titleIcon.png"));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("start() error = " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}