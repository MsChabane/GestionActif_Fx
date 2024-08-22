package com.app.controls;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.app.DBControls.ActifDB;
import com.app.DBControls.AffectationDB;
import com.app.DBControls.EmpruntDB;
import com.app.models.Actif;
import com.app.models.Affectation;
import com.app.models.Emprunt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AlertControl implements Initializable{
	private double x,y;
	  @FXML
	    private Label lblTitre;

	    

	    @FXML
	    void drag(MouseEvent event) {
		  	Stage stage = (Stage) lblTitre.getScene().getWindow();
		  	stage.setX(event.getScreenX()-x);
		  	stage.setY(event.getScreenY()-y);
		  	
	    }

	    @FXML
	    void press(MouseEvent event) {
	    	Stage stage = (Stage) lblTitre.getScene().getWindow();
	    	stage.setOpacity(0.4);
	    	x=event.getSceneX();
	    	y=event.getSceneY();
	    }

	    @FXML
	    void releas(MouseEvent event) {
	    	Stage stage = (Stage) lblTitre.getScene().getWindow();
	    	stage.setOpacity(1);
	    }

	    @FXML
	    void returnToOwner(ActionEvent event) {
	    	Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
	    	stage.getOwner().setOpacity(1);
	    	stage.close();
	    }
	    private Object control,data;
	    private int index;
	    public void setControls(Object control,Object data,String message,int index ) {
	    	this.control=control;
	    	this.data=data;
	    	lblTitre.setText(message);
	    	this.index=index;
	    }
	    @FXML
	    void appliquer(ActionEvent event) {
	    	if(control instanceof ActifControl) {
	    		ActifControl cntrl = (ActifControl) control;
	    		Actif actif =(Actif) data;
	    		try {
					ActifDB.delete(actif);
					cntrl.delete(index);
				} catch (SQLException e) {					
					e.printStackTrace();
				}
	    	}else if(control instanceof AffectationControl ) {
	    		AffectationControl cntrl = (AffectationControl) control;
	    		Affectation affectation = (Affectation) data;
	    		affectation.getActif().setStatus("En Stock");
	    		try {
					ActifDB.changeStatus(affectation.getActif());
					AffectationDB.dropLast(affectation);
					cntrl.delete(index);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
	    	}else if(control instanceof EmpruntControl ) {
	    		EmpruntControl cntrl = (EmpruntControl) control;
	    		Emprunt emprunt = (Emprunt) data;
	    		emprunt.getActif().setStatus("En Stock");
	    		try {
					ActifDB.changeStatus(emprunt.getActif());
					EmpruntDB.delete(emprunt);
					cntrl.delete(index);
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
	    	}
	    	returnToOwner(event);
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		
			
		}  
	    
}
