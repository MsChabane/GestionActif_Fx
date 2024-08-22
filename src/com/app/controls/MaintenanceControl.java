package com.app.controls;



import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


import org.controlsfx.control.textfield.CustomTextField;



import com.app.models.Actif;



import com.app.models.Maintenance;
import com.app.models.Types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

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

import javafx.stage.Stage;

import javafx.util.Callback;


public class MaintenanceControl implements Initializable {
	

	private double x,y;
	public void close(ActionEvent event ) {
		//StylesControl.saveIndex();
		((Stage)((Button)event.getSource()).getScene().getWindow()).close();
	}
	 @FXML
	    private TableColumn<Maintenance, Actif> actifCol;

	    @FXML
	    private TableColumn<Maintenance, String> controlsCol;

	    @FXML
	    private TableColumn<Maintenance, Date> dateDebuCol;

	    @FXML
	    private TableColumn<Maintenance, Date> dateTermineCol;

	    @FXML
	    private TableColumn<Maintenance, Types> typeCol;

	    @FXML
	    private TableColumn<Maintenance, String> idCol;

	    @FXML
	    private Label lblTitle;

	    @FXML
	    private TableColumn<Maintenance, String> statusCol;

	    @FXML
	    private TableView<Maintenance> tableMaintenance;

	    @FXML
	    private CustomTextField txtRecherche;
    @FXML
    void addNewItem(ActionEvent event) {
    	
    }
    @FXML
    void showDeleteItem(ActionEvent event) {

    }
    @FXML
    void showTermine(ActionEvent event) {
    	
    }
    private ObservableList<Maintenance>maintenances;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maintenances=FXCollections.observableArrayList();
		idCol.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("id"));
		dateDebuCol.setCellValueFactory(new PropertyValueFactory<Maintenance, Date>("dateDeb"));
		dateTermineCol.setCellValueFactory(new PropertyValueFactory<Maintenance, Date>("dateFin"));
		typeCol.setCellValueFactory(new PropertyValueFactory<Maintenance, Types>("typeMaintenance"));
		actifCol.setCellValueFactory(new PropertyValueFactory<Maintenance, Actif>("actif"));
		statusCol.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("status"));
		Callback<TableColumn<Maintenance, String>,TableCell<Maintenance, String>>cellfactory= (TableColumn<Maintenance, String> col)->{
			
			final TableCell<Maintenance, String> cell = new TableCell<Maintenance, String>(){
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
							
						});
						Button btnUpdate = new Button(null,editIcon);
						btnUpdate.getStyleClass().add("editBtn");
						
							
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
			return new TableCell<Maintenance, String>(){
				protected void updateItem(String item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						if(!item.equalsIgnoreCase("En cours")) {
							setStyle("-fx-text-fill:red");
						}
						else {
							setStyle("-fx-text-fill:green");
						}
					}
				}
			};
		});
		
		dateDebuCol.setCellFactory(col->{
			return new TableCell<Maintenance, Date>(){
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
		dateTermineCol.setCellFactory(col->{
			return new TableCell<Maintenance, Date>(){
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
		actifCol.setCellFactory(col->{
			return new TableCell<Maintenance, Actif>(){
				protected void updateItem(Actif item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getId()+" "+item.getDesg());
					}
				}
			};
		});
		typeCol.setCellFactory(col->{
			return new TableCell<Maintenance, Types>(){
				protected void updateItem(Types item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getIntitule());	
						
					}
				}
			};
		});
		
		FilteredList<Maintenance>filActifs = new FilteredList<Maintenance>(maintenances, e->true);
		txtRecherche.textProperty().addListener((ob,oldV,newV)->{
			filActifs.setPredicate(actif->{
				//String value = txtRecherche.getText().toLowerCase();
				
				return false;
			});
			SortedList<Maintenance>sortActifs = new SortedList<Maintenance>(filActifs);
			sortActifs.comparatorProperty().bind(tableMaintenance.comparatorProperty());
			tableMaintenance.setItems(sortActifs);
		});
		tableMaintenance.setItems(maintenances);
		//loadData();
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
		public void add(Maintenance Maintenance) {
			maintenances.add(Maintenance);
		}
		public void setUpdate(Maintenance Maintenance, int index) {
			maintenances.set(index, Maintenance);
		}
		/*id_em ,datePret_em ,dateRemi_em,estRetourner_em,actif,desg_ac,dateintr_ac,id_empEx,intitule_empEx*/
	/*	private void loadData() {
			 Task<Void>task= new Task<Void>() {
				@Override
				protected Void call() throws  Exception  {
						//actifs.setAll(ActifDB.getData());
						ResultSet res = MaintenanceDB.getData();
						while(res.next()) {
							Maintenances.add(new Maintenance(res.getString(1), res.getDate(2),res.getDate(3), res.getBoolean(4), 
									new Actif(res.getString(5), res.getString(6), res.getDate(7)),
									new EmplacementExterne(res.getInt(8), res.getString(9), res.getString(10)))												
									);
						}
						tableMaintenance.setItems(Maintenances);
					return null;
				}	
			};
			task.setOnFailed(e->task.getException().printStackTrace());
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		}*/
		public void delete(int index) {
			maintenances.remove(index);
			
		}
}
