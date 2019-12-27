package com.Pentominos3d.UI;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.AlgorithmPackingResult;
import Entities.Container;
import Entities.ContainerPackingResult;
import Entities.Item;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.PhongMaterial;

/**
 * Controller in charge of the 3D box solutions representation.
 *
 */
public class Box3DViewerController implements Initializable {

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
	private final double SCALE = 20;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private void initMouseControl(SmartGroup group, Scene scene) {
		Rotate xRotate;
		Rotate yRotate;
		pane.getTransforms().addAll(xRotate = new Rotate(0, Rotate.X_AXIS), yRotate = new Rotate(0, Rotate.Y_AXIS));
		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		scene.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();
		});

		scene.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY - (anchorX - event.getSceneX()));
		});

		scene.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			pane.translateZProperty().set(pane.getTranslateZ() - delta);
		});
	}
	
	public void initData(ContainerPackingResult a, Container bin) {
		this.group = new SmartGroup();
		this.Algo = a.getAlgorithmPackingResults().get(0);

		this.binBox = new Box(a.getContainerLength(), a.getContainerHeight(), a.getContainerWidth());

		binBox.setScaleX(SCALE+2);
		binBox.setScaleY(SCALE+2);
		binBox.setScaleZ(SCALE+2);
		binBox.setDrawMode(DrawMode.LINE);

		
		binBox.setTranslateX(120);
		binBox.setTranslateY(((SCALE * binBox.getHeight()) / 2)-5);
		binBox.setTranslateZ(-(binBox.getDepth() / 2)+2);
		
		group.getChildren().add(binBox);

		for (Item i : Algo.getPackedItems()) {
			Box newBox = new Box(i.getPackDimX(), i.getPackDimY(), i.getPackDimZ());

			newBox.setScaleX(SCALE);
			newBox.setScaleY(SCALE);
			newBox.setScaleZ(SCALE);

			PhongMaterial material = new PhongMaterial();
			material.setDiffuseMap(new Image(getClass().getResourceAsStream("/MetalTexture.jpg")));
			newBox.setMaterial(material);
			newBox.setTranslateX((i.getCoordX() * SCALE) - 20);
			newBox.setTranslateY((i.getCoordY() * SCALE) + 10);
			newBox.setTranslateZ((i.getCoordZ() * SCALE) - 10);
			group.getChildren().add(newBox);
		}
		pane.getChildren().add(group);
		initMouseControl(this.group, this.pane.getScene());
	}

}
