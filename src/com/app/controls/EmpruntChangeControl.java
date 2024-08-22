package com.app.controls;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;

import com.app.DBControls.EmpruntDB;
import com.app.models.Emprunt;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EmpruntChangeControl implements Initializable{

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
	    private DatePicker dateRemis;
	@FXML
	void change(ActionEvent event) throws ParseException {
		if(isValid()) {
			emprunt.setDateRemis(new SimpleDateFormat("yyyy-MM-dd").parse(dateRemis.getValue().toString()));
			try {
				EmpruntDB.modify(emprunt);
				control.setUpdate(emprunt,index);
				returnToOwner(event);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private EmpruntControl control;
	private Emprunt emprunt;
	private int index;
	public void setControl(EmpruntControl control,Emprunt emprunt,int index) {
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
		if(dateRemis.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date affectation").onAction(e->dateRemis.requestFocus()).show();
			return false;
		}
		
		return true; 
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			
		dateRemis.setOnAction(e->{
			if( dateRemis.getValue().isBefore(LocalDate.parse(new java.sql.Date(emprunt.getDatePret().getTime()).toString()))) {
				dateRemis.setValue(LocalDate.parse(new java.sql.Date(emprunt.getDatePret() .getTime()).toString()));
			}
		});
		
		
	}
	


}
