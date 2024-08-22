package com.app.controls;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import com.app.DBControls.ActifDB;
import com.app.models.Actif;
import com.app.models.Categorie;
import com.app.models.Fournisseur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ActifAjoutControl implements Initializable{
	
	private Notifications notif;
	private double x,y;
	
	@FXML
    private CheckBox consomCheck;
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
	    private DatePicker dateInv;

	    @FXML
	    private CustomTextField txtValeur;

	    @FXML
	    private TextArea txtdsg;
	    

	    @FXML
	    void addNew(ActionEvent event) {
	    	if(isValid()) {
	    		try {
					Actif  actif = new Actif(null, txtdsg.getText(),"Bon","En Stock",Double.valueOf(txtValeur.getText()), 
							new SimpleDateFormat("yyyy-MM-dd").parse(dateInv.getValue().toString()), ctgCombo.getValue(), FrnsCombo.getValue(),consomCheck.isSelected());
					ActifDB.add(actif);
					control.add(actif);
					notif.graphic(new ImageView(new Image("/com/app/images/valid.png"))).text("ajouter avec succes").show();
				} catch (NumberFormatException|SQLException |ParseException e) {		
					e.printStackTrace();
				} 
	    	}
	    }
	    
	    private ActifControl control;
	    public void setControl(ActifControl control) {
	    	this.control=control;
	    }
	    @FXML
	    void returnToOwner(ActionEvent event) {
	    	Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
	    	stage.getOwner().setOpacity(1);
	    	stage.close();
	    }
	    private boolean isValid() {
	    	notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT).owner(((Stage)txtdsg.getScene().getWindow()).getOwner());
	    	if(FrnsCombo.getSelectionModel().getSelectedIndex()<0) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner fournisseur").onAction(e->FrnsCombo.requestFocus()).show();
	    		return false;
	    	}
	    	if(ctgCombo.getSelectionModel().getSelectedIndex()<0) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner categorie").onAction(e->ctgCombo.requestFocus()).show();
	    		return false;
	    	}
	    	if(dateInv.getValue()==null) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date inventaire").onAction(e->dateInv.requestFocus()).show();
	    		return false;
	    	}
	    	if(txtValeur.getText().isEmpty() || !txtValeur.getText().matches("(\\d+)||(\\d+.\\d+)")) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("valeur invalide").onAction(e->txtValeur.requestFocus()).show();
	    		return false;
	    	}
	    	if(txtdsg.getText().isBlank()) {
	    		notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("deseignation invalide").onAction(e->txtdsg.requestFocus()).show();
	    		return false;
	    	}
	    	return true;
	    }
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			//ObservableList<Categorie>ctgs = FXCollections.observableArrayList(Data.getCats());
			
			//ctgCombo.setItems(ctgs);
			///ObservableList<Fournisseur>fr = FXCollections.observableArrayList(Data.getFournisseur());
			
			//FrnsCombo.setItems(fr);
			
		}
	    
	    
}
