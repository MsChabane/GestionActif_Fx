package com.app.controls;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import com.app.DBControls.ActifDB;
import com.app.DBControls.CategorieDB;
import com.app.DBControls.FournisseurDB;
import com.app.models.Actif;
import com.app.models.Categorie;
import com.app.models.Fournisseur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import javafx.util.Duration;

public class ActifModifierControl implements Initializable{
	
	private Notifications notif;
	private double x,y;
	
	
	  @FXML
	    void drag(MouseEvent event) {
		  	Stage stage = (Stage) txtdsg.getScene().getWindow();
		  	stage.setX(event.getScreenX()-x);
		  	stage.setY(event.getScreenY()-y);
		  	
	    }

	    @FXML
	    void press(MouseEvent event) {
	    	Stage stage = (Stage) txtdsg.getScene().getWindow();
	    	stage.setOpacity(0.4);
	    	x=event.getSceneX();
	    	y=event.getSceneY();
	    }

	    @FXML
	    void releas(MouseEvent event) {
	    	Stage stage = (Stage) txtdsg.getScene().getWindow();
	    	stage.setOpacity(1);
	    }
	    @FXML
	    private ComboBox<Fournisseur> FrnsCombo;

	    @FXML
	    private ComboBox<Categorie> ctgCombo;

	 

	    @FXML
	    private CustomTextField txtValeur;

	    @FXML
	    private TextArea txtdsg;
	    

	    @FXML
	    void edit(ActionEvent event) {
	    	if(isValid()) {
	    		try {
					actif.setDesg(txtdsg.getText());
					actif.setValeur(Double.valueOf(txtValeur.getText()));
					if(ctgCombo.getSelectionModel().getSelectedIndex()>=0) {
						actif.setCategorie(ctgCombo.getValue());
					}
					if(FrnsCombo.getSelectionModel().getSelectedIndex()>=0) {
						actif.setFournisseur(FrnsCombo.getValue());
					}
					ActifDB.modify(actif);
					control.setUpdate(actif,index);
					returnToOwner(event);
				} catch (NumberFormatException | SQLException e) {
					e.printStackTrace();
				} 
	    	}
	    }
	    
	    private ActifControl control;
	    private Actif actif;
	    private int index ;
	    public void setControl(ActifControl control,Actif actif,int index) {
	    	this.control=control;
	    	this.actif=actif;
	    	this.index=index;
	    	txtValeur.setText(String.valueOf(actif.getValeur()));
	    	txtdsg.setText(actif.getDesg());
	    	FrnsCombo.setValue(actif.getFournisseur());
	    	ctgCombo.setValue(actif.getCategorie());
	    }
	    @FXML
	    void returnToOwner(ActionEvent event) {
	    	Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
	    	stage.getOwner().setOpacity(1);
	    	stage.close();
	    }
	    private boolean isValid() {
	    	notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.BOTTOM_RIGHT).owner(((Stage)txtdsg.getScene().getWindow()).getOwner());
	    	
	    	if(!txtValeur.getText().matches("(\\d+)||(\\d+.\\d+)")) {
	    		Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.BOTTOM_RIGHT)
	    		.owner(((Stage)txtdsg.getScene().getWindow()).getOwner())
	    		.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("valeur invalide").onAction(e->txtValeur.requestFocus()).show();
	    		return false;
	    	}
	    	if(txtdsg.getText().isBlank()) {
	    		Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.BOTTOM_RIGHT)
	    		.owner(((Stage)txtdsg.getScene().getWindow()).getOwner())
	    		.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("deseignation invalide").onAction(e->txtdsg.requestFocus()).show();
	    		return false;
	    	}
	    	return true;
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {		
			try {
				ObservableList<Categorie> ctgs;
				ctgs = FXCollections.observableArrayList(CategorieDB.getData());
				ctgCombo.setItems(ctgs);
				ObservableList<Fournisseur> fr;
				fr = FXCollections.observableArrayList(FournisseurDB.getData());
				FrnsCombo.setItems(fr);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			
		}
	    
	    
}
