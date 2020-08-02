package controler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Passenger;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PassengersProfileControler implements Initializable {

    @FXML TextField namefeild;
    @FXML TextField lastnamefeild;
    @FXML TextField phnumberfeild;
    @FXML TextField moneyfeild;
    @FXML TextField usernamefeild;
    @FXML TextField passwordfeild;
    @FXML TextField emailfeild;

    @FXML Button savebtn;
    @FXML Button cancelbtn;
    @FXML Button editprofilephbtn;
    @FXML Circle crl;
    @FXML Label erorlbl;
    @FXML AnchorPane root;

    private double x = 0, y = 0;

    private Passenger user;
    private TableView table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeDraggable();
        Platform.runLater( () -> {

            Image image = null;
            try {
                image = new Image(new FileInputStream(user.getProfile_photo_Path()));
                // image = new Image("../view/Picturs/passengers_defult_photo.png");
                //image = new Image("\\H:\\programs code\\Java\\Airport_Managment\\src\\view\\Picturspassengers_defult_photo.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            crl.setFill(new ImagePattern(image));
            namefeild.setText(user.getName());
            lastnamefeild.setText(user.getLastname());
            usernamefeild.setText(user.getUsername());
            passwordfeild.setText(user.getPassword());
            emailfeild.setText(user.getEmail());
            moneyfeild.setText(String.valueOf(user.getMoney()));
            phnumberfeild.setText(user.getPhoneNumber());
            user.show();
        });
//        System.out.println(user.getId());
        //setuser(24);

        savebtn.setOnAction( e -> {
//                if (namefeild.getText().isEmpty() || lastnamefeild.getText().isEmpty() || usernamefeild.getText().isEmpty()
//                        || passwordfeild.getText().isEmpty() || emailfeild.getText().isEmpty() ||
//                        phnumberfeild.getText().isEmpty() || moneyfeild.getText().isEmpty()) {
//                    erorlbl.setText("fill all parameters");
//                    Toolkit.getDefaultToolkit().beep();
//                } else if (isValid(emailfeild.getText())) {
//                    try {
//                        if (DataBase.checkregisrerOfpassenger(usernamefeild.getText())) {
//                            user.setName(namefeild.getText());
//                            user.setLastname(lastnamefeild.getText());
//                            user.setMoney(Double.parseDouble(moneyfeild.getText()));
//                            user.setEmail(emailfeild.getText());
//                            user.setUsername(usernamefeild.getText());
//                            user.setPassword(passwordfeild.getText());
//                            user.setPhoneNumber(phnumberfeild.getText());
//                            DataBase.updatpassenger(user);
//                            PassebgertableControler.editstage = null;
//                            table.setItems(DataBase.getpassengers());
//                            ((Stage) savebtn.getScene().getWindow()).close();
//                        }
//                    } catch (SQLException ex) {
//                        ex.printStackTrace();
//                    } catch (NumberFormatException ep) {
//                        erorlbl.setText("Enter Number in money Field ");
//                        Toolkit.getDefaultToolkit().beep();
//                    }
//                }else {
//                    erorlbl.setText("chose another username");
//                    Toolkit.getDefaultToolkit().beep();
//                }
           // PassebgertableControler.editstage = null;
           // ((Stage) savebtn.getScene().getWindow()).close();

            if (namefeild.getText().isEmpty() || lastnamefeild.getText().isEmpty() || usernamefeild.getText().isEmpty()
                        || passwordfeild.getText().isEmpty() || emailfeild.getText().isEmpty() ||
                        phnumberfeild.getText().isEmpty() || moneyfeild.getText().isEmpty()){
                erorlbl.setText("fill all parameters");
                Toolkit.getDefaultToolkit().beep();
            } else if (isValid(emailfeild.getText())) {
                try {
                        user.setName(namefeild.getText());
                        user.setLastname(lastnamefeild.getText());
                        user.setMoney(Double.parseDouble(moneyfeild.getText()));
                        user.setEmail(emailfeild.getText());
                        user.setUsername(usernamefeild.getText());
                        user.setPassword(passwordfeild.getText());
                        user.setPhoneNumber(phnumberfeild.getText());
                        DataBase.updatpassenger(user);
                        PassebgertableControler.editstage = null;
                        table.setItems(DataBase.getpassengers());
                        ((Stage) savebtn.getScene().getWindow()).close();

                } catch (SQLException ex){
                    ex.printStackTrace();
                } catch (NumberFormatException ep){
                    erorlbl.setText("Enter Number in money Field ");
                        Toolkit.getDefaultToolkit().beep();
                }
            }else {
                erorlbl.setText("The email is invalid");
                Toolkit.getDefaultToolkit().beep();
            }
        });

        cancelbtn.setOnAction( e -> {
            PassebgertableControler.editstage = null;
            ((Stage) savebtn.getScene().getWindow()).close();
        });

        editprofilephbtn.setOnAction( e -> {
            FileChooser fileChooser = new FileChooser();    ///make to me choose from my files
            //pattern that need to look for like png mp4 and...
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.jpg) ," +
                    "(*.png)","*.jpg","*.png");
            fileChooser.getExtensionFilters().add(filter); // getting patern to filechosser

            File file = fileChooser.showOpenDialog(null);
            String filepath = file.toURI().toString();

            // hear i just set path to be good
            filepath=  filepath.replace("file:","");
            filepath=  filepath.replace("/","\\");
            filepath=  filepath.replace("%20"," ");
            String[] path = filepath.split("");
            // deleting the firs / of path
            filepath = "";
            for (int i = 1 ; i<path.length ; i++){
                filepath = filepath + path[i];
            }
            System.out.println(filepath);
            if (file != null){
                Image _image_ = null;
                try {
                    _image_ = new Image(new FileInputStream(filepath));

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                crl.setFill(new ImagePattern(_image_));
                user.setProfile_photo_Path(filepath);
            }
        });
    }
    public void settableANDpassenger(TableView<Passenger> table,int userid){
        this.table = table;
        setuser(userid);
    }
    public void setuser(int id){
        try {
            this.user = DataBase.searchFrompassenger(id);
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
