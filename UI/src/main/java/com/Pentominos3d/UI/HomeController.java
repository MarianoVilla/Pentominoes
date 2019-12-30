package com.Pentominos3d.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Home page controller.
 *
 */
public class HomeController implements Initializable {
	
	@FXML
	AnchorPane pane;
	@FXML
	private Box spinningBox;

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupAnimation();
		setBoxMaterial();
	}
	private void setBoxMaterial() {
		Image diffuseMap = new Image("/MetalTexture.jpg");
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(diffuseMap);
		spinningBox.setMaterial(material);
	}
	private void setupAnimation() {

		RotateTransition rotate = new RotateTransition(Duration.millis(10000), spinningBox);
		rotate.setAxis(new Point3D(1,1,0));
		rotate.setByAngle(360);
		rotate.setCycleCount(Animation.INDEFINITE);  
		rotate.setFromAngle(0);
		rotate.setToAngle(360);
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.play(); 
	}
	public void startBoxPacking() throws IOException {
	    Stage stage = new Stage();
		String fxmlFile = "/BoxIndex.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		Scene scene = new Scene(rootNode);
		scene.getStylesheets().add("/styles.css");
		stage.setTitle("Box Packing");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/ksirtet.png")));
		stage.show();
	}
	public void startPentominoesPacking() throws IOException {	
	    Stage stage = new Stage();
		String fxmlFile = "/PentominoesIndex.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		Scene scene = new Scene(rootNode, 800, 540);
		scene.getStylesheets().add("/styles.css");

		stage.setTitle("Pentominoes Packing");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/T.png")));
		stage.show();
	}
	

}
