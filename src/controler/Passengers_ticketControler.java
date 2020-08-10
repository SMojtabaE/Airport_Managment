package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import model.*;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Passengers_ticketControler implements Initializable {

    @FXML TableView<Passengers_ticket> table;
    @FXML TableColumn<Passengers_ticket , Integer> ticketid;
    @FXML TableColumn<Passengers_ticket , Integer> passengersid;

    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML ImageView passimg;
    @FXML TextField passidfield;
    @FXML TextField ticketidfield;
    @FXML Button cancelbtn;
    @FXML Label erorlbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ticketid.setCellValueFactory(new PropertyValueFactory<>("ticket_id"));
        passengersid.setCellValueFactory(new PropertyValueFactory<>("passenger_id"));

        try {
            table.setItems(DataBase.getPassengers_ticket());
        } catch (SQLException e) {
            e.printStackTrace();
        }



        searchimg.setOnMousePressed( e -> {
            if (!ticketidfield.getText().isEmpty()) {
                try {
                    ObservableList<Passengers_ticket> searchedairplane = null;
                    try {
                        searchedairplane = DataBase.getPassengers_ticketByticketid(Integer.parseInt(ticketidfield.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                        table.setItems(searchedairplane);
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        passimg.setOnMousePressed( e -> {
            if (!passidfield.getText().isEmpty()) {
                try {
                    ObservableList<Passengers_ticket> searchedairplane = null;
                    try {
                        searchedairplane = DataBase.getPassengers_ticketBypassengerid(Integer.parseInt(passidfield.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    table.setItems(searchedairplane);
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });

        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getPassengers_ticket());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cancelbtn.setOnAction( e -> {
            Passengers_ticket selected= table.getSelectionModel().getSelectedItem();
            try {
                if (selected != null) {
                    Ticket ticket = DataBase.searchInticketsid(selected.getTicket_id());
                    Flight flight = DataBase.searchForflight(ticket.getFlight_id());
                    if (flight.getStatus().equals(Status.open)) {
                        Passenger passenger = DataBase.searchFrompassenger(selected.getPassenger_id());
                        DataBase.deletpassengers_ticket_passenger(ticket.getId(), passenger.getId());
                        passenger.setMoney(passenger.getMoney() +  ( ticket.getPrice() - ((ticket.getLoss() * ticket.getPrice()) / 100)));
                        flight.setSold_tickets(flight.getSold_tickets() + 1);
                        DataBase.updateflight(flight);
                        DataBase.updatpassengersmony(passenger);
                    } else {
                        erorlbl.setText("this flight is done");
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
