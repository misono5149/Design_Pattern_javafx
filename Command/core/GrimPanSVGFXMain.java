/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.core;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GrimPanSVGFXMain  extends Application {

	@Override
	public void start(Stage primaryStage){

		GrimPanController control = new GrimPanController();
		GrimPanPaneView root = new GrimPanPaneView(control);
		root.parentStage = primaryStage;
		control.view = root;
		
		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("GrimPan");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
