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
import javafx.stage.Stage;
import model.Ticket;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Ticket_tableControler implements Initializable {
    // initialing the table and its columns
    @FXML TableView<Ticket> table;
    @FXML TableColumn<Ticket, Integer> id;
    @FXML TableColumn<Ticket, Integer> flightid;
    @FXML TableColumn<Ticket, Double> price;
    @FXML TableColumn<Ticket, Double> loss;

    @FXML Button addbtn;
    @FXML Button removebtn;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;
    @FXML ImageView airplanesearchimg;
    @FXML TextField airplaneidsearcgfeild;

    static Stage registerstage = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        flightid.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        loss.setCellValueFactory(new PropertyValueFactory<>("loss"));
        try {
            table.setItems(DataBase.gettickets());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        removebtn.setOnAction( e -> {
            Ticket selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deletticket(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Ticket searchedticket = null;
                    try {
                        searchedticket = DataBase.searchInticketsid(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedticket != null) {
                        ObservableList<Ticket> selected = FXCollections.observableArrayList();
                        selected.add(searchedticket);
                        table.setItems(selected);
                    }
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        airplanesearchimg.setOnMousePressed( e -> {
            if (!airplaneidsearcgfeild.getText().isEmpty()) {
                try {
                        ObservableList<Ticket> searchidInflights = DataBase.searchflight_idIntickets(Integer.parseInt(airplaneidsearcgfeild.getText()));
                        table.setItems(searchidInflights);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });
        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.gettickets());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

    }
}
