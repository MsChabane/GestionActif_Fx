package com.app.controls;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;

import com.app.DBControls.ActifDB;
import com.app.DBControls.EmpruntDB;
import com.app.models.Actif;
import com.app.models.Emprunt;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RemisControl implements Initializable{

	private Notifications notif;
	private double x,y;
	@FXML
	void drag(MouseEvent event) {
		Stage stage = (Stage) dateRemis.getScene().getWindow();
		stage.setX(event.getScreenX()-x);
		stage.setY(event.getScreenY()-y);
	}
	@FXML
	void press(MouseEvent event) {
		Stage stage = (Stage) dateRemis.getScene().getWindow();
		stage.setOpacity(0.4);
		x=event.getSceneX();
		y=event.getSceneY();
	}
	@FXML
	void releas(MouseEvent event) {
		Stage stage = (Stage) dateRemis.getScene().getWindow();
		stage.setOpacity(1);
	}
	@FXML
    private ComboBox<String> comboEtat;
	 @FXML
	    private DatePicker dateRemis;
	@FXML
	void remis(ActionEvent event) throws ParseException {
		if(isValid()) {
			emprunt.setDateRemis(new SimpleDateFormat("yyyy-MM-dd").parse(dateRemis.getValue().toString()));
			emprunt.getActif().setEvaluation(comboEtat.getValue());
			
			emprunt.setEstRetourner(true);
			try {
				EmpruntDB.remis(emprunt);
				ActifDB.changeEvaluation(emprunt.getActif());
				emprunt.getActif().setStatus("En Stock");
				ActifDB.changeStatus(emprunt.getActif());
				control.delete(index);
				returnToOwner(event);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	private RevenueControl control;
	private Emprunt emprunt;
	private int index;
	public void setControl(RevenueControl control,Emprunt emprunt,int index) {
		this.control=control;
		this.emprunt=emprunt;
		this.index= index;
		this.dateRemis.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format((emprunt.getDateRemis()))));
		
	}
	@FXML
	void returnToOwner(ActionEvent event) {
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		stage.getOwner().setOpacity(1);
		stage.close();
	}
	
	private boolean isValid() {
		notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT).owner(((Stage)dateRemis.getScene().getWindow()).getOwner());	    		
		if(comboEtat.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectionner l'etat de l'actif").onAction(e->comboEtat.requestFocus()).show();
			return false;
		}
		if(dateRemis.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date affectation").onAction(e->dateRemis.requestFocus()).show();
			return false;
		}
		
		return true; 
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		comboEtat.setItems(FXCollections.observableArrayList("Bon","Mauvais"));
		dateRemis.setOnAction(e->{
			if( dateRemis.getValue().isBefore(LocalDate.parse(new java.sql.Date(emprunt.getDatePret().getTime()).toString()))) {
				dateRemis.setValue(LocalDate.parse(new java.sql.Date(emprunt.getDatePret() .getTime()).toString()));
			}
		});
		
		
	}
	


}
