package controler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Passenger;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PassengerRegisterControler implements Initializable {
    @FXML Button cancelbtn;
    @FXML Button savebtn;
    @FXML TextField namefeild;
    @FXML TextField lastnamefeild;
    @FXML TextField usernamefeild;
    @FXML TextField emailfeild;
    @FXML TextField phonefeild;
    @FXML TextField monyfeild;
    @FXML PasswordField passwordfeild;
    @FXML Label erorlbl;
    @FXML BorderPane root;

    private double x = 0, y = 0;
    private TableView table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();

        cancelbtn.setOnAction( e -> {
            LoginpageControler.registerstage = null;
            PassebgertableControler.registerstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });

        savebtn.setOnAction( e -> {
            if (namefeild.getText().isEmpty() || lastnamefeild.getText().isEmpty() || usernamefeild.getText().isEmpty()
                    || passwordfeild.getText().isEmpty() || emailfeild.getText().isEmpty() ||
                    phonefeild.getText().isEmpty() || monyfeild.getText().isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else if (isValid(emailfeild.getText())) {
                try {
                    if (DataBase.checkregisrerOfpassenger(usernamefeild.getText())){
                        Passenger passenger = new Passenger(namefeild.getText(),lastnamefeild.getText(),
                                usernamefeild.getText(),passwordfeild.getText(),emailfeild.getText(),
                                phonefeild.getText(),Double.parseDouble(monyfeild.getText()));
                        int id = DataBase.creatpassenger(passenger);
                        passenger.setId(id);
                        LoginpageControler.registerstage = null;
                        PassebgertableControler.registerstage = null;
                            if (table !=null){
                                table.getItems().add(passenger);
                            }
                        ((Stage)savebtn.getScene().getWindow()).close();
                    }else {
                        erorlbl.setText("chose another username");
                        Toolkit.getDefaultToolkit().beep();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ep){
                    erorlbl.setText("Enter Number in money Field");
                    Toolkit.getDefaultToolkit().beep();
                }
            }else{
                    erorlbl.setText("The email is invalid");
                Toolkit.getDefaultToolkit().beep();
            }
        });
    }
    public void settable(TableView table){ this.table = table; }

    public static boolean isValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
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
