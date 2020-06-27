package controler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    @FXML TextField empusernamefeild;
    @FXML TextField passusernamefeild;
    @FXML PasswordField emppasswordfeild;
    @FXML PasswordField passpasswordfeild;
    @FXML Label emperorlbl;
    @FXML Label passerorlbl;
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

        empexitbtn.setOnAction( e -> System.exit(0));
        passclosebtn.setOnAction( e -> System.exit(0));

        passsinginbtn.setOnAction( e -> {
            if (registerstage==null){
                try {
                    BorderPane root = FXMLLoader.load(this.getClass().getResource("../view/PassengerRegister.fxml"));
                    registerstage = new Stage();
                    registerstage.initStyle(StageStyle.UNDECORATED);
                    registerstage.setScene(new Scene(root));
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
                    ArrayList<Manager> managers = DataBase.getmanagers();
                    for (int i = 0 ; i < managers.size() ; i++){
                        if (empusernamefeild.getText().equals(managers.get(i).getUsername()) &&
                                emppasswordfeild.getText().equals(managers.get(i).getPassword())){
                            /////////////going to managers page
                        }
                    }

                    ArrayList<Employee> employees = DataBase.getemployees();
                    for (int i = 0 ; i < employees.size() ; i++){
                        if (empusernamefeild.getText().equals(employees.get(i).getUsername()) &&
                                emppasswordfeild.getText().equals(employees.get(i).getPassword())){
                            /////////////going to employees page
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                emperorlbl.setText("username Or password Is wrong");
            }

        });

        passloginbtn.setOnAction( e -> {
            if (passusernamefeild.getText().isEmpty() || passpasswordfeild.getText().isEmpty()){
                passerorlbl.setText("fill all fields");
            }else {
                try {
                    ArrayList<Passenger> passengers = DataBase.getpassengers();

                    for ( int i = 0 ; i < passengers.size() ; i++){
                        if (passusernamefeild.getText().equals(passengers.get(i).getUsername()) && passpasswordfeild.
                                getText().equals(passengers.get(i).getUsername())){
                            ////////going to passengers page
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                passerorlbl.setText("username Or password Is wrong");
            }

        });


    }
}
