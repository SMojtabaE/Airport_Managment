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
import model.Employee;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeestableControler implements Initializable {

    // initialing the table and its columns
    @FXML TableView<Employee> table;
    @FXML TableColumn<Employee, String> name;
    @FXML TableColumn<Employee, String> lastname;
    @FXML TableColumn<Employee, String> username;
    @FXML TableColumn<Employee, String> email;
    @FXML TableColumn<Employee, String> phnumber;
    @FXML TableColumn<Employee, String> addres;
    @FXML TableColumn<Employee, Double> mony;
    @FXML TableColumn<Employee, Integer> id;

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
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        mony.setCellValueFactory(new PropertyValueFactory<>("salary"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phnumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addres.setCellValueFactory(new PropertyValueFactory<>("adress"));
        try {
            table.setItems(DataBase.getemployees());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addbtn.setOnAction( e -> {
            if (registerstage==null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AddEmployee.fxml"));
                try {
                    loader.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                AddemployeeControler controler = loader.getController();
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

        removebtn.setOnAction( e -> {
            Employee selected= table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                table.getItems().remove(selected);
                try {
                    DataBase.deletmemployee(selected);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchimg.setOnMousePressed( e -> {
            if (!searcgfeild.getText().isEmpty()) {
                try {
                    Employee searchedUser = null;
                    try {
                        searchedUser = DataBase.searchForemployee(Integer.parseInt(searcgfeild.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    if (searchedUser != null) {
                        ObservableList<Employee> selected = FXCollections.observableArrayList();
                        selected.add(searchedUser);
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
                table.setItems(DataBase.getemployees());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        editbtn.setOnAction( e -> {
            if (editstage == null){
                Employee user = table.getSelectionModel().getSelectedItem();
                if (user != null) {
                    FXMLLoader loader_ = new FXMLLoader(getClass().getResource("../view/EmployeesProfile.fxml"));
                    try {
                        loader_.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    EmployeesprofileControler controler1 = loader_.getController();
                    controler1.setdata(table, user,0,null);
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
