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
import model.Passenger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dashbord_passengersControler implements Initializable {

    @FXML BorderPane borderpane;
    @FXML Circle profilepic;
    @FXML StackPane ticketstack;
    @FXML StackPane reportsstack;
    @FXML StackPane flightstack;
    @FXML StackPane passticketsstack;
    @FXML StackPane logout;
    @FXML Button username;

    static Passenger passenger;
    static Stage profilestage = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater( () -> {
            if (passenger != null) {
                username.setText(passenger.getUsername());
                Image image = null;
                try {
                    image = new Image(new FileInputStream(passenger.getProfile_photo_Path()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                profilepic.setFill(new ImagePattern(image));
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
                DataBase.report( "Passenger " + passenger.getUsername() + " loged out");
                ((Stage)logout.getScene().getWindow()).close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        profilepic.setOnMousePressed( e -> {
            if (profilestage == null) {
                FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/PassengersProfile.fxml"));
                try {
                    loader_.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                PassengersProfileControler controler1 = loader_.getController();
                controler1.setdata(null, passenger, profilepic);
                profilestage = new Stage();
                profilestage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(loader_.getRoot());
                scene.setFill(Color.TRANSPARENT);
                profilestage.setScene(scene);
                profilestage.initStyle(StageStyle.TRANSPARENT);
                profilestage.show();
            }
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
                    controler.setuser(passenger.getId(),2);
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

        flightstack.setOnMousePressed( e -> {
            BorderPane flighttbl = null;
            try {
                flighttbl = FXMLLoader.load(getClass().getResource("../view/Show_flightsTopassengers.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(flighttbl);
        });

        ticketstack.setOnMousePressed( e -> {
            BorderPane flighttbl = null;
            try {
                flighttbl = FXMLLoader.load(getClass().getResource("../view/Show_ticketsTopassengers.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(flighttbl);
        });
        passticketsstack.setOnMousePressed( e -> {
            BorderPane flighttbl = null;
            try {
                flighttbl = FXMLLoader.load(getClass().getResource("../view/Show_ticketsOfpassenger.fxml"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            borderpane.setCenter(flighttbl);
        });
    }
    public void setuser(Passenger user){
        this.passenger = user;
    }
}
