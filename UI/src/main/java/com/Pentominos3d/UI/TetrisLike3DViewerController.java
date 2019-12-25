package com.Pentominos3d.UI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.PentoColor;
import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.PentominoesDefaultFactory;
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

//TODO: implement the rest of this UI.
public class TetrisLike3DViewerController implements Initializable {
	
	private static final int XSCALE = 40;
	private static final int YSCALE = 40;
	private static final int ZSCALE = 40;
	private SmartGroup group;
    final String cssDefault = "-fx-border-color: blue;\n"
            + "-fx-border-insets: 5;\n"
            + "-fx-border-width: 3;\n"
            + "-fx-border-style: dashed;\n";
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
		pane.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
		
		//pane.translateXProperty().set(WIDTH/2); 
		//pane.translateYProperty().set(HEIGHT/2);
		//pane.translateZProperty().set(-400);
	}
	public void initData(List<LayeredContainer> containers) {
		double containerOffset = 0;
		for(LayeredContainer container : containers) {
			drawLayeredContainer(container, containerOffset);
			containerOffset += ((container.getWidth() * XSCALE)*2)+5;
		}
		initMouseControl(this.group, this.pane.getScene());
		pane.getChildren().add(group);
	}
	public void initData(LayeredContainer container) {
		drawLayeredContainer(container, 0);
		group.setTranslateX(100);
		group.setTranslateY(250);
		group.setTranslateZ(-300);
		pane.getChildren().add(group);
		initMouseControl(this.group, this.group.getScene());
	}
	private void drawLayeredContainer(LayeredContainer container, double containerOffset) {
		drawLayers(container.getLayers(), containerOffset);
		drawContainer(container);
		
	}
	private void drawContainer(LayeredContainer container) {
		Box containerBox = new Box(container.getLength(), container.getHeight(), container.getWidth());
		containerBox.setScaleX(XSCALE);
		containerBox.setScaleY(YSCALE);
		containerBox.setScaleZ(ZSCALE);
		containerBox.setDrawMode(DrawMode.LINE);
		group.getChildren().add(containerBox);
		containerBox.setTranslateX(XSCALE*8-10);
		containerBox.setTranslateY(YSCALE);
		containerBox.setTranslateZ(ZSCALE);
	}
	private void drawLayers(List<SolutionLayer> layers, double xOffset) {
		double zOffset = 0;
		for(SolutionLayer layer : layers) {
			int[][] board = layer.getBoard();
			for(int i = 0; i < board.length;i++) {
				for(int j = 0;  j < board[0].length;j++) {
					if(board[i][j] == 0) 
						continue;
					Box newBox = new Box(0.5, 0.5, 0.5);
					newBox.setScaleX(XSCALE);
					newBox.setScaleY(YSCALE);
					newBox.setScaleZ(ZSCALE);
					newBox.setTranslateX(((i*XSCALE)+xOffset)/2);
					newBox.setTranslateY((j*YSCALE)/2);
					newBox.setTranslateZ((zOffset+layer.getHeight())*ZSCALE);
					newBox.setStyle(cssDefault);
					Color color = getColorMapping(board[i][j]);
					newBox.setMaterial(new PhongMaterial(color));
					group.getChildren().add(newBox);
				}
			}	
			zOffset += layer.getHeight();
		}
	}
	private Color getColorMapping(int pentoID) {
		PentoColor pColor = Pentomino.typeIDColorMap.get(pentoID);
		return pColor == null ? Color.BLACK : pColor.getFxColor();
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
		
		pane.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			pane.translateZProperty().set(pane.getTranslateZ() + delta);
		});
	}

}
