/**
 * misono_hufs_CSE_designPattern
 * 
 */
package hufs.ces.grimpan.javafx;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GrimPanJavaFXMain  extends Application {

	@Override
	public void start(Stage primaryStage){


		GrimPanPane root = new GrimPanPane();
		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("GrimPan");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
