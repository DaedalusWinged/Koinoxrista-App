package winged.daedalus.koinoxristaapp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import winged.daedalus.koinoxristaapp.screenhandling.ScreensController;

//INFO for standalone exe, check: https://maven.apache.org/plugins/maven-assembly-plugin/usage.html

/**
 * 
 * Created by TeoGia on Jul 7, 2015
 */
public class ScreensFramework extends Application {
	
	//Declare all app's screens here (name and fxml resource)
	public static final String MAIN_MENU = "mainMenu";
	public static final String MAIN_MENU_FXML = "/fxml/mainMenu_xml.fxml";
	public static final String LOGIN = "login";
	public static final String LOGIN_FXML = "/fxml/login_xml.fxml";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ScreensController mainContainer = new ScreensController();
		//Loading our screens
		mainContainer.loadScreen(ScreensFramework.LOGIN, ScreensFramework.LOGIN_FXML);
		mainContainer.loadScreen(ScreensFramework.MAIN_MENU, ScreensFramework.MAIN_MENU_FXML);
		//Set the screen to be displayed first
		mainContainer.setScreen(ScreensFramework.LOGIN);
		
		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}