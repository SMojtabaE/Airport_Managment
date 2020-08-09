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
import model.Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dashbord_managerControler implements Initializable {
    @FXML BorderPane borderpane;
    @FXML Circle profilepic;
    @FXML Circle crl1;
    @FXML Circle crl2;
    @FXML Circle crl3;
    @FXML Circle crl4;
    @FXML Circle crl5;
    @FXML Circle crl6;
    @FXML Circle crl7;
    @FXML Circle crl8;
    @FXML StackPane passengersstack;
    @FXML StackPane employeesstack;
    @FXML StackPane airplanestack;
    @FXML StackPane ticketstack;
    @FXML StackPane reportsstack;
    @FXML StackPane flightstack;
    @FXML StackPane passticketsstack;
    @FXML StackPane logout;
    @FXML Button username;
    @FXML Button darkthem;

    private Manager manager;
    static Stage profilestage = null;
    static String backgroundColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        String backgroundColor = "13333e";
       // profilepic.setStroke(Paint.valueOf(backgroundColor));
        Platform.runLater( () -> {
            if (manager != null) {
                username.setText(manager.getUsername());
                Image image = null;
                try {
                    image = new Image(new FileInputStream(manager.getProfile_photo_Path()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                profilepic.setFill(new ImagePattern(image));

                if (manager.getDarkthem().equals("off")){
                    backgroundColor = "ffff";
                    username.setTextFill(Paint.valueOf("030303"));
                    darkthem.setTextFill(Paint.valueOf("030303"));
                    darkthem.setText("Light them");
                }else if (manager.getDarkthem().equals("on")){
                    backgroundColor = "13333e";
                    username.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setText("Dark them");
                }
              setthem();
            }
        });


        logout.setOnMousePressed( e -> {
            BorderPane border = null;
            try {
                border =  FXMLLoader.load(getClass().getResource("../view/Loginpage.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Airport Managment By S_M_E");
                stage.setScene(new Scene(border));
                stage.show();
                DataBase.report( "Manager " + manager.getUsername() + " loged out");
                ((Stage)logout.getScene().getWindow()).close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        darkthem.setOnAction( e -> {
            try {
                if (manager.getDarkthem().equals("off")){
                    manager.setDarkthem("on");
                    backgroundColor = "13333e";
                    username.setTextFill(Paint.valueOf("ffff"));
                    darkthem.setTextFill(Paint.valueOf("ffff"));
                    DataBase.updatmanagersthem(manager);
                    darkthem.setText("Light them");
                    setthem();
                }else if (manager.getDarkthem().equals("on")){
                    manager.setDarkthem("off");
                    backgroundColor = "ffff";
                    username.setTextFill(Paint.valueOf("030303"));
                    darkthem.setTextFill(Paint.valueOf("030303"));
                    DataBase.updatmanagersthem(manager);
                    darkthem.setText("Dark them");
                    setthem();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        profilepic.setOnMousePressed( e -> {
            if (profilestage == null) {
                FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/ManagersProfile.fxml"));
                try {
                    loader_.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ManagersprofileControler controler1 = loader_.getController();
                controler1.settableANDpassenger(null, manager, 1, profilepic);
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
            BorderPane passtbl = null;
            try {
                passtbl = FXMLLoader.load(getClass().getResource("../view/Reports_table.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(passtbl);
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

        employeesstack.setOnMousePressed( e -> {
            BorderPane employytable = null;
            try {
                employytable = FXMLLoader.load(getClass().getResource("../view/Employeestable.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(employytable);
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
    public void setuser(Manager user){
        this.manager = user;
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
        crl8.setStroke(Paint.valueOf(backgroundColor));
    }
}
