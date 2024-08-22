package com.app.controls;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;
import com.app.models.Actif;
import com.app.models.EmplacementExterne;
import com.app.models.Emprunt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.util.Duration;
public class RevenueControl implements Initializable {
	

	private double x,y;
	public void close(ActionEvent event ) {
		try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("/com/app/fxml/Emprunt.fxml"));
			loader.load();
			Parent root = loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/com/app/styles/ThemeTwo2.css").toExternalForm());
			Stage stage = (Stage) tableEmprunt.getScene().getWindow();
			stage.setScene(scene);	
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 @FXML
	    private TableColumn<Emprunt, Actif> actifCol;

	    @FXML
	    private TableColumn<Emprunt, String> controlsCol;

	    @FXML
	    private TableColumn<Emprunt, Date> datePretCol;

	    @FXML
	    private TableColumn<Emprunt, Date> dateRemisCol;

	    @FXML
	    private TableColumn<Emprunt, EmplacementExterne> distinCol;

	    @FXML
	    private TableColumn<Emprunt, String> idCol;

	    

	    

	    @FXML
	    private TableView<Emprunt> tableEmprunt;

	    @FXML
	    private CustomTextField txtRecherche;
   
    private ObservableList<Emprunt>emprunts;
    public void setEmprunts(List<Emprunt> emprunts) {
		this.emprunts.setAll(emprunts);
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		emprunts=FXCollections.observableArrayList();
		idCol.setCellValueFactory(new PropertyValueFactory<Emprunt, String>("id"));
		datePretCol.setCellValueFactory(new PropertyValueFactory<Emprunt, Date>("datePret"));
		dateRemisCol.setCellValueFactory(new PropertyValueFactory<Emprunt, Date>("dateRemis"));
		distinCol.setCellValueFactory(new PropertyValueFactory<Emprunt, EmplacementExterne>("emplacementExterne"));
		actifCol.setCellValueFactory(new PropertyValueFactory<Emprunt, Actif>("actif"));
		Callback<TableColumn<Emprunt, String>,TableCell<Emprunt, String>>cellfactory= (TableColumn<Emprunt, String> col)->{
			
			final TableCell<Emprunt, String> cell = new TableCell<Emprunt, String>(){
				@Override
				public void updateItem(String item, boolean emplty) {
					if(emplty) {
						setGraphic(null);
						setText(null);
					}else {
						ImageView editIcon = new ImageView(new Image("/com/app/images/icons8_checkmark_yes_32px_1.png"));
						editIcon.prefWidth(25);
						editIcon.prefHeight(25);
						
						
					
						Button btnUpdate = new Button(null,editIcon);
						btnUpdate.getStyleClass().add("editBtn");
						btnUpdate.setOnAction(e->{
							int x = tableEmprunt.getSelectionModel().getSelectedIndex();
							if(x>=0) {
							try {
					    		FXMLLoader loader = new FXMLLoader();
					    		loader.setLocation(getClass().getResource("/com/app/fxml/Remis.fxml"));
								loader.load();
								RemisControl control =loader.getController();
								control.setControl(RevenueControl.this,emprunts.get(x),x);
								Parent root = loader.getRoot();
								Scene scene = new Scene(root);
								scene.getStylesheets().add(getClass().getResource("/com/app/styles/ThemeTwo2.css").toExternalForm());
								Stage stage = new Stage();
								stage.setScene(scene);
								stage.initOwner(tableEmprunt.getScene().getWindow());
								stage.initModality(Modality.APPLICATION_MODAL);
								stage.initStyle(StageStyle.UNDECORATED);
								stage.getOwner().setOpacity(0.4);
								stage.show();
							} catch (IOException ex) {	
								ex.printStackTrace();							}
							}else {
								Notifications.create().hideAfter(Duration.seconds(3)).position(Pos.TOP_LEFT)
								.owner(((Stage)tableEmprunt.getScene().getWindow()).getOwner())
								.graphic(new ImageView(new Image("/com/app/images/warning.png")))
								.text("selectionner").show();	
							}
						});
						HBox v = new HBox(btnUpdate);
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
		
		
		datePretCol.setCellFactory(col->{
			return new TableCell<Emprunt, Date>(){
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
		dateRemisCol.setCellFactory(col->{
			return new TableCell<Emprunt, Date>(){
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
			return new TableCell<Emprunt, Actif>(){
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
		distinCol.setCellFactory(col->{
			return new TableCell<Emprunt, EmplacementExterne>(){
				protected void updateItem(EmplacementExterne item,boolean isEmpty) {
					if(item ==null || isEmpty ) {
						setText(null);
						setStyle(null);
					}else {
						setText(item.getIntitle());	
						
					}
				}
			};
		});
		
		FilteredList<Emprunt>filActifs = new FilteredList<Emprunt>(emprunts, e->true);
		txtRecherche.textProperty().addListener((ob,oldV,newV)->{
			filActifs.setPredicate(actif->{
				//String value = txtRecherche.getText().toLowerCase();
				
				return false;
			});
			SortedList<Emprunt>sortActifs = new SortedList<Emprunt>(filActifs);
			sortActifs.comparatorProperty().bind(tableEmprunt.comparatorProperty());
			tableEmprunt.setItems(sortActifs);
		});
		tableEmprunt.setItems(emprunts);
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
		public void add(Emprunt emprunt) {
			emprunts.add(emprunt);
		}
		public void delete(int index) {
			emprunts.remove(index);
		}
}
