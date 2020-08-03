package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddemployeeControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextField namefield;
    @FXML TextField lastnamefield;
    @FXML TextField usernamefield;
    @FXML TextField passwordfield;
    @FXML TextField salaryfield;
    @FXML TextField phnumberfield;
    @FXML TextField emailfield;
    @FXML TextArea addresfield;
    @FXML AnchorPane root;
    @FXML Label erorlbl;

    private double x = 0, y = 0;
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();
        cancelbtn.setOnAction( e -> {
            EmployeestableControler.registerstage = null;
            EmployeestableControler.editstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });

        savebtn.setOnAction( e -> {
        if (namefield.getText().isEmpty() || lastnamefield.getText().isEmpty() || usernamefield.getText().isEmpty()
                || passwordfield.getText().isEmpty() || emailfield.getText().isEmpty() ||
                phnumberfield.getText().isEmpty() || salaryfield.getText().isEmpty() || addresfield.getText().isEmpty()){
            erorlbl.setText("fill all parameters");
            Toolkit.getDefaultToolkit().beep();
        } else if (isValid(emailfield.getText())) {
            try {
                if (DataBase.checkregisrerOfemoloyees(usernamefield.getText())){
                    Employee user = new Employee(namefield.getText(),lastnamefield.getText(),
                            usernamefield.getText(),passwordfield.getText(),emailfield.getText(),
                            addresfield.getText(),phnumberfield.getText(),Double.parseDouble(salaryfield.getText()));
                    user.setId(DataBase.createmployee(user));
                    EmployeestableControler.registerstage = null;
                    EmployeestableControler.editstage = null;
                    if (table !=null){
                        table.getItems().add(user);
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
