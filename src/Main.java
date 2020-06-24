import com.sun.org.apache.xpath.internal.objects.XString;
import controler.DataBase;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Manager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main{
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }

    public static void main(String[] args) throws SQLException {// launch(args);
        Manager mg = new Manager("Seyed Mojtaba","Esmaili","sme","9731",
                "mo93esm@gmail.com","khormoj","09394601584",37.50,1);
        mg.setId(DataBase.creatmanager(mg));
        System.out.println(mg.getId() + "  " + mg.getName());
        mg.show();
        }
}
