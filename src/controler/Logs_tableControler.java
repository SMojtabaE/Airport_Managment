package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Employee;
import model.Logs;


import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Logs_tableControler implements Initializable {

    // initialing the table and its columns
    @FXML TableView<Logs> table;
    @FXML TableColumn<Logs, String> massage;
    @FXML TableColumn<Logs, String> time;
    @FXML TableColumn<Logs, String> date;;
    @FXML TableColumn<Logs, Integer> id;

    @FXML Button removebtn;
    @FXML Button removeallbtn;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        massage.setCellValueFactory(new PropertyValueFactory<>("report"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            table.setItems(DataBase.getlogs());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    try {
                        table.setItems(DataBase.searchinlogs(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getlogs());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        removebtn.setOnAction( e -> {
            Logs selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deletlog(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        removeallbtn.setOnAction( e -> {
            try {
                DataBase.deletlogAll();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
