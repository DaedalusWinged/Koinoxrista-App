package winged.daedalus.koinoxristaapp.fxml_controllers;

import winged.daedalus.koinoxristaapp.screenhandling.ScreensController;
import winged.daedalus.koinoxristaapp.screenhandling.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import winged.daedalus.koinoxristaapp.ScreensFramework;

/**
 *
 * @author tsirkos
 */
public class Login_xmlController implements ControlledScreen {
	
	//TODO remove them once the login implementation is ready
	private static final String username = "tsirkos";
	private static final String password = "giakovas";
	
	ScreensController myController;
	
	@FXML protected Text actiontarget;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
    
    @FXML 
	protected void handleSubmitButtonAction(ActionEvent event) {
		if (!"".equals(usernameField.getText()) && !"".equals(passwordField.getText())) {
			actiontarget.setText(authenticate(usernameField.getText(), passwordField.getText()));
		}
    }
	
	@FXML
	protected void handleEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (!"".equals(usernameField.getText()) && !"".equals(passwordField.getText())) {
				actiontarget.setText(authenticate(usernameField.getText(), passwordField.getText()));
			}
	
		}

	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	@FXML
	private String authenticate(String uname, String pswd){
		if (uname.equals(username)  && pswd.equals(password)) {
			myController.setScreen(ScreensFramework.MAIN_MENU);
			return "authenticated";
		} else {
			return "Wrong username or password";

		}
	}
}