package com.Pentominos3d.UI;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.AlgorithmPackingResult;
import Entities.Container;
import Entities.ContainerPackingResult;
import Entities.DefaultContainer;
import Entities.Item;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Pentominoes3DViewerController implements Initializable {

	@FXML
	Box binBox;
	private AlgorithmPackingResult Algo;
	@FXML
	AnchorPane pane;
	double anchorX, anchorY;
	double anchorAngleX = 0;
	double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty();
	private final DoubleProperty angleY = new SimpleDoubleProperty();
	private SmartGroup group;
	private final double XSCALE = 50;
	private final double YSCALE = 50;
	private final double ZSCALE = 50;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	private void initMouseControl(SmartGroup group, Scene scene) {
		Rotate xRotate;
		Rotate yRotate;
		pane.getTransforms().addAll(
				xRotate = new Rotate(0, Rotate.X_AXIS),
				yRotate = new Rotate(0, Rotate.Y_AXIS)
		);
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);
		
		scene.setOnMousePressed(event ->{
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});
		
		scene.setOnMouseDragged(event ->{
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY - (anchorX - event.getSceneX()));
		});
		
		scene.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			pane.translateZProperty().set(pane.getTranslateZ() + delta);
		});
	}
//TODO: fix visuals.
	public void initData(ContainerPackingResult a, Container bin) {
		this.group = new SmartGroup();
		pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		pane.translateXProperty().set(300);
		pane.translateYProperty().set(300);
		pane.translateZProperty().set(0);
		 

		this.Algo = a.getAlgorithmPackingResults().get(0);
		//this.binBox = new Box(bin.getLength(), bin.getHeight(), bin.getWidth());
		/* binBox.setRotationAxis(new Point3D(0,1,0)); */
		/* binBox.setRotate(-90); */
		
		//binBox.setScaleX(51); binBox.setScaleY(51); binBox.setScaleZ(51);
		//binBox.setDrawMode(DrawMode.LINE);
		//pane.getChildren().add(binBox);

		for (Item i : Algo.getPackedItems()) {
			Box newBox = new Box(i.getPackDimX(), i.getPackDimY(), i.getPackDimZ());
			newBox.setScaleX(XSCALE);
			newBox.setScaleY(YSCALE);
			newBox.setScaleZ(ZSCALE);
			/*
			 * These bunch of magic numbers make it work for A-Class-only packings.
			 * newBox.setTranslateX(group.getTranslateX()-((i.getCoordX()*50)+(((binBox.
			 * getWidth()/2)-63))));//Z
			 * newBox.setTranslateY(group.getTranslateY()-((i.getCoordY()*50)+(((binBox.
			 * getHeight())*57)-3)));//Y
			 * newBox.setTranslateZ(group.getTranslateZ()-((i.getCoordZ()*50)+(((binBox.
			 * getDepth()/2)*32)-1)));
			 */
			/*
			 * newBox.setTranslateX(group.getTranslateX()-((i.getCoordX()*50)+(((binBox.
			 * getWidth()/2))-88)));
			 * newBox.setTranslateY(group.getTranslateY()-((i.getCoordY()*50)+(((binBox.
			 * getHeight())*50)+25)));
			 * newBox.setTranslateZ(group.getTranslateZ()-((i.getCoordZ()*50)+(((binBox.
			 * getDepth()/2)*50)-120)));
			 */
			/*
			 * newBox.setTranslateX(((i.getCoordX()*50)+(((binBox.getWidth()/2)))));
			 * newBox.setTranslateY(((i.getCoordY()*50)+(((binBox.getHeight())*50))));
			 * newBox.setTranslateZ(((i.getCoordZ()*50)+(((binBox.getDepth()/2)*50))));
			 */
			  newBox.setTranslateX(((i.getCoordX()*XSCALE)));
			  newBox.setTranslateY(((i.getCoordY()*YSCALE)));
			  newBox.setTranslateZ(((i.getCoordZ()*ZSCALE)));
			  group.getChildren().add(newBox);
		}
		group.setTranslateZ(-100);
		pane.getChildren().add(group);
		initMouseControl(this.group, this.pane.getScene());
	}

}
