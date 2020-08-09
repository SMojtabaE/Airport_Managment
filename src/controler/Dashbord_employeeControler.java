package controler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dashbord_employeeControler implements Initializable {

    @FXML BorderPane borderpane;
    @FXML Circle profilepic;
    @FXML Circle crl1;
    @FXML Circle crl2;
    @FXML Circle crl3;
    @FXML Circle crl4;
    @FXML Circle crl5;
    @FXML Circle crl6;
    @FXML Circle crl7;
    @FXML StackPane passengersstack;
    @FXML StackPane airplanestack;
    @FXML StackPane ticketstack;
    @FXML StackPane reportsstack;
    @FXML StackPane flightstack;
    @FXML StackPane passticketsstack;
    @FXML StackPane logout;
    @FXML Button username;
    @FXML Button darkthem;

    private Employee employee;
    static Stage profilestage = null;
    static String backgroundColor;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater( () -> {
            if (employee != null) {
                username.setText(employee.getUsername());
                Image image = null;
                try {
                    image = new Image(new FileInputStream(employee.getProfile_photo_Path()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                profilepic.setFill(new ImagePattern(image));

                if (employee.getDarkthem().equals("off")){
                    backgroundColor = "ffff";
                    username.setTextFill(Paint.valueOf("030303"));
                    darkthem.setTextFill(Paint.valueOf("030303"));
                    darkthem.setText("Light them");
                }else if (employee.getDarkthem().equals("on")){
                    backgroundColor = "13333e";
                    username.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setText("Dark them");
                }
                setthem();
            }
        });

        logout.setOnMousePressed( e -> {
            BorderPane border;
            try {
                border =  FXMLLoader.load(getClass().getResource("../view/Loginpage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Airport Managment By S_M_E");
                stage.setScene(new Scene(border));
                stage.show();
                DataBase.report( "Employee " + employee.getUsername() + " loged out");
                ((Stage)logout.getScene().getWindow()).close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        darkthem.setOnAction( e -> {
            try {
                if (employee.getDarkthem().equals("off")){
                    employee.setDarkthem("on");
                    backgroundColor = "13333e";
                    username.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setTextFill(Paint.valueOf("ffff"));
                    DataBase.updatemployeesthem(employee);
                    setthem();
                }else if (employee.getDarkthem().equals("on")){
                    employee.setDarkthem("off");
                    backgroundColor = "ffff";
                    username.setTextFill(Paint.valueOf("030303"));
                    darkthem.setTextFill(Paint.valueOf("030303"));
                    DataBase.updatemployeesthem(employee);
                    setthem();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        profilepic.setOnMousePressed( e -> {
            if (profilestage == null) {
                FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/EmployeesProfile.fxml"));
                try {
                    loader_.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                EmployeesprofileControler controler1 = loader_.getController();
                controler1.setdata(null, employee, 1, profilepic);
                profilestage = new Stage();
                profilestage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(loader_.getRoot());
                scene.setFill(Color.TRANSPARENT);
                profilestage.setScene(scene);
                profilestage.initStyle(StageStyle.TRANSPARENT);
                profilestage.show();
            }
        });
        airplanestack.setOnMousePressed( e -> {
            BorderPane passtbl = null;
            try {
                passtbl = FXMLLoader.load(getClass().getResource("../view/AirplanesTable.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(passtbl);
        });

        passticketsstack.setOnMousePressed( e -> {
            BorderPane passticket = null;
            try {
                passticket = FXMLLoader.load(getClass().getResource("../view/Passengers_ticket.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(passticket);
        });

        reportsstack.setOnMousePressed( e -> {
            if (profilestage==null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Add_Massage.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Add_massageControler controler = loader.getController();
                try {
                    controler.setuser(employee.getId(),1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                profilestage = new Stage();
                profilestage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(loader.getRoot());
                scene.setFill(Color.TRANSPARENT);
                profilestage.setScene(scene);
                profilestage.initStyle(StageStyle.TRANSPARENT);
                profilestage.show();
            }
        });
        ticketstack.setOnMousePressed( e -> {
            BorderPane tickettbl = null;
            try {
                tickettbl = FXMLLoader.load(getClass().getResource("../view/Tickets_table.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(tickettbl);
        });
        passengersstack.setOnMousePressed( e -> {
            BorderPane passtbl = null;
            try {
                passtbl = FXMLLoader.load(getClass().getResource("../view/Passengerstable.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(passtbl);
        });

        flightstack.setOnMousePressed( e -> {
            BorderPane flighttbl = null;
            try {
                flighttbl = FXMLLoader.load(getClass().getResource("../view/Flights_table.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(flighttbl);
        });

    }
    public void setuser(Employee user){
        this.employee = user;
    }

    public void setthem(){
        borderpane.setStyle("-fx-background-color: #"+backgroundColor);
        profilepic.setStroke(Paint.valueOf(backgroundColor));
        crl1.setStroke(Paint.valueOf(backgroundColor));
        crl2.setStroke(Paint.valueOf(backgroundColor));
        crl3.setStroke(Paint.valueOf(backgroundColor));
        crl4.setStroke(Paint.valueOf(backgroundColor));
        crl5.setStroke(Paint.valueOf(backgroundColor));
        crl6.setStroke(Paint.valueOf(backgroundColor));
        crl7.setStroke(Paint.valueOf(backgroundColor));
    }
}
