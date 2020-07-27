package controler;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Employee;
import model.Manager;
import model.Passenger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginpageControler implements Initializable {

    @FXML Button emploginbtn;
    @FXML Button empexitbtn;
    @FXML Button passclosebtn;
    @FXML Button passloginbtn;
    @FXML Button passsinginbtn;
    @FXML Button passforgotpassbtn;
    @FXML Button empforgetpassbtn;
    @FXML TextField empusernamefeild;
    @FXML TextField passusernamefeild;
    @FXML PasswordField emppasswordfeild;
    @FXML PasswordField passpasswordfeild;
    @FXML Label emperorlbl;
    @FXML Label passerorlbl;
    @FXML BorderPane root;
    static Stage registerstage = null;

    static Manager superadmin;

    static {
        try {
            superadmin = DataBase.getmanagers().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //loadsplashscreen();
        empexitbtn.setOnAction( e -> System.exit(0));
        passclosebtn.setOnAction( e -> System.exit(0));

        passsinginbtn.setOnAction( e -> {
            if (registerstage==null){
                try {
                    BorderPane root = FXMLLoader.load(this.getClass().getResource("../view/PassengerRegister.fxml"));
                    registerstage = new Stage();
                    registerstage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    registerstage.setScene(scene);
                    registerstage.initStyle(StageStyle.TRANSPARENT);
                    registerstage.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        emploginbtn.setOnAction( e -> {
            if (empusernamefeild.getText().isEmpty() || emppasswordfeild.getText().isEmpty()){
                emperorlbl.setText("fill all fields");
            }else if (empusernamefeild.getText().equals(superadmin.getUsername()) && emppasswordfeild.getText().
                    equals(superadmin.getPassword())){

                ///////////////////later

            }else{
                try {

//                    for (int i = 0 ; i < managers.size() ; i++){
//                        if (empusernamefeild.getText().equals(managers.get(i).getUsername()) &&
//                                emppasswordfeild.getText().equals(managers.get(i).getPassword())){
//                            /////////////going to managers page
//                        }
//                    }
                    Manager user = DataBase.checkusernam(empusernamefeild.getText(),emppasswordfeild.getText());
                    //System.out.println(user.getPassword() + user.getUsername());
                    if (user != null){
                        emperorlbl.setText("welcome sthsdt sdth stdh sdtf bdsfb dgb");
                       // System.out.println("welcome sthsdt sdth stdh sdtf bdsfb dgb");
                        BorderPane root = FXMLLoader.load(this.getClass().getResource("../view/PassengerRegister.fxml"));
                        registerstage = new Stage();
                        registerstage.initStyle(StageStyle.UNDECORATED);
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        registerstage.setScene(scene);
                        registerstage.initStyle(StageStyle.TRANSPARENT);
                        registerstage.show();
                    }else{
                        emperorlbl.setText("wrong sdfgasfgasfv");
                    // System.out.println(" sthsdt sdth stdh sdtf bdsfb dgb");
                }
                ArrayList<Employee> employees = DataBase.getemployees();
                for (int i = 0; i < employees.size(); i++) {
                    if (empusernamefeild.getText().equals(employees.get(i).getUsername()) &&
                            emppasswordfeild.getText().equals(employees.get(i).getPassword())) {
                        /////////////going to employees page
                    }
                }

                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
               // emperorlbl.setText("username Or password Is wrong");
            }

        });

        passloginbtn.setOnAction( e -> {
            if (passusernamefeild.getText().isEmpty() || passpasswordfeild.getText().isEmpty()){
                passerorlbl.setText("fill all fields");
            }else {
                try {
//                    ArrayList<Passenger> passengers = DataBase.getpassengers();
//                    for ( int i = 0 ; i < passengers.size() ; i++){
//                        if (passusernamefeild.getText().equals(passengers.get(i).getUsername()) && passpasswordfeild.
//                                getText().equals(passengers.get(i).getUsername())){
//                            ////////going to passengers page
//                        }
//                    }

                    Passenger passenger = DataBase.checkusernamOfpassengers(passusernamefeild.getText(),passpasswordfeild.getText());
                    if (passenger != null) {
                        passerorlbl.setText("welcome " + passenger.getUsername());
                    }else
                        passerorlbl.setText("username Or password Is wrong");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        empforgetpassbtn.setOnAction( e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ForgottenPassword.fxml"));

            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            ForgottenpasswordControler controler = loader.getController();
            controler.whois(0);

            Stage stage = new Stage(StageStyle.UNDECORATED);

            Scene scene = new Scene(loader.getRoot());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            ((Stage)empforgetpassbtn.getScene().getWindow()).close();
        });

        passforgotpassbtn.setOnAction( e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ForgottenPassword.fxml"));

            try {
                loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ForgottenpasswordControler controler = loader.getController();
            controler.whois(1);
            Stage stage = new Stage(StageStyle.UNDECORATED);

            Scene scene = new Scene(loader.getRoot());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            ((Stage)empforgetpassbtn.getScene().getWindow()).close();
        });
    }


    public void loadsplashscreen(){
        try {
            //Load splash screen view FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource(("../view/Splashscreen.fxml")));
            //Add it to root container (Can be StackPane, AnchorPane etc)
            root.getChildren().removeAll();
            root.getChildren().setAll(pane);

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            //After fade in, start fade out
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            //After fade out, load actual content
            fadeOut.setOnFinished((e) -> {
                try {
                    BorderPane parentContent = FXMLLoader.load(getClass().getResource(("../view/Loginpage.fxml")));
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            });
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
