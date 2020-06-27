
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane pane =  FXMLLoader.load(Main.class.getResource("view/Loginpage.fxml"));
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Airport Manager by S_M_E");
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args);


//        Manager mg = new Manager("Seyed Mojtaba","Esmaili","sme","9731",
//                "mo93esm@gmail.com","khormoj","09394601584",37.50,1);
//        mg.setId(DataBase.creatmanager(mg));
//        System.out.println(mg.getId() + "  " + mg.getName());
//        mg.show();
        }
}
