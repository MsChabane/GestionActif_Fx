package com.app.controls;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;

import com.app.models.Operateur;
import com.app.models.Operation;
import com.app.models.Panne;
import com.app.models.Types;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MaintenanceAjoutControl implements Initializable{

	private Notifications notif;
	private double x,y;
	
	@FXML
	void drag(MouseEvent event) {
		Stage stage = (Stage) comboTyMn.getScene().getWindow();
		stage.setX(event.getScreenX()-x);
		stage.setY(event.getScreenY()-y);	
	}

	@FXML
	void press(MouseEvent event) {
		Stage stage = (Stage) comboTyMn.getScene().getWindow();
		stage.setOpacity(0.4);
		x=event.getSceneX();
		y=event.getSceneY();
	}

	@FXML
	void releas(MouseEvent event) {
		Stage stage = (Stage) comboTyMn.getScene().getWindow();
		stage.setOpacity(1);
	}

	 @FXML
	    private TableColumn<Operation, Operateur> Operateurcol;

	    @FXML
	    private TableColumn<Operation, Types> TypeCol;

	    @FXML
	    private ComboBox<Types> comboTyMn;

	    @FXML
	    private TableColumn<Operation, String> controlsCol;

	    @FXML
	    private TableColumn<Operation, Date> dateCol;

	    @FXML
	    private DatePicker dateDeb;

	    @FXML
	    private DatePicker dateTermine;

	    @FXML
	    private TableColumn<Operation, String> idCol;

	    @FXML
	    private TableColumn<Operation, String> initCol;

	    @FXML
	    private TableColumn<Operation, Panne> panneCol;

	    @FXML
	    private TableView<Operation> tableOperation;

	    @FXML
	    private CustomTextField txtActif;

	    @FXML
	    private CustomTextField txtIntiMn;

	    @FXML
	    void ajouterMn(ActionEvent event) {

	    }
	    @FXML
	    void ajouterOperation(ActionEvent event) {

	    }

	private MaintenanceControl control;
	public void setControl(MaintenanceControl control) {
		this.control=control;
	}
	@FXML
	void returnToOwner(ActionEvent event) {
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		stage.getOwner().setOpacity(1);
		stage.close();
	}
	private boolean isValid() {
		notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT).owner(((Stage)tableOperation.getScene().getWindow()).getOwner());
	/*	if(table.getSelectionModel().getSelectedIndex()<0 || index >actifs.size()-1) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner l'actif").onAction(e->table.requestFocus()).show();
			return false;
		}
		if(empExCombo.getSelectionModel().getSelectedIndex()<0) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("selectioner l'emplacement").onAction(e->empExCombo.requestFocus()).show();
			return false;
		}
		if(datePret.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date Pret").onAction(e->datePret.requestFocus()).show();
			return false;
		}  	
		if(dateRemis.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date Remis").onAction(e->dateRemis.requestFocus()).show();
			return false;
		}  	*/
		return true;
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	}
	

}
