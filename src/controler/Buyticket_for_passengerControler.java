package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Flight;
import model.Passenger;
import model.Status;
import model.Ticket;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Buyticket_for_passengerControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextField passengerid;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;
    private Ticket ticket;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable();
        cancelbtn.setOnAction( e -> {
            Ticket_tableControler.registerstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });
        savebtn.setOnAction( e -> {
            if (passengerid.getText().isEmpty()){
                erorlbl.setText("Enter passenger Id");
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    Passenger passenger = DataBase.searchFrompassenger(Integer.parseInt(passengerid.getText()));
                    if (passenger != null) {
                        Flight flight;
                            flight = DataBase.searchForflight(ticket.getFlight_id());
                        if (flight.getStatus().equals(Status.open)) {
                            if (flight.getSold_tickets() > 0) {
                                if (DataBase.passengercanbuy(ticket.getId(), passenger.getId())) {
                                    if (DataBase.passengercanbuychektime(flight, passenger.getId())) {
                                        if ((passenger.getMoney() - ticket.getPrice()) > 0) {
                                            DataBase.createpassengers_ticket(ticket.getId(), passenger.getId());
                                            passenger.setMoney(passenger.getMoney() - ticket.getPrice());
                                            DataBase.updatpassengersmony(passenger);
                                            flight.setSold_tickets(flight.getSold_tickets() - 1);
                                            DataBase.updateflight(flight);
                                            Ticket_tableControler.registerstage = null;
                                            ((Stage) savebtn.getScene().getWindow()).close();
                                        } else {
                                            erorlbl.setText("Passenger is low on money,charg it");
                                            Toolkit.getDefaultToolkit().beep();
                                        }
                                    }else {
                                        erorlbl.setText("Passenger have flight in this time!!!");
                                        Toolkit.getDefaultToolkit().beep();
                                    }
                                } else {
                                    erorlbl.setText("Already have one");
                                    Toolkit.getDefaultToolkit().beep();
                                }
                            }else {
                                erorlbl.setText("thers no seat!!");
                                Toolkit.getDefaultToolkit().beep();
                            }
                        }else {
                        erorlbl.setText("you cant buy it,its late");
                        Toolkit.getDefaultToolkit().beep();
                    }
                    }else {
                        erorlbl.setText("Passenger ID is Invalid");
                        Toolkit.getDefaultToolkit().beep();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }catch (NumberFormatException ex){
                    erorlbl.setText("Enter Number in airplane_ID Field");
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

    }
    public void settable(Ticket ticket){ this.ticket = ticket; }
    private void makeDraggable(){
        root.setOnMousePressed( ( (event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));
        root.setOnMouseDragged( ( (event) -> {
            Stage stage =  (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }));
    }
}
