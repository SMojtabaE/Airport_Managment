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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashbord_managerControler implements Initializable {
    @FXML BorderPane borderpane;
    @FXML Circle profilepic;
    @FXML StackPane passengersstack;
    @FXML StackPane employeesstack;
    @FXML StackPane airplanestack;
    @FXML StackPane ticketstack;
    @FXML StackPane reportsstack;
    @FXML StackPane flightstack;
    @FXML StackPane passticketsstack;
    @FXML StackPane logout;
    @FXML Button username;

    private Manager manager;
    static Stage profilestage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                ((Stage)logout.getScene().getWindow()).close();
            } catch (IOException ex) {
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
}
