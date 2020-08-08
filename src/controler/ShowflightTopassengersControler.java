package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Flight;
import model.Status;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ShowflightTopassengersControler implements Initializable {
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

    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        origin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        longs.setCellValueFactory(new PropertyValueFactory<>("longs"));
        soldticket.setCellValueFactory(new PropertyValueFactory<>("sold_tickets"));
        try {
            table.setItems(DataBase.getflights());
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getflights());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
    }
}
