package com.app.controls;



import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;


import com.app.DBControls.AffectationDB;
import com.app.models.Actif;
import com.app.models.Affectation;

import com.app.models.EmplacementInterne;

import com.app.models.TypeEmplacement;

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

public class AffectationControl implements Initializable {
	
	private   ObservableList<Affectation> affectations ;

  
    @FXML
    private TableColumn<Affectation, String> controlsCol;

    @FXML
    private TableColumn<Affectation, Date> dateCol;

    @FXML
    private TableColumn<Affectation, EmplacementInterne> distCol;

    @FXML
    private TableColumn<Affectation, Actif> dsgCol;

    @FXML
    private TableColumn<Affectation, Actif> actifCol;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<Affectation> tableAff;

    

    @FXML
    private TableColumn<Affectation, EmplacementInterne> typeDistCol;

	private double x,y;
	public void close(ActionEvent event ) {
		((Stage)((Button)event.getSource()).getScene().getWindow()).close();
	}
   
    @FXML
    private CustomTextField txtRecherche;
    
   
    @FXML
    void addNewItem(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/com/app/fxml/AffectationAjout.fxml"));
			loader.load();
			AffectationAjoutControl control =loader.getController();
			control.setControl(AffectationControl.this);
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(tableAff.getScene().getWindow());
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
		affectations=FXCollections.observableArrayList();
		actifCol.setCellValueFactory(new PropertyValueFactory<Affectation, Actif>("actif"));
		//dsgCol.setCellValueFactory(new PropertyValueFactory<Affectation, Actif>("actif"));
		dateCol.setCellValueFactory(new PropertyValueFactory<Affectation, Date>("dateAffec"));
		distCol.setCellValueFactory(new PropertyValueFactory<Affectation, EmplacementInterne>("emplacementInterne"));
		typeDistCol.setCellValueFactory(new PropertyValueFactory<Affectation, EmplacementInterne>("emplacementInterne"));
		
	
		
		Callback<TableColumn<Affectation, String>,TableCell<Affectation, String>>cellfactory= (TableColumn<Affectation, String> col)->{
			
			final TableCell<Affectation, String> cell = new TableCell<Affectation, String>(){
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
							int  index = tableAff.getSelectionModel().getSelectedIndex();
							if(index>=0) {
						    	try {
						    		FXMLLoader loader = new FXMLLoader();
						    		loader.setLocation(getClass().getResource("/com/app/fxml/Alert.fxml"));
									loader.load();
									AlertControl control =loader.getController();
									control.setControls(AffectationControl.this,affectations.get(index) ,"Ètes-vous sûre de supprimer l'Affectation ? ", index);
									Parent root = loader.getRoot();
									Scene scene = new Scene(root);
									Stage stage = new Stage();
									stage.setScene(scene);
									stage.initOwner(tableAff.getScene().getWindow());
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
							int x = tableAff.getSelectionModel().getSelectedIndex();
							if(x>=0) {
								try {
						    		FXMLLoader loader = new FXMLLoader();
						    		loader.setLocation(getClass().getResource("/com/app/fxml/AffectationChange.fxml"));
									loader.load();
									AffectationChangeControl control =loader.getController();
									control.setControl(AffectationControl.this,affectations.get(x),x);
									Parent root = loader.getRoot();
									Scene scene = new Scene(root);
									Stage stage = new Stage();
									stage.setScene(scene);
									stage.initOwner(tableAff.getScene().getWindow());
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
		actifCol.setCellFactory(col->{
			return new TableCell<Affectation, Actif>(){
				protected void updateItem(Actif item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText("( ID = "+item.getId()+") "+item.getDesg());	
						
					}
				}
			};
		});
		
		dateCol.setCellFactory(col->{
			return new TableCell<Affectation, Date>(){
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
		distCol.setCellFactory(col->{
			return new TableCell<Affectation, EmplacementInterne>(){
				protected void updateItem(EmplacementInterne item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getIntitule());
					}
				}
			};
		});
		typeDistCol.setCellFactory(col->{
			return new TableCell<Affectation, EmplacementInterne>(){
				protected void updateItem(EmplacementInterne item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getTypeEmplacement().getIntitule());
					}
				}
			};
		});
		//affectations.addAll(Data.getAffectations());
		
		tableAff.setItems(affectations);
		loadData();
		
		FilteredList<Affectation>filActifs = new FilteredList<Affectation>(affectations, e->true);
		txtRecherche.textProperty().addListener((ob,oldV,newV)->{
			filActifs.setPredicate(affectation->{
				String value = txtRecherche.getText().toLowerCase();
				if(affectation.getActif().getDesg().toLowerCase().contains(value))return true;
				if(affectation.getActif().getId().equals(value))return true;
				if(affectation.getEmplacementInterne().getIntitule().toLowerCase().contains(value))return true;
				if(affectation.getEmplacementInterne().getTypeEmplacement().getIntitule().toLowerCase().contains(value))return true;
				return false;
			});
			SortedList<Affectation>sortActifs = new SortedList<Affectation>(filActifs);
			sortActifs.comparatorProperty().bind(tableAff.comparatorProperty());
			tableAff.setItems(sortActifs);
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
		public void add(Affectation affectation) {
			affectations.add(affectation);
		}
		public void setUpdate(Affectation affectation, int index) {
			affectations.set(index, affectation);
		}
		public void delete( int index) {
			affectations.remove(index);
		}
		private void loadData() {
			 Task<Void>task= new Task<Void>() {
				@Override
				protected Void call() throws  Exception  {
						//actifs.setAll(ActifDB.getData());
						ResultSet res = AffectationDB.getData();
						while(res.next()) {
							affectations.add(new Affectation(new Actif(res.getString(1), res.getString(2)),
									new EmplacementInterne(res.getString(4), res.getString(5),new TypeEmplacement(res.getInt(6),res.getString(7))),
									res.getDate(3), true)												
									);
						}
						tableAff.setItems(affectations);
					return null;
				}	
			};
			task.setOnFailed(e->task.getException().printStackTrace());
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		}
}
