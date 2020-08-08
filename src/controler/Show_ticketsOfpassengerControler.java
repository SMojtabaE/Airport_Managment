package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Flight;
import model.Passenger;
import model.Status;
import model.Ticket;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Show_ticketsOfpassengerControler implements Initializable {
    // initialing the table and its columns
    @FXML TableView<Ticket> table;
    @FXML TableColumn<Ticket, Integer> id;
    @FXML TableColumn<Ticket, Integer> flightid;
    @FXML TableColumn<Ticket, Double> price;
    @FXML TableColumn<Ticket, Double> loss;

    @FXML Button cancel;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;
    @FXML ImageView airplanesearchimg;
    @FXML TextField airplaneidsearcgfeild;
    @FXML Label erorlbl;

    Passenger passenger = Dashbord_passengersControler.passenger;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        flightid.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        loss.setCellValueFactory(new PropertyValueFactory<>("loss"));
        try {
            table.setItems(DataBase.getticketsOfpassenger(passenger.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cancel.setOnAction( e -> {
            Ticket selected= table.getSelectionModel().getSelectedItem();
            try {
                if (selected != null) {
                    Flight flight = DataBase.searchForflight(selected.getFlight_id());
                    if (flight.getStatus().equals(Status.open)) {
                            DataBase.deletpassengers_ticket_passenger(selected.getId(), passenger.getId());
                            passenger.setMoney(passenger.getMoney() + selected.getPrice());
                            flight.setSold_tickets(flight.getSold_tickets() + 1);
                            DataBase.updateflight(flight);
                            DataBase.updatpassenger(passenger);
                    } else {
                        erorlbl.setText("this flight is done");
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Ticket searchedticket = null;
                    try {
                        searchedticket = DataBase.searchInticketsid(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedticket != null) {
                        ObservableList<Ticket> selected = FXCollections.observableArrayList();
                        selected.add(searchedticket);
                        table.setItems(selected);
                    }
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        airplanesearchimg.setOnMousePressed( e -> {
            if (!airplaneidsearcgfeild.getText().isEmpty()) {
                try {
                    ObservableList<Ticket> searchidInflights = DataBase.searchflight_idIntickets(Integer.parseInt(
                            airplaneidsearcgfeild.getText()));
                    table.setItems(searchidInflights);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.gettickets());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }
}
