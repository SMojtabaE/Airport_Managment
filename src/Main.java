
import controler.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

      BorderPane pane =  FXMLLoader.load(Main.class.getResource("view/DashbordSuperadmin.fxml"));
      //AnchorPane pane =  FXMLLoader.load(Main.class.getResource("view/PassengersProfile.fxml"));
       //AnchorPane pane =  FXMLLoader.load(Main.class.getResource("view/Splashscreen.fxml"));
      //  AnchorPane pane =  FXMLLoader.load(Main.class.getResource("view/PassengersProfile.fxml"));
        primaryStage.setScene(new Scene(pane));
       // primaryStage.setFullScreen(true);
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

//        Flight fl = new Flight(1,1,"khurmoj","Ahvaz", LocalDate.now(),
//                LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")),44,"5H", Status.open);
//        System.out.println(fl.getAirplaine_id());
//        try {
//            DataBase.updateflight(fl);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("created Fuck yehhhhhh");

//        Airplane tk = new Airplane(20);
//        try {
//            DataBase.createAirplane(tk);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("created Fuck yehhhhhh");

    }
}
