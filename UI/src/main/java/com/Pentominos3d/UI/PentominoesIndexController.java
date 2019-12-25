package com.Pentominos3d.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Entities.*;
import Services.ItemsFactory;
import Services.PackingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PentominoesIndexController implements Initializable {
	
	private static final Logger log = LoggerFactory.getLogger(PentominoesIndexController.class);
	@FXML private ChoiceBox<Item> cboContainers = new ChoiceBox<Item>();
	@FXML private Button btnAddContainer;
	@FXML TextField txtWidth;
	@FXML TextField txtHeight;
	@FXML TextField txtLength;
	@FXML TextField txtValue;
	@FXML TextField txtQty;
	@FXML TextField txtID;
	@FXML TableView<Item> tblContainers;
	@FXML TableColumn<Item, Integer> colID;
	@FXML TableColumn<Item, Double> colWidth;
	@FXML TableColumn<Item, Double> colHeight;
	@FXML TableColumn<Item, Double>  colLength;
	@FXML TableColumn<Item, Integer>  colQuantity;
	@FXML TableColumn<Item, Double>  colValue;
	@FXML TableColumn<Item, Double>  colVolume;
	int incrementalID = 1;

	
	@FXML TableView <ContainerPackingResult> tblOutput;
	@FXML TableColumn<ContainerPackingResult, Double> colOutWidth;
	@FXML TableColumn<ContainerPackingResult, Double> colOutHeight;
	@FXML TableColumn<ContainerPackingResult, Double> colOutLength;
	@FXML TableColumn<ContainerPackingResult, Double> colOutValue;
	@FXML TableColumn<ContainerPackingResult, Integer> colOutContainers;
	@FXML TableColumn<ContainerPackingResult, Long> colOutPackTime;
	@FXML TableColumn<ContainerPackingResult, Double> colOutPackedVolumePercentage;
	@FXML TableColumn<ContainerPackingResult, Double> colOutVolume;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn colOutView;
	@FXML TextField txtX;
	
	private void setDefaultTextBox() {
		this.txtID.setText(String.valueOf(incrementalID));
		this.txtQty.setText("1");
		this.txtValue.setText("1");
	}

	private void setupCboContainer() {
		cboContainers.getItems().setAll(new ClassAContainer(1, 0, 0), new ClassBContainer(2, 0, 0), new ClassCContainer(3, 0, 0));
		cboContainers.setOnAction(this::setContainerProps);
		this.cboContainers.getSelectionModel().selectFirst();
	}
	private void setupInputTable() {
		colID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
		colWidth.setCellValueFactory(new PropertyValueFactory<Item, Double>("dim1"));
		colHeight.setCellValueFactory(new PropertyValueFactory<Item, Double>("dim2"));
		colLength.setCellValueFactory(new PropertyValueFactory<Item, Double>("dim3"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));
		colValue.setCellValueFactory(new PropertyValueFactory<Item, Double>("value"));
		colVolume.setCellValueFactory(new PropertyValueFactory<Item, Double>("volume"));
	}
	@SuppressWarnings("unchecked")
	private void setupOutputTable() {
		colOutPackTime.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Long>("packTimeInMilliseconds"));
		colOutPackedVolumePercentage.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Double>("percentContainerVolumePacked"));
		colOutWidth.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Double>("containerWidth"));
		colOutHeight.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Double>("containerHeight"));
		colOutLength.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Double>("containerLength"));
		colOutVolume.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult,Double>("containerVolume"));
		colOutContainers.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Integer>("packedItemsCount"));
		colOutValue.setCellValueFactory(new PropertyValueFactory<ContainerPackingResult, Double>("value"));
		colOutView.setCellFactory(ActionButtonTableCell.<ContainerPackingResult>forTableColumn("View", (ContainerPackingResult a) -> {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Pentominoes3DViewer.fxml"));
			Parent root1;
			try {
				root1 = (Parent) fxmlLoader.load();
			} catch (IOException e) {
				log.debug(e.getMessage());
				return;
			}
			root1.setStyle("-fx-background-color: transparent;");
		    Stage stage = new Stage();
		    stage.setTitle("3D viewer");
		    Scene scene = new Scene(root1, 1000, 1000, true);
					
			PerspectiveCamera camera = new PerspectiveCamera(true);
			camera.setTranslateX(0);
			camera.setTranslateY(-100);
			camera.setTranslateZ(-100);
			camera.setFieldOfView(90);
			scene.setCamera(camera);
					 
		    stage.setScene(scene);  
		    Pentominoes3DViewerController controller = 
		    		fxmlLoader.<Pentominoes3DViewerController>getController();
		    controller.initData(a, new DefaultContainer(0));
		    stage.show();
		}));    
	}
	public void initialize(URL location, ResourceBundle resources) {
		setDefaultTextBox(); 
		setupCboContainer();
		setupInputTable();
		setupOutputTable();
	}
	
	public void setContainerProps(ActionEvent action) {
		Item selected = cboContainers.getSelectionModel().getSelectedItem();
		txtWidth.setText(String.valueOf(selected.getDim1()));
		txtHeight.setText(String.valueOf(selected.getDim2()));
		txtLength.setText(String.valueOf(selected.getDim3()));
	}
	public void addContainer(ActionEvent action) {
		if(!validInputNumber()) {
			new Alert(AlertType.WARNING, "You should only input non-negative numeric values.", ButtonType.OK).show();
			return;
		}
		Item toBeAdded = ItemsFactory.Create(cboContainers.getSelectionModel().getSelectedItem().getClass().getName(),
				Integer.valueOf(txtQty.getText()),
				Double.valueOf(txtValue.getText()),
				Integer.valueOf(txtID.getText()));
		if(tryAddToExistingClass(toBeAdded))
			return;
		tblContainers.getItems().add(toBeAdded);
	}
	private boolean validInputNumber() {
		return ControllersHelper.validNumber(txtQty.getText())
				&& ControllersHelper.validNumber(txtValue.getText())
				&& ControllersHelper.validNumber(txtID.getText());
	}

	private boolean tryAddToExistingClass(Item item) {
		for(int i = 0; i < tblContainers.getItems().size();i++) {
			Item existingItem = tblContainers.getItems().get(i);
			if(existingItem.getClass() == item.getClass() && existingItem.getValue() == item.getValue()) {
				existingItem.setQuantity(existingItem.getQuantity()+item.getQuantity());
				tblContainers.getItems().set(i, existingItem);
				return true;
			}
		}
		return false;
	}
	//TODO: change the table to use the ContainerPackingResult directly.
	public void packAll() {
		List<Item> Items = tblContainers.getItems();
		ArrayList<ContainerPackingResult> PackingResults =  PackingService.PackAll(Items, new DefaultContainer(1));
		for(ContainerPackingResult res : PackingResults) {
			tblOutput.getItems().addAll(res);
		}
	}

	@FXML public void pack()	{
		List<Item> Items = tblContainers.getItems();
		ContainerPackingResult packingResult = PackingService.Pack(new DefaultContainer(1), Items);
		tblOutput.getItems().addAll(packingResult);
		
	}
	public void clearItems() {
		tblContainers.getItems().clear();
	}
	public void clearOutput() {
		tblOutput.getItems().clear();
	}
	
	
}
