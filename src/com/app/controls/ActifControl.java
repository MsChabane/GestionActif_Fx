package com.app.controls;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import com.app.DBControls.ActifDB;
import com.app.models.Actif;
import com.app.models.Categorie;
import com.app.models.Fournisseur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


public class ActifControl implements Initializable {
	
	private   ObservableList<Actif> actifs ;

	private double x,y;
	public void close(ActionEvent event ) {
		//StylesControl.saveIndex();
		((Stage)((Button)event.getSource()).getScene().getWindow()).close();
	}
    @FXML
    private TableColumn<Actif, String> controlsCol;

    @FXML
    private TableColumn<Actif, Categorie> ctgCol;

    @FXML
    private TableColumn<Actif, Date > dateCol;

    @FXML
    private TableColumn<Actif, String> dsgCol;

    @FXML
    private TableColumn<Actif, String> etatCol;

    @FXML
    private TableColumn<Actif, Fournisseur> frnsrCol;

    @FXML
    private TableColumn<Actif, String> idCol;

    @FXML
    private Label lblTitle;

    @FXML
    private TableColumn<Actif , String> statusCol;

    @FXML
    private CustomTextField txtRecherche;
    @FXML
    private TableColumn<Actif, Double> vlCol;
    @FXML
    private TableView<Actif> tableActif;
    @FXML
    private TableColumn<Actif, Boolean> comsomCol;
    @FXML
    void addNewItem(ActionEvent event) {	
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/com/app/fxml/ActifAjout.fxml"));
			loader.load();
			ActifAjoutControl control =loader.getController();
			control.setControl(ActifControl.this);
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(tableActif.getScene().getWindow());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.getOwner().setOpacity(0.4);
			stage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    @FXML
    void showDeleteItem(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		actifs=FXCollections.observableArrayList();
		idCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("id"));
		dsgCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("desg"));
		etatCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("evaluation"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("status"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Actif, Date>("dateInvtr"));
		vlCol.setCellValueFactory(new PropertyValueFactory<Actif, Double>("valeur"));
		ctgCol.setCellValueFactory(new PropertyValueFactory<Actif, Categorie>("categorie"));
		frnsrCol.setCellValueFactory(new PropertyValueFactory<Actif, Fournisseur>("fournisseur"));
		comsomCol.setCellValueFactory(new PropertyValueFactory<Actif, Boolean>("estConsomable"));
		Callback<TableColumn<Actif, String>,TableCell<Actif, String>>cellfactory= (TableColumn<Actif, String> col)->{		
			final TableCell<Actif, String> cell = new TableCell<Actif, String>(){
				@Override
				public void updateItem(String item, boolean emplty) {
					if(emplty) {
						setGraphic(null);
						setText(null);
					}else {
						ImageView editIcon = new ImageView(new Image("/com/app/images/editTable.png"));
						editIcon.prefWidth(25);
						editIcon.prefHeight(25);
						ImageView deleteIcon = new ImageView(new Image("/com/app/images/trush.png"));
						deleteIcon.prefWidth(25);
						deleteIcon.prefHeight(25);
						
						Button btnDelete = new Button(null,deleteIcon);
						btnDelete.getStyleClass().add("deleteBtn");
						btnDelete.setOnAction(e->{
							int  index = tableActif.getSelectionModel().getSelectedIndex();
							if(index>=0) {
						    	try {
						    		FXMLLoader loader = new FXMLLoader();
						    		loader.setLocation(getClass().getResource("/com/app/fxml/Alert.fxml"));
									loader.load();
									AlertControl control =loader.getController();
									control.setControls(ActifControl.this,actifs.get(index) ,"Ètes-vous sûre de supprimer l'Actif ? ", index);
									Parent root = loader.getRoot();
									Scene scene = new Scene(root);
									Stage stage = new Stage();
									stage.setScene(scene);
									stage.initOwner(tableActif.getScene().getWindow());
									stage.initModality(Modality.APPLICATION_MODAL);
									stage.initStyle(StageStyle.UNDECORATED);
									stage.getOwner().setOpacity(0.4);
									stage.show();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
					    	}
						});
						Button btnUpdate = new Button(null,editIcon);
						btnUpdate.getStyleClass().add("editBtn");
						btnUpdate.setOnAction(e->{
							int  index = tableActif.getSelectionModel().getSelectedIndex();
							if(index>=0) {
						    	try {
						    		FXMLLoader loader = new FXMLLoader();
						    		loader.setLocation(getClass().getResource("/com/app/fxml/ActifModifier.fxml"));
									loader.load();
									ActifModifierControl control =loader.getController();
									control.setControl(ActifControl.this,actifs.get(index),index);
									Parent root = loader.getRoot();
									Scene scene = new Scene(root);
									Stage stage = new Stage();
									stage.setScene(scene);
									stage.initOwner(tableActif.getScene().getWindow());
									stage.initModality(Modality.APPLICATION_MODAL);
									stage.initStyle(StageStyle.UNDECORATED);
									stage.getOwner().setOpacity(0.4);
									stage.show();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
					    	}
						});
						HBox v = new HBox(btnUpdate,btnDelete);
						v.setAlignment(Pos.CENTER);
						v.setSpacing(10);
						setGraphic(v);
						setText(null);
					}
				}
			};
			return cell;
		};
		controlsCol.setCellFactory(cellfactory);
		statusCol.setCellFactory(col->{
			return new TableCell<Actif, String>(){
				protected void updateItem(String item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item);	
						if(item.equalsIgnoreCase("En Stock")){
							setStyle("-fx-text-fill:orange;");
						}else if(item.equalsIgnoreCase("En service")) {
							setStyle("-fx-text-fill:green;");
						}else if(item.equalsIgnoreCase("Hors service")) {
							setStyle("-fx-text-fill:red;");
						}else if(item.equalsIgnoreCase("En maintenance")) {
							setStyle("-fx-text-fill:#fe5a26;");
						}
					}
				}
			};
		});
		frnsrCol.setCellFactory(col->{
			return new TableCell<Actif, Fournisseur>(){
				protected void updateItem(Fournisseur item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getEntreprise());	
					}
				}
			};
		});
		dateCol.setCellFactory(col->{
			return new TableCell<Actif, Date>(){
				protected void updateItem(Date item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(new SimpleDateFormat("dd/MM/yyyy").format(item));	
					}
				}
			};
		});
		comsomCol.setCellFactory(col->{
			return new TableCell<Actif, Boolean>(){
				protected void updateItem(Boolean item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item?"consomable":"non consomable");
					}
				}
			};
		});
		etatCol.setCellFactory(col->{
			return new TableCell<Actif, String>(){
				protected void updateItem(String item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item);	
						
						if(item.equalsIgnoreCase("Bon")) {
							setStyle("-fx-text-fill:green");
						}else if(item.equalsIgnoreCase("mauvais")) {
							setStyle("-fx-text-fill:red;");
						}
					}
				}
			};
		});
		
		//actifs.addAll(Data.getActifs());
		//tableActif.setItems(actifs);
		loadData();
		FilteredList<Actif>filActifs = new FilteredList<Actif>(actifs, e->true);
		txtRecherche.textProperty().addListener((ob,oldV,newV)->{
			filActifs.setPredicate(actif->{
				String value = txtRecherche.getText().toLowerCase();
				if(actif.getCategorie().getIntitule().toLowerCase().contains(value))return true;
				if(actif.getFournisseur().getEntreprise().toLowerCase().contains(value))return true;
				if(actif.getDesg().toLowerCase().contains(value))return true;
				if(actif.getEvaluation().toLowerCase().contains(value))return true;
				if(actif.getStatus().toLowerCase().contains(value))return true;
				if(actif.getId().equals(value))return true;
				if(value.toLowerCase().equals("consomable") && actif.isEstConsomable())return true;
				if(value.toLowerCase().equals("non consomable") && !actif.isEstConsomable())return true;
				return false;
			});
			SortedList<Actif>sortActifs = new SortedList<Actif>(filActifs);
			sortActifs.comparatorProperty().bind(tableActif.comparatorProperty());
			tableActif.setItems(sortActifs);
		});
		
	}
	  @FXML
	  
	    void drag(MouseEvent event) {
		  	Stage stage = (Stage) ((HBox)event.getSource()).getScene().getWindow();
		  	stage.setX(event.getScreenX()-x);
		  	stage.setY(event.getScreenY()-y);
		  	
	    }

	    @FXML
	    void press(MouseEvent event) {
	    	Stage stage = (Stage) ((HBox)event.getSource()).getScene().getWindow();
	    	stage.setOpacity(0.4);
	    	x=event.getSceneX();
	    	y=event.getSceneY();
	    }

	    @FXML
	    void releas(MouseEvent event) {
	    	Stage stage = (Stage) ((HBox)event.getSource()).getScene().getWindow();
	    	stage.setOpacity(1);
	    }
		public void add(Actif actif) {
			actifs.add(actif);
		}
		public void setUpdate(Actif actif, int index) {
			actifs.set(index, actif);
		}
		public void delete(int index) {
			actifs.remove(index);
		}
		private void loadData() {
			 Task<Void>task= new Task<Void>() {
				@Override
				protected Void call() throws  Exception  {
						//actifs.setAll(ActifDB.getData());
						ResultSet res = ActifDB.getData();
						while(res.next()) {
							actifs.add(
									new Actif(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getDouble(5), res.getDate(6),
											new Categorie(res.getInt(11), res.getString(12)),
											new Fournisseur(res.getInt(7), res.getString(8), res.getString(9), res.getString(10),null ),
											 res.getBoolean(13))
									);
						}
						tableActif.setItems(actifs);
					return null;
				}
				
			};
			task.setOnFailed(e->System.out.println("failed"));
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		}
}
