package controler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Passenger;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PassebgertableControler implements Initializable {
    // initialing the table and its columns
    @FXML  TableView<Passenger> table;
    @FXML TableColumn<Passenger, String> name;
    @FXML TableColumn<Passenger, String> lastname;
    @FXML TableColumn<Passenger, String> username;
    @FXML TableColumn<Passenger, String> email;
    @FXML TableColumn<Passenger, String> phnumber;
    @FXML TableColumn<Passenger, Double> mony;
    @FXML TableColumn<Passenger, Integer> id;

    @FXML Button addbtn;
    @FXML Button removebtn;
    @FXML Button editbtn;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;
    static Stage registerstage = null;
    static Stage editstage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        mony.setCellValueFactory(new PropertyValueFactory<>("money"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phnumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        try {
             table.setItems(DataBase.getpassengers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addbtn.setOnAction( e -> {
            if (registerstage==null){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PassengerRegister.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    PassengerRegisterControler controler = loader.getController();
                    controler.settable(table);
                    registerstage = new Stage();
                    registerstage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(loader.getRoot());
                    scene.setFill(Color.TRANSPARENT);
                    registerstage.setScene(scene);
                    registerstage.initStyle(StageStyle.TRANSPARENT);
                    editstage = registerstage;
                    registerstage.show();
            }
        });

        removebtn.setOnAction( e -> {
           Passenger selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deletpassenger(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Passenger searchedPassenger = null;
                    try {
                        searchedPassenger = DataBase.searchFrompassenger(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedPassenger != null) {
                        ObservableList<Passenger> selected = FXCollections.observableArrayList();
                        selected.add(searchedPassenger);
                        table.setItems(selected);
                    }
                }catch (NumberFormatException ep){
                    System.out.println("NumberFormatException exception,pleas enter an integer valuo");
                }
            }
        });
        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getpassengers());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        editbtn.setOnAction( e -> {
            if (editstage == null){
                Passenger passenger = table.getSelectionModel().getSelectedItem();
                if (passenger != null) {
                    System.out.println(passenger.getId());
                    FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/PassengersProfile.fxml"));
                    try {
                        loader_.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    PassengersProfileControler controler1 = loader_.getController();
                    controler1.setdata(table, passenger,null);
                    editstage = new Stage();
                    editstage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(loader_.getRoot());
                    scene.setFill(Color.TRANSPARENT);
                    editstage.setScene(scene);
                    editstage.initStyle(StageStyle.TRANSPARENT);
                    registerstage = editstage;
                    editstage.show();
                }
            }
        });
    }

}
