package com.Pentominos3d.UI;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entities.AlgorithmPackingResult;
import Entities.Container;
import Entities.DefaultContainer;
import Entities.Item;
import TetrisLike3DSolver.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TetrisLikeIndexController implements Initializable {

	@FXML ChoiceBox<Pentomino> cboPentominoes;
	@FXML TableView<Pentomino> tblPentominoes;
	@FXML Button btnClearPentos;
	@FXML Button btnAddPento;
	@FXML Button btnClearOutput;
	@FXML Button btnPackAll;
	@FXML TableView<LayeredContainer> tblOutput;
	@FXML TextField txtID;
	@FXML TableColumn<LayeredContainer, Integer> colOutputID;
	@FXML TableColumn<LayeredContainer, Double> colOutputLength;
	@FXML TableColumn<LayeredContainer, Double> colOutputWidth;
	@FXML TableColumn<LayeredContainer, Double> colOutputHeight;
	@FXML TableColumn<LayeredContainer, Double> colOutputValue;
	
	@FXML TableColumn<Pentomino, Integer> colInputID;
	@FXML TableColumn<Pentomino, Double> colInputHeight;
	@FXML TableColumn<Pentomino, Double> colInputValue;
	@FXML TableColumn<Pentomino, Color> colInputColor;
	@FXML ImageView imgPento;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn colOutputView;
	
	@FXML public void clearOutput() { tblOutput.getItems().clear(); }
	@FXML public void clearPentos() { tblPentominoes.getItems().clear(); }
	@FXML public void goBack() {}
	
	@FXML public void packAll() {
		List<Pentomino> Pentominoes = tblPentominoes.getItems();
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(2.5, 4, 16);
		ArrayList<LayeredContainer> Solution = Solver3D.PackAll(Pentominoes);
		tblOutput.getItems().addAll(Solution);
		
	}
	@FXML public void fitInOne() {
		List<Pentomino> Pentominoes = tblPentominoes.getItems();
		Layering3DPentominoesSolver Solver3D = new Layering3DPentominoesSolver(2.5, 4, 16);
		LayeredContainer Solution = Solver3D.Pack(Pentominoes);
		tblOutput.getItems().add(Solution);
	}
	
	@FXML
	public void addPento() {
		if (!ControllersHelper.validNumber(txtID.getText())) {
			new Alert(AlertType.WARNING, "The ID has to be a non-negative number.", ButtonType.OK).show();
			return;
		}
		Pentomino selectedPento = cboPentominoes.getSelectionModel().getSelectedItem();
		selectedPento.setId(Integer.valueOf(txtID.getText()));
		tblPentominoes.getItems().add(selectedPento);
	}

	private void setupCboPentominoes() {
		cboPentominoes.getItems().setAll(PentominoesDefaultFactory.Create('L'), PentominoesDefaultFactory.Create('P'),
				PentominoesDefaultFactory.Create('T'));
		cboPentominoes.setOnAction(this::setPentoImage);
		cboPentominoes.getSelectionModel().selectFirst();
	}

	private void setPentoImage(ActionEvent action) {
		Character pentoChar = cboPentominoes.getSelectionModel().getSelectedItem().getTypeChar();
		imgPento.setImage(new Image(getClass().getResourceAsStream("/images/" + pentoChar + ".png")));
	}

	private void setupInputTable() {
		colInputID.setCellValueFactory(new PropertyValueFactory<Pentomino, Integer>("id"));
		colInputHeight.setCellValueFactory(new PropertyValueFactory<Pentomino, Double>("height"));
		colInputValue.setCellValueFactory(new PropertyValueFactory<Pentomino, Double>("value"));
		colInputColor.setCellValueFactory(new PropertyValueFactory<Pentomino, Color>("color"));
	}

	@SuppressWarnings("unchecked")
	private void setupOutputTable() {

		colOutputID.setCellValueFactory(new PropertyValueFactory<LayeredContainer, Integer>("id"));
		colOutputLength.setCellValueFactory(new PropertyValueFactory<LayeredContainer, Double>("length"));
		colOutputWidth.setCellValueFactory(new PropertyValueFactory<LayeredContainer, Double>("width"));
		colOutputHeight.setCellValueFactory(new PropertyValueFactory<LayeredContainer, Double>("height"));
		colOutputValue.setCellValueFactory(new PropertyValueFactory<LayeredContainer, Double>("value"));
		colOutputView
				.setCellFactory(ActionButtonTableCell.<LayeredContainer>forTableColumn("View", (LayeredContainer a) -> {

					String fxmlFile = "/fxml/TetrisLike3DViewer.fxml";
					FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
					Parent rootNode = null;
					try {
						rootNode = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					rootNode.setStyle("-fx-background-color: transparent;");
					/*
					 * PerspectiveCamera camera = new PerspectiveCamera(); camera.setTranslateX(0);
					 * camera.setTranslateY(-300); camera.setTranslateZ(-300);
					 * camera.setFieldOfView(90);
					 * scene.setCamera(camera);
					 */
					Scene scene = new Scene(rootNode, 700, 600);
					Stage stage = new Stage();
					stage.setTitle("Pentominos3D");
					stage.setScene(scene);
					TetrisLike3DViewerController controller = loader.<TetrisLike3DViewerController>getController();
					controller.initData(a);
					stage.show();
				}));
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupCboPentominoes();
		setupInputTable();
		setupOutputTable();
		
	}

}
