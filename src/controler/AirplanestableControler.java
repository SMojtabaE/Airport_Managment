package controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Airplane;
import model.Employee;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AirplanestableControler implements Initializable {
    @FXML TableView<Airplane> table;
    @FXML TableColumn<Airplane , Integer> id;
    @FXML TableColumn<Airplane , Integer> seats;

    @FXML Button addbtn;
    @FXML Button removebtn;
    @FXML Button editbtn;
    @FXML ImageView searchimg;
    @FXML ImageView louddataimg;
    @FXML TextField searcgfeild;

    static Stage registerstage = null;
    static Stage editstage = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        try {
            table.setItems(DataBase.getAirplanes());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addbtn.setOnAction( e -> {
        });

        removebtn.setOnAction( e -> {
                Airplane selected= table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    table.getItems().remove(selected);
                    try {
                        DataBase.deletairplane(selected);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Airplane searchedairplane = null;
                    try {
                        searchedairplane = DataBase.searchForairplane(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedairplane != null) {
                        ObservableList<Airplane> selected = FXCollections.observableArrayList();
                        selected.add(searchedairplane);
                        table.setItems(selected);
                    }
                }catch (NumberFormatException ep){
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("NumberFormatException, pleas enter an integer valuo");
                }
            }
        });

        louddataimg.setOnMousePressed( e -> {
            try {
                table.setItems(DataBase.getAirplanes());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        addbtn.setOnAction( e -> {
            if (registerstage==null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Addairplane.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AddairplaineControler controler = loader.getController();
                controler.settable(table);
                registerstage = new Stage();
                registerstage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(loader.getRoot());
                scene.setFill(Color.TRANSPARENT);
                registerstage.setScene(scene);
                registerstage.initStyle(StageStyle.TRANSPARENT);
                editstage = registerstage;
                registerstage.show();
            }
        });

        editbtn.setOnAction( e -> {
                if (editstage == null){
                    Airplane plane = table.getSelectionModel().getSelectedItem();
                    if (plane != null) {
                        FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/Editairplane.fxml"));
                        try {
                            loader_.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        EditairplaneControler controler1 = loader_.getController();
                        controler1.settableANDpassenger(table, plane);
                        editstage = new Stage();
                        editstage.initStyle(StageStyle.UNDECORATED);
                        Scene scene = new Scene(loader_.getRoot());
                        scene.setFill(Color.TRANSPARENT);
                        editstage.setScene(scene);
                        editstage.initStyle(StageStyle.TRANSPARENT);
                        registerstage = editstage;
                        editstage.show();
                    }
                }
        });
    }
}
