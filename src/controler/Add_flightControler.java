package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Flight;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Add_flightControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextField time;
    @FXML TextField origin;
    @FXML TextField destination;
    @FXML TextField longs;
    @FXML TextField airplaneid;
    @FXML DatePicker date;
    @FXML AnchorPane root;
    @FXML Label erorlbl;

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
            String datek = String.valueOf(date.getValue());
            if ( time.getText().isEmpty() || origin.getText().isEmpty() || destination.getText().isEmpty() ||
                    longs.getText().isEmpty() || airplaneid.getText().isEmpty() || datek.isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    if (DataBase.airplaine_ID_Isvali(Integer.parseInt(airplaneid.getText()))) {
                        Flight flight = new Flight(Integer.parseInt(airplaneid.getText()), origin.getText(),
                                destination.getText(), date.getValue(), time.getText(), longs.getText());
                        flight.setId(DataBase.createflight(flight));
                        Flights_tableControler.registerstage = null;
                        Flights_tableControler.editstage = null;
                        if (table != null) {
                            table.getItems().add(flight);
                        }
                        ((Stage) savebtn.getScene().getWindow()).close();

                    }else {
                        erorlbl.setText("Enter a Valid airplane ID");
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
