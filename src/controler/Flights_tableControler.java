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
import model.Flight;
import model.Status;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Flights_tableControler implements Initializable {
    // initialing the table and its columns
    @FXML TableView<Flight> table;
    @FXML TableColumn<Flight, String> origin;
    @FXML TableColumn<Flight, String> destination;
    @FXML TableColumn<Flight, LocalDate> date;
    @FXML TableColumn<Flight, LocalTime> time;
    @FXML TableColumn<Flight, Integer> soldticket;
    @FXML TableColumn<Flight, String> longs;
    @FXML TableColumn<Flight, Status> status;
    @FXML TableColumn<Flight, Integer> id;
    @FXML TableColumn<Flight, Integer> airplaneid;

    @FXML Button addbtn;
    @FXML Button removebtn;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;
    @FXML ImageView airplanesearchimg;
    @FXML TextField airplanesearcgfeild;

    static Stage registerstage = null;
    static Stage editstage = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        origin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        airplaneid.setCellValueFactory(new PropertyValueFactory<>("airplaine_id"));
        longs.setCellValueFactory(new PropertyValueFactory<>("longs"));
        soldticket.setCellValueFactory(new PropertyValueFactory<>("sold_tickets"));
        try {
            table.setItems(DataBase.getflights());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addbtn.setOnAction( e -> {
            if (registerstage==null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Add_flight.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Add_flightControler controler = loader.getController();
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
            Flight selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deleteflight(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Flight searchedflight = null;
                    try {
                        searchedflight = DataBase.searchForflight(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedflight != null) {
                        ObservableList<Flight> selected = FXCollections.observableArrayList();
                        selected.add(searchedflight);
                        table.setItems(selected);
                    }
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });

        airplanesearchimg.setOnMousePressed( e -> {
            if (!airplanesearcgfeild.getText().isEmpty()) {
                try {
                    try {
                        ObservableList<Flight> searchidInflights = DataBase.searchairplane_idIntickets(Integer.parseInt(airplanesearcgfeild.getText()));
                        table.setItems(searchidInflights);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getflights());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

    }

}
