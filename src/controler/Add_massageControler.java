package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Passenger;
import model.Report_to_managmers;


import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Add_massageControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextArea masage;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;
    private Employee employee = null;
    private Passenger passanger = null;
    private int  who_is;   ///////////  1 = employee    2 = passenger
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        makeDraggable();

        cancelbtn.setOnAction( e -> {
            Dashbord_employeeControler.profilestage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });

        savebtn.setOnAction( e -> {
            if (masage.getText().isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else {
                try {
                    Report_to_managmers report;
                    DateTimeFormatter ftime = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime time1 = LocalTime.now();
                    String time = time1.format(ftime);
                    String date = String.valueOf(LocalDate.now());
                    if (passanger == null){
                        report = new Report_to_managmers(employee.getId(),masage.getText(),date,time,"employee");
                        DataBase.creatreport(report);
                    }else if(employee == null){
                        report = new Report_to_managmers(passanger.getId(),masage.getText(),date,time,"Passenger");
                        DataBase.creatreport(report);
                    }
                    Dashbord_employeeControler.profilestage = null;
                    ((Stage) savebtn.getScene().getWindow()).close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }catch (NumberFormatException ex){
                    erorlbl.setText("Enter Number in Flight_ID Field");
                    Toolkit.getDefaultToolkit().beep();
                }
            }

        });

    }

    public void setuser(int id,int who_is) throws SQLException {
        this.who_is = who_is;
        if (who_is== 1){
            this.employee = DataBase.searchForemployee(id);
        }else{
            this.passanger = DataBase.searchFrompassenger(id);
        }
    }
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
