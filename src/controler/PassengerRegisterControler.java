package controler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Passenger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();

        cancelbtn.setOnAction( e -> {
            LoginpageControler.registerstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });

        savebtn.setOnAction( e -> {
            if (namefeild.getText().isEmpty() || lastnamefeild.getText().isEmpty() ||
                    usernamefeild.getText().isEmpty() || passwordfeild.getText().isEmpty() ||
                    emailfeild.getText().isEmpty() || phonefeild.getText().isEmpty() ||
                    monyfeild.getText().isEmpty() ){
                erorlbl.setText("fill all parameters");
            } else if (isValid(emailfeild.getText())) {
                try {
                    ArrayList<Passenger> passengers = DataBase.getpassengers();
                    Boolean flag = false;
                    for (int i = 0 ; i < passengers.size() ; i++){
                        if (usernamefeild.getText().equals(passengers.get(i).getUsername())){
                            erorlbl.setText("chose another username");
                            flag = true;
                        }
                    }
                    if (flag==false){
                        Passenger passenger = new Passenger(namefeild.getText(),lastnamefeild.getText(),
                                usernamefeild.getText(),passwordfeild.getText(),emailfeild.getText(),
                                phonefeild.getText(),Double.parseDouble(monyfeild.getText()));
                        DataBase.creatpassenger(passenger);
                        LoginpageControler.registerstage = null;
                        ((Stage)savebtn.getScene().getWindow()).close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }else{
                    erorlbl.setText("The email is invalid");


            }
        });
    }



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
