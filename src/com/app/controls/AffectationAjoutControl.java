package com.app.controls;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import com.app.DBControls.ActifDB;
import com.app.DBControls.AffectationDB;
import com.app.DBControls.EmplacementInterneDB;
import com.app.DBControls.TypeEmplacementDB;
import com.app.models.Actif;
import com.app.models.Affectation;
import com.app.models.EmplacementInterne;
import com.app.models.TypeEmplacement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AffectationAjoutControl implements Initializable{
	
	private Notifications notif;
	private double x,y;
	 @FXML
	    private TableColumn<Actif, Date> dateCol;
	    @FXML
	    private TableColumn<Actif, String> dsgCol;
	    @FXML
	    private TableColumn<Actif, String> idCol;
	    @FXML
	    private TableView<Actif> table;
	  @FXML
	    void drag(MouseEvent event) {
		  	Stage stage = (Stage) typeCombo.getScene().getWindow();
		  	stage.setX(event.getScreenX()-x);
		  	stage.setY(event.getScreenY()-y);	
	    }

	    @FXML
	    void press(MouseEvent event) {
	    	Stage stage = (Stage) typeCombo.getScene().getWindow();
	    	stage.setOpacity(0.4);
	    	x=event.getSceneX();
	    	y=event.getSceneY();
	    }

	    @FXML
	    void releas(MouseEvent event) {
	    	Stage stage = (Stage) typeCombo.getScene().getWindow();
	    	stage.setOpacity(1);
	    }
	   

	    @FXML
	    private DatePicker dateAff;

	    @FXML
	    private ComboBox<EmplacementInterne> empCombo;

	    @FXML
	    private ComboBox<TypeEmplacement> typeCombo;

	    @FXML
	    void affecter(ActionEvent event) throws ParseException, SQLException {
	    	if(isValid()) {
		    	Affectation affectation = new Affectation(actifs.get(index), empCombo.getValue(), 
		    			new SimpleDateFormat("yyyy-MM-dd").parse(dateAff.getValue().toString())
		    			, true);
		    	AffectationDB.add(affectation);
		    	actifs.get(index).setStatus("En Service");
		    	ActifDB.changeStatus(actifs.get(index));
		    	control.add(affectation);
		    	actifs.remove(index);
		    	notif.graphic(new ImageView(new Image("/com/app/images/valid.png"))).text("affecter avec succes").show();
	    	}
	    }
	    

	   
	   
	    private AffectationControl control;
	    public void setControl(AffectationControl control) {
	    	this.control=control;
	    }
	    @FXML
	    void returnToOwner(ActionEvent event) {
	    	Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
	    	stage.getOwner().setOpacity(1);
	    	stage.close();
	    }
	    private boolean isValid() {
	    	notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT).owner(((Stage)table.getScene().getWindow()).getOwner());
	    	if(table.getSelectionModel().getSelectedIndex()<0 || index >actifs.size()-1) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner l'actif").onAction(e->table.requestFocus()).show();
	    		return false;
	    	}
	    	if(empCombo.getSelectionModel().getSelectedIndex()<0) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner l'emplacement").onAction(e->empCombo.requestFocus()).show();
	    		return false;
	    	}
	    	if(dateAff.getValue()==null) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date affectation").onAction(e->dateAff.requestFocus()).show();
	    		return false;
	    	}  	
	    	return true;
	    }
	    private int index;
	    private Date maxDate=null;
	    ObservableList<Actif>actifs;
	    ObservableList<EmplacementInterne>emps;
	 	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	 		actifs=FXCollections.observableArrayList(); 
			idCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("id"));
			dateCol.setCellValueFactory(new PropertyValueFactory<Actif, Date>("dateInvtr"));
			dsgCol.setCellValueFactory(new PropertyValueFactory<Actif, String>("desg"));
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
			
			table.setOnMouseClicked(e->{
				index=table.getSelectionModel().getSelectedIndex();
				if (index>=0) {
					try {
						maxDate =AffectationDB.getMaxDate(actifs.get(index));
						dateAff.setValue(LocalDate.parse(maxDate.toString()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			emps = FXCollections.observableArrayList();	
			dateAff.setOnAction(e->{
				if(index <0) {
					Notifications
					.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT)
					.owner(((Stage)table.getScene().getWindow()).getOwner())
					.graphic(new ImageView(new Image("/com/app/images/warning.png")))
					.text("selectionner actif  ").onAction(ex->table.requestFocus()).show();
				}
				else if( dateAff.getValue().isBefore(LocalDate.parse(maxDate.toString()))) {
					dateAff.setValue(LocalDate.parse(maxDate.toString()));
				}
			});
			FilteredList<EmplacementInterne>filter = new FilteredList<EmplacementInterne>(emps,e->true);
			typeCombo.setOnAction(e->{
				filter.setPredicate(d->{
					if(d.getTypeEmplacement().getIntitule().equals(typeCombo.getValue().getIntitule()))return true;
					return false;	
				});
				empCombo.setItems(filter);
			});
			loadData();
		}
	 	private void loadData() {
				 Task<Void>task= new Task<Void>() {
					@Override
					protected Void call() throws  Exception  {
							ResultSet res = ActifDB.getDataByStatus("En Stock");
							while(res.next()) {
								actifs.add(new Actif(res.getString(1), res.getString(2), res.getDate(3)));
							}
							table.setItems(actifs);
							res =TypeEmplacementDB.getData();
							ArrayList<TypeEmplacement>types= new ArrayList<TypeEmplacement>();
							while(res.next()) {
								types.add(new TypeEmplacement(res.getInt(1), res.getString(2)));
							}
							typeCombo.setItems(FXCollections.observableArrayList(types));
							emps.setAll(EmplacementInterneDB.getData());
							empCombo.setItems(emps);
						return null;
					}
					
				};
				task.setOnFailed(e->task.getException().printStackTrace());
				Thread th = new Thread(task);
				th.setDaemon(true);
				th.start();
			}  
	    
}
