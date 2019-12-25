package com.Pentominos3d.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Entities.ClassAContainer;
import Entities.ClassBContainer;
import Entities.ClassCContainer;
import Entities.Container;
import Entities.ContainerPackingResult;
import Entities.DefaultContainer;
import Entities.Item;
import Services.PackingService;
import TetrisLike2DSolver.Pentominos2DSolverMT;
import TetrisLike2DSolver.Pentominos2DSolverMT.Solution;
import TetrisLike3DSolver.LayeredContainer;
import TetrisLike3DSolver.Layering3DPentominoesSolver;
import TetrisLike3DSolver.Pentomino;
import TetrisLike3DSolver.PentominoesDefaultFactory;
import TetrisLike3DSolver.SolutionLayer;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);


    public static void main(String[] args) throws Exception {
        launch(args);
    }

	public void start(Stage stage) throws Exception {
    	
		
		/*
		 * ArrayList<Item> Items = new ArrayList<Item>() {{ add(new ClassAContainer(1,
		 * 100, 1)); add(new ClassBContainer(1, 0, 3)); add(new ClassCContainer(1, 0,
		 * 2)); }};
		 * 
		 * ArrayList<ContainerPackingResult> PackingResults =
		 * PackingService.PackAll(Items, new DefaultContainer(1));
		 * 
		 * FXMLLoader fxmlLoader = new
		 * FXMLLoader(getClass().getResource("/fxml/Pentominos3DViewer.fxml")); Parent
		 * root1; try { root1 = (Parent) fxmlLoader.load(); } catch (IOException e) {
		 * log.debug("ERROR: " + e.getMessage() + "\n" + e.getCause()); return; } stage
		 * = new Stage(); stage.setTitle("3D viewer"); PerspectiveCamera camera = new
		 * PerspectiveCamera();
		 * 
		 * camera.setTranslateX(0); camera.setTranslateY(0); camera.setTranslateZ(0);
		 * 
		 * camera.setFieldOfView(100); Scene scene = new Scene(root1, 1200, 800, true);
		 * scene.setFill(Color.ANTIQUEWHITE); scene.setCamera(camera);
		 * stage.setScene(scene); Pentominoes3DViewerController controller =
		 * fxmlLoader.<Pentominoes3DViewerController>getController();
		 * controller.initData(PackingResults.get(0).getAlgorithmPackingResults().get(0)
		 * , new DefaultContainer()); stage.show();
		 */
		 
		 
		
		
		/*
		 * ArrayList<Pentomino> Pentominoes = new ArrayList<Pentomino>();
		 * Pentominoes.addAll(PentominoesDefaultFactory.CreateMany(1, 'L'));
		 * 
		 * Pentominoes.addAll(PentominoesDefaultFactory.CreateMany(1, 'P'));
		 * Pentominoes.addAll(PentominoesDefaultFactory.CreateMany(1, 'T'));
		 * 
		 * Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(2.5,
		 * 4, 16); ArrayList<LayeredContainer> Solutions = Solver3D.Run(Pentominoes);
		 * String fxmlFile = "/fxml/TetrisLike3DViewer.fxml"; FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource(fxmlFile)); Parent rootNode =
		 * loader.load(); PerspectiveCamera camera = new PerspectiveCamera();
		 * camera.setTranslateX(600); camera.setTranslateY(300);
		 * camera.setTranslateZ(0); camera.setFieldOfView(-1000); Scene scene = new
		 * Scene(rootNode, 800, 800, true); scene.setCamera(camera);
		 * stage.setTitle("Pentominos3D"); stage.setScene(scene);
		 * TetrisLike3DViewerController controller =
		 * loader.<TetrisLike3DViewerController>getController();
		 * controller.initData(Solutions); stage.show();
		 */
		 
		 
		 

		
		String fxmlFile = "/fxml/HomePage.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent rootNode = (Parent) loader.load();
		rootNode.setStyle("-fx-background-color: transparent;");
		//PerspectiveCamera camera = new PerspectiveCamera();
		//camera.setTranslateZ(-200);
		Scene scene = new Scene(rootNode,700,600);
		stage.setResizable(false);
		//scene.setCamera(camera);
		scene.getStylesheets().add("/styles/styles.css");
		stage.setTitle("Pentominos3D");
		stage.setScene(scene);
		stage.show();
		 
		 
    }
    
}
