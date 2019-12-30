package com.Pentominos3d.UI;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.PentoColor;
import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.SolutionLayer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;

/**
 * A controller in charge of the 3D pentominoes solutions representation.
 *
 */
public class Pentominoes3DViewerController implements Initializable {

	private static final int SCALE = 40;
	private SmartGroup group;
	final String cssDefault = "-fx-border-width: 3;";
	@FXML
	AnchorPane pane;
	double anchorX, anchorY;
	double anchorAngleX = 0;
	double anchorAngleY = 0;
	private final DoubleProperty angleX = new SimpleDoubleProperty();
	private final DoubleProperty angleY = new SimpleDoubleProperty();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.group = new SmartGroup();
		pane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
	}
	/**
	 * Initializes from a single layered container.
	 * 
	 * @param container
	 */
	public void initData(LayeredContainer container) {
		drawLayeredContainer(container, 0);
		pane.getChildren().add(group);
		translateGroup();
		initMouseControl(this.group, this.group.getScene());
	}

	private void translateGroup() {
		group.setTranslateX(50);
		group.setTranslateY(0);
		group.setTranslateZ(-300);
	}
	private void drawLayeredContainer(LayeredContainer container, double containerOffset) {
		drawLayers(container.getLayers(), containerOffset);
		drawContainer(container);

	}

	@Deprecated
	private void drawContainer(LayeredContainer container) {
		Box containerBox = new Box(container.getLength(), container.getHeight(), container.getWidth());
		containerBox.setScaleX((SCALE / 2));
		containerBox.setScaleY((SCALE / 2)+3);
		containerBox.setScaleZ((SCALE / 2)+1);
		containerBox.setDrawMode(DrawMode.LINE);
		containerBox.setTranslateX(310);
		containerBox.setTranslateY(SCALE);
		containerBox.setTranslateZ(90);
		group.getChildren().add(containerBox);
	}

	private void drawLayers(List<SolutionLayer> layers, double xOffset) {
		double zOffset = 0;
		for (SolutionLayer layer : layers) {
			int[][] board = layer.getBoard();
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j] == 0) continue;
					 
					Box newBox = new Box(0.5, 0.5, 0.5);
					newBox.setScaleX(SCALE);
					newBox.setScaleY(SCALE);
					newBox.setScaleZ(SCALE);
					newBox.setTranslateX(((j * SCALE) + xOffset) / 2);
					newBox.setTranslateY((i * SCALE) / 2);
					newBox.setTranslateZ((zOffset + layer.getHeight()) * SCALE);
					Color color = getColorMapping(board[i][j]);
					newBox.setStyle(cssDefault);
					newBox.setMaterial(new PhongMaterial(color));
					group.getChildren().add(newBox);
				}
			}
			zOffset += layer.getHeight();
		}
	}

	private Color getColorMapping(int pentoID) {
		PentoColor pColor = Pentomino.typeIDColorMap.get(pentoID);
		return pColor == null ? Color.GHOSTWHITE : pColor.getFxColor();
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

		pane.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			pane.translateZProperty().set(pane.getTranslateZ() - delta);
		});
	}

}
