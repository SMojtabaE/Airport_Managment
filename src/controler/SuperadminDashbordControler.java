package controler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuperadminDashbordControler implements Initializable {
    
    @FXML BorderPane borderpane;
    @FXML Circle profilepic;
    @FXML StackPane passengersstack;
    @FXML StackPane employeesstack;
    @FXML StackPane managersstack;
    @FXML Button username;

    private Manager superadmin;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater( () -> {
            if (superadmin != null) {
                username.setText(superadmin.getUsername());
                Image image = null;
                try {
                    image = new Image(new FileInputStream(superadmin.getProfile_photo_Path()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                profilepic.setFill(new ImagePattern(image));
            }
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
        managersstack.setOnMousePressed( e -> {

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

    }
    public void setuser(Manager user){
        this.superadmin = user;
    }
}
