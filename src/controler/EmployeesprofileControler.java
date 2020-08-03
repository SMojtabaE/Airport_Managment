package controler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Employee;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeesprofileControler implements Initializable {
    @FXML TextField namefeild;
    @FXML TextField lastnamefeild;
    @FXML TextField phnumberfeild;
    @FXML TextField moneyfeild;
    @FXML Button username;
    @FXML TextField passwordfeild;
    @FXML TextField emailfeild;
    @FXML TextArea addresfirld;

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML Button editprofilephbtn;
    @FXML Circle crl;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;

    private Employee user;
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();
        Platform.runLater( () -> {

            Image image = null;
            try {
                image = new Image(new FileInputStream(user.getProfile_photo_Path()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            crl.setFill(new ImagePattern(image));
            namefeild.setText(user.getName());
            lastnamefeild.setText(user.getLastname());
            username.setText(user.getUsername());
            passwordfeild.setText(user.getPassword());
            emailfeild.setText(user.getEmail());
            moneyfeild.setText(String.valueOf(user.getSalary()));
            phnumberfeild.setText(user.getPhoneNumber());
            addresfirld.setText(user.getAdress());
            user.show();
        });

        savebtn.setOnAction( e -> {
            if (namefeild.getText().isEmpty() || lastnamefeild.getText().isEmpty() || username.getText().isEmpty()
                    || passwordfeild.getText().isEmpty() || emailfeild.getText().isEmpty() ||
                    phnumberfeild.getText().isEmpty() || moneyfeild.getText().isEmpty() || addresfirld.getText().isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else if (isValid(emailfeild.getText())) {
                try {
                    user.setName(namefeild.getText());
                    user.setLastname(lastnamefeild.getText());
                    user.setSalary(Double.parseDouble(moneyfeild.getText()));
                    user.setEmail(emailfeild.getText());
                    user.setPassword(passwordfeild.getText());
                    user.setPhoneNumber(phnumberfeild.getText());
                    DataBase.updatemployee(user);
                    EmployeestableControler.editstage = null;
                    EmployeestableControler.registerstage = null;
                    if (table != null) {
                        table.setItems(DataBase.getemployees());
                    }
                    ((Stage) savebtn.getScene().getWindow()).close();

                } catch (SQLException ex){
                    ex.printStackTrace();
                } catch (NumberFormatException ep){
                    erorlbl.setText("Enter Number in Salary Field ");
                    Toolkit.getDefaultToolkit().beep();
                }
            }else {
                erorlbl.setText("The email is invalid");
                Toolkit.getDefaultToolkit().beep();
            }
        });

        cancelbtn.setOnAction( e -> {
            EmployeestableControler.editstage = null;
            EmployeestableControler.registerstage = null;
            ((Stage) savebtn.getScene().getWindow()).close();
        });

        editprofilephbtn.setOnAction( e -> {
            try {
                FileChooser fileChooser = new FileChooser();    ///make to me choose from my files
                //pattern that need to look for like png mp4 and...
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.jpg) ," +
                        "(*.png)", "*.jpg", "*.png");
                fileChooser.getExtensionFilters().add(filter); // getting patern to filechosser

                File file = fileChooser.showOpenDialog(null);
                String filepath = file.toURI().toString();

                // hear i just set path to be good
                filepath = filepath.replace("file:", "");
                filepath = filepath.replace("/", "\\");
                filepath = filepath.replace("%20", " ");
                String[] path = filepath.split("");
                // deleting the firs / of path
                filepath = "";
                for (int i = 1; i < path.length; i++) {
                    filepath = filepath + path[i];
                }
                if (file != null) {
                    Image _image_ = null;
                    try {
                        _image_ = new Image(new FileInputStream(filepath));

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    crl.setFill(new ImagePattern(_image_));
                    user.setProfile_photo_Path(filepath);
                }
            } catch (NullPointerException ex){ }
        });
    }
    public void settableANDpassenger(TableView<Employee> table,int userid){
        this.table = table;
        setuser(userid);
    }
    public void setuser(int id){
        try {
            this.user = DataBase.searchForemployee(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
