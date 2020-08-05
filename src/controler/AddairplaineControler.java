package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Airplane;
import model.Employee;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddairplaineControler implements Initializable {

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML TextField txtfield;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;
    private TableView table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();
        cancelbtn.setOnAction( e -> {
            AirplanestableControler.registerstage = null;
            AirplanestableControler.editstage = null;
            ((Stage)cancelbtn.getScene().getWindow()).close();
        });
        savebtn.setOnAction( e -> {
            if (txtfield.getText().isEmpty()){
                erorlbl.setText("fill parameter");
                Toolkit.getDefaultToolkit().beep();
            } else
                try {
                        Airplane airplain = new Airplane(Integer.parseInt(txtfield.getText()));
                        airplain.setId(DataBase.createAirplane(airplain));
                        AirplanestableControler.registerstage = null;
                        AirplanestableControler.editstage = null;
                        if (table !=null){
                            table.getItems().add(airplain);
                        }
                        ((Stage)savebtn.getScene().getWindow()).close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ep){
                    erorlbl.setText("Enter Number in Field");
                    Toolkit.getDefaultToolkit().beep();
                }

        });
    }

    public void settable(TableView table){ this.table = table; }
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
