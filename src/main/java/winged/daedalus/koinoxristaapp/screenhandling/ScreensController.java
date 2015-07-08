package winged.daedalus.koinoxristaapp.screenhandling;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Created by tsirkos on Jul 7, 2015
 */
public class ScreensController extends StackPane {
	
	private final static Logger LOGGER = Logger.getLogger(ScreensController.class.getName());
	private final HashMap<String, Node> screens;//TODO recompile with Xlint to see if i can get rid of the compilation warnings about unsafe operations
	
	/**
	 * This is the constructor
	 */
	public ScreensController() {
		LOGGER.setLevel(Level.ALL);
		this.screens = new HashMap<String, Node>();
	}
	
	/**
	 * <h2>ScreensController.addScreen(String name, Node screen)</h2>
	 * Simply adds the Screen's name and top Node into the HashMap.
	 * @param name
	 * @param screen 
	 */
	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}
	
	/**
	 * <h2>ScreensController.loadScreen(String name, String resource)</h2>
	 * This method loads the fxml file specified by resource, and it gets the top Node for the screen.
	 * We can also get the controller associated to this screen, which allows us to set the parent for the screen,
	 * as all the controllers shared the common type ControlledScreen. Finally the screen is added to the screens hash map.
	 * As you can see from the code, the loaded fxml file, doesn't get added to the scene graph, so the loaded screen doesn't
	 * get displayed or loaded to the screen.
	 * @param name Screen's name
	 * @param resource Screen's FXML
	 * @return true or false
	 */
	public boolean loadScreen(String name, String resource) {
		try {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			return true;
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "IOException", ex);
			return false;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception", e);
			return false;
		}
	}
	
	/**
	 * <h2>ScreensController.setScreen(final String name)</h2>
	 * This method displays a screen with a given screen name.
	 *	- First the method verifies that the screen has been previously loaded.
	 *	- We check if there is already a screen been displayed, so we need to play the screen transition sequence. If there isn't any screen, we just add it to the graph and perform a nice fade-in animation.
	 *	- If there is a screen already been displayed, we play an animation to fade out the current screen, and we defined an eventHandler to handle execution after this.
     *	- Once the screen is invisible, we remove it from the graph, and we add the new screen. Again, a nice animation is played to show the new screen.
	 * @param name Screen's name
	 * @return true or false
	 */
	public boolean setScreen(final String name){
		if (screens.get(name) != null) {//screen loaded
			final DoubleProperty opacity = opacityProperty();
			
			//Check if there is more than one screen
			if (!getChildren().isEmpty()) {
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity,1.0)), new KeyFrame(new Duration(500),
				
						new EventHandler() {
							
							@Override
							public void handle(Event t) {
								//remove displayed screen
								getChildren().remove(0);
								//add new screen
								getChildren().add(0, screens.get(name));
								Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
									new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
			} else {
				//if no one else has been displayed, just show
				setOpacity(0.0);
				getChildren().add(screens.get(name));
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)), 
					new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		} else {
			LOGGER.log(Level.WARNING, "Screen hasn't been loaded yet!");
			return false;
		}
	}
	
	/**
	 * <h2>ScreensController.unloadScreen(String name)</h2>
	 * Simply removes the screen from our HashMap, and reports the status of this operation.
	 * @param name Screen's name
	 * @return true or false
	 */
	public boolean unloadScreen(String name) {
		if (screens.remove(name) == null) {
			LOGGER.log(Level.WARNING, "Screen didn't exist in the HashMap!");
			return false;
		} else {
			return true;
		}
	}
}