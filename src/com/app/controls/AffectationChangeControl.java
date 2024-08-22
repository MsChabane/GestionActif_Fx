package com.app.controls;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;


import com.app.DBControls.AffectationDB;
import com.app.DBControls.EmplacementInterneDB;
import com.app.DBControls.TypeEmplacementDB;

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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AffectationChangeControl implements Initializable{

	private Notifications notif;
	private double x,y;
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
	void change(ActionEvent event) throws ParseException {
		if(isValid()) {
			if(empCombo.getSelectionModel().getSelectedIndex()>=0)
				affectation.setEmplacementInterne(empCombo.getValue());
			affectation.setDateAffec(new SimpleDateFormat("yyyy-MM-dd").parse(dateAff.getValue().toString()));
			try {

				Boolean bool =AffectationDB.isExist(affectation);
				if(bool==null) {
					AffectationDB.dropLast(affectation);
					AffectationDB.add(affectation);
					notif.graphic(new ImageView(new Image("/com/app/images/valid.png"))).text("ajouter avec succes").show();
				}else if (!bool.booleanValue()){
					AffectationDB.changeStatus(affectation);
					notif.graphic(new ImageView(new Image("/com/app/images/valid.png"))).text("ajouter avec succes").show();
				}else {
					notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("affectation exist").show();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			control.setUpdate(affectation,index);
			returnToOwner(event);
		}
	}




	private AffectationControl control;
	private Affectation affectation;
	private int index;
	public void setControl(AffectationControl control,Affectation affectation,int index) {
		this.control=control;
		this.affectation=affectation;
		this.index= index;
		this.dateAff.setValue(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(affectation.getDateAffec())));
		this.empCombo.setValue(affectation.getEmplacementInterne());
	}
	@FXML
	void returnToOwner(ActionEvent event) {
		Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
		stage.getOwner().setOpacity(1);
		stage.close();
	}
	private boolean exist;
	private boolean isValid() {
		notif =  Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT).owner(((Stage)typeCombo.getScene().getWindow()).getOwner());	    		
		if(dateAff.getValue()==null) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("entrer la date affectation").onAction(e->dateAff.requestFocus()).show();
			return false;
		}
		if(exist) {
			notif.graphic(new ImageView(new Image("/com/app/images/warning.png"))).text("l'affectation dija exist").onAction(e->dateAff.requestFocus()).show();
			return false;
		}
		return true; 
	}
	ObservableList<EmplacementInterne>emps;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		emps = FXCollections.observableArrayList();
		empCombo.setItems(emps);	
		dateAff.setOnAction(e->{
			if( dateAff.getValue().isBefore(LocalDate.parse(new java.sql.Date(affectation.getDateAffec().getTime()).toString()))) {
				dateAff.setValue(LocalDate.parse(new java.sql.Date(affectation.getDateAffec() .getTime()).toString()));
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
				ResultSet res;
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
