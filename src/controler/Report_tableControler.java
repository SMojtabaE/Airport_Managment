package controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Report_to_managmers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Report_tableControler implements Initializable {

    // initialing the table and its columns
    @FXML TableView<Report_to_managmers> table;
    @FXML TableColumn<Report_to_managmers, String> massage;
    @FXML TableColumn<Report_to_managmers, String> time;
    @FXML TableColumn<Report_to_managmers, String> username;
    @FXML TableColumn<Report_to_managmers, String> date;
    @FXML TableColumn<Report_to_managmers, String> idof;
    @FXML TableColumn<Report_to_managmers, Integer> id;

    @FXML Button removebtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        massage.setCellValueFactory(new PropertyValueFactory<>("massage"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        username.setCellValueFactory(new PropertyValueFactory<>("who_is"));
        idof.setCellValueFactory(new PropertyValueFactory<>("idof"));
        try {
            table.setItems(DataBase.getreports());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        removebtn.setOnAction( e -> {
            Report_to_managmers selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deletreport(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
