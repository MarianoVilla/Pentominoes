package com.Pentominos3d.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PentominoesHomeController implements Initializable {
	
	@FXML
	AnchorPane pane;
	@FXML
	private Box spinningBox;

	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupAnimation();
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
		String fxmlFile = "/fxml/PentominoesIndex.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		Scene scene = new Scene(rootNode);
		scene.getStylesheets().add("/styles/styles.css");
		stage.setTitle("Box Packing");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/ksirtet.png")));
		stage.show();
	}
	public void startPentominoesPacking() throws IOException {	
	    Stage stage = new Stage();
		String fxmlFile = "/fxml/TetrisLikeIndex.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		Scene scene = new Scene(rootNode, 750, 600);
		scene.getStylesheets().add("/styles/styles.css");

		stage.setTitle("Pentominoes Packing");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/t.png")));
		stage.show();
	}
	

}
