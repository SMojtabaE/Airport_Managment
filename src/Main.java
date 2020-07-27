
import controler.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Manager;

import java.sql.SQLException;


public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

       AnchorPane pane =  FXMLLoader.load(Main.class.getResource("view/Splashscreen.fxml"));
        primaryStage.setScene(new Scene(pane));
    //    primaryStage.setTitle("Airport Manager by S_M_E");
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args);


//        Manager mg = new Manager("moji","ahmadi","mo93","123",
//                "mo93esdm@gmail.com","khoddrmoj","09174601584",57.50,1);
//        try {
//            mg.setId(DataBase.creatmanager(mg));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(mg.getId() + "  " + mg.getName());
//        mg.show();
        }
}
