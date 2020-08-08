package controler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Employee;
import model.Manager;
import model.Passenger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
            superadmin = DataBase.getsuperadmin();
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PassengerRegister.fxml"));
                    try {
                        loader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    TableView table = null;
                    PassengerRegisterControler controler = loader.getController();
                    controler.settable(table);
                    registerstage = new Stage();
                    registerstage.initStyle(StageStyle.UNDECORATED);
                    Scene scene = new Scene(loader.getRoot());
                    scene.setFill(Color.TRANSPARENT);
                    registerstage.setScene(scene);
                    registerstage.initStyle(StageStyle.TRANSPARENT);
                    registerstage.show();


            }
        });

        emploginbtn.setOnAction( e -> {
            if (empusernamefeild.getText().isEmpty() || emppasswordfeild.getText().isEmpty()){
                emperorlbl.setText("fill all fields");
            }else if (empusernamefeild.getText().equals(superadmin.getUsername()) && emppasswordfeild.getText().
                    equals(superadmin.getPassword())){
                try {
                    BorderPane  border =  FXMLLoader.load(getClass().getResource("../view/DashbordSuperadmin.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Airport Managment By S_M_E");
                    stage.setScene(new Scene(border));
                    stage.show();
                    DataBase.report("super admin "+superadmin.getUsername() + " loged in");
                    ((Stage)emploginbtn.getScene().getWindow()).close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else{
                try {
                    Manager user = DataBase.checkusernamOfmanager(empusernamefeild.getText(),emppasswordfeild.getText());
                    if (user != null){
                        FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/Dashbord_Managers.fxml"));
                        loader_.load();
                        Dashbord_managerControler controler1 = loader_.getController();
                        controler1.setuser(user);
                        Stage editstage = new Stage();
                        Scene scene = new Scene(loader_.getRoot());
                        editstage.setTitle("Airport Managment By S_M_E");
                        editstage.setScene(scene);
                        editstage.show();
                        DataBase.report( "Manager " + user.getUsername() + " loged in");
                        ((Stage)emploginbtn.getScene().getWindow()).close();
                    }else {
                        Employee employee = DataBase.checkusernamOfemployees(empusernamefeild.getText(),emppasswordfeild.getText());
                        if (employee != null){
                            FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/Dashbord_Employees.fxml"));
                            loader_.load();
                            Dashbord_employeeControler controler1 = loader_.getController();
                            controler1.setuser(employee);
                            Stage editstage = new Stage();
                            Scene scene = new Scene(loader_.getRoot());
                            editstage.setTitle("Airport Managment By S_M_E");
                            editstage.setScene(scene);
                            editstage.show();
                            DataBase.report( "Employee " + employee.getUsername() + " loged in");
                            ((Stage)emploginbtn.getScene().getWindow()).close();
                        }
                    }
                } catch (SQLException | IOException ex) {
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
                    Passenger passenger = DataBase.checkusernamOfpassengers(passusernamefeild.getText(),passpasswordfeild.getText());
                    if (passenger != null) {

                            FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/Dashbord_Passengers.fxml"));
                        try {
                            loader_.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Dashbord_passengersControler controler1 = loader_.getController();
                            controler1.setuser(passenger);
                            Stage editstage = new Stage();
                            Scene scene = new Scene(loader_.getRoot());
                            editstage.setTitle("Airport Managment By S_M_E");
                            editstage.setScene(scene);
                            editstage.show();
                            DataBase.report( "Passenger " + passenger.getUsername() + " loged in");
                            ((Stage)emploginbtn.getScene().getWindow()).close();

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
}
