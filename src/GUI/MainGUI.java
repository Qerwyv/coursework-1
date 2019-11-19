package GUI;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;


public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
			AnchorPane root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene scene = new Scene(root, 584, 803);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("My First Coursework");
            primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
