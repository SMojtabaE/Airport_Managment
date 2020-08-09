package controler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Employee;
import model.Manager;
import model.Passenger;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ForgottenpasswordControler implements Initializable {

    @FXML Button resetbtn;
    @FXML Button backbtn;
    @FXML TextField emailfeild;
    @FXML Label lbl;
    @FXML BorderPane root;

    private double x = 0, y = 0;

    private int who_is;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();

        resetbtn.setOnAction( e -> {
            lbl.setStyle(" -fx-text-fill: #55e66c");
            boolean flag = false;
            if (lbl.getText().isEmpty()){
                lbl.setStyle(" -fx-text-fill: red");
                lbl.setText("Enter Your Email");
                Toolkit.getDefaultToolkit().beep();
            }else if (isValid(emailfeild.getText())){
                if (who_is == 0) {
                    try {
                        String password =DataBase.resetpasswordusers(emailfeild.getText());
                        if (password != null){
                            lbl.setText("Password Is " + password);
                            flag = true;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else if (who_is == 1) {
                    try {
                        String password = DataBase.resetpasswordPassengers(emailfeild.getText());
                        if (password != null){
                            lbl.setText("Password Is " + password);
                            flag = true;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (!flag) {
                    lbl.setStyle(" -fx-text-fill: red");
                    lbl.setText("wrong Email");
                    Toolkit.getDefaultToolkit().beep();
                }
            }else {
                lbl.setStyle(" -fx-text-fill: red");
                lbl.setText("Email is invalid");
                Toolkit.getDefaultToolkit().beep();
            }
        });


        backbtn.setOnAction( e -> {
            try {
                BorderPane root = FXMLLoader.load(this.getClass().getResource("../view/Loginpage.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ((Stage)backbtn.getScene().getWindow()).close();
        });

    }
    public void whois(int number){
        this.who_is = number;
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

    public static boolean isValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
