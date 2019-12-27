package com.Pentominos3d.UI;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point of the app.
 *
 */
public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);


    public static void main(String[] args) throws Exception {
    	log.debug("Starting the app");
        launch(args);
    }

	public void start(Stage stage) throws Exception {
		String fxmlFile = "/HomePage.fxml";
		log.debug("Home FXML page: " + fxmlFile);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		HostServices services = this.getHostServices(); 
		String url = services.resolveURI(services.getCodeBase(), "/containers.jpg");
		rootNode.setStyle("-fx-background-image: url('"+url+"');");
		Scene scene = new Scene(rootNode,700,600);
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/ksirtet.png")));
		scene.getStylesheets().add("/styles.css");
		stage.setTitle("Pentominos3D");
		stage.setScene(scene);
		stage.show();
    }
    
}
