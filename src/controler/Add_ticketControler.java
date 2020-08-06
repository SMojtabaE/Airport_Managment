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
import model.Ticket;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Add_ticketControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextField price;
    @FXML TextField loss;
    @FXML TextField flightid;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;
    private TableView table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable();

        cancelbtn.setOnAction( e -> {
            Flights_tableControler.registerstage = null;
            Flights_tableControler.editstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });

        savebtn.setOnAction( e -> {
            if (price.getText().isEmpty() || loss.getText().isEmpty() || flightid.getText().isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    if (DataBase.isvalidflightid(Integer.parseInt(flightid.getText()))) {
                        Ticket ticket = new Ticket(Double.parseDouble(price.getText()),Double.parseDouble(loss.getText())
                        ,Integer.parseInt(flightid.getText()));
                        ticket.setId(DataBase.createticket(ticket));
                        Ticket_tableControler.registerstage = null;
                        table.getItems().add(ticket);
                        ((Stage) savebtn.getScene().getWindow()).close();
                    }else {
                        erorlbl.setText("Enter a Valid Flight ID");
                        Toolkit.getDefaultToolkit().beep();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }catch (NumberFormatException ex){
                    erorlbl.setText("Enter Number in Flight_ID Field");
                    Toolkit.getDefaultToolkit().beep();
                }
            }

        });

    }

    public void settable(TableView table){ this.table = table; }
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
