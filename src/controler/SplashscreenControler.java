package controler;

import animatefx.animation.Bounce;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashscreenControler implements Initializable {
    @FXML AnchorPane splashroot;
    @FXML Circle crl1;
    @FXML Circle crl2;
    @FXML Circle crl3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadsplashscreen();
    }

    public void loadsplashscreen(){
        //            //Load splash screen view FXML
//            AnchorPane pane = FXMLLoader.load(getClass().getResource(("../view/Splashscreen.fxml")));
//            //Add it to root container (Can be StackPane, AnchorPane etc)
//            root.getChildren().removeAll();
//            root.getChildren().setAll(pane);

        //Load splash screen with fade in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), splashroot);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        //Finish splash with fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), splashroot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();

        // circls
        new Bounce(crl1).setCycleCount(20).setDelay(Duration.valueOf("1000ms")).play();
        new Bounce(crl2).setCycleCount(20).setDelay(Duration.valueOf("1300ms")).play();
        new Bounce(crl3).setCycleCount(20).setDelay(Duration.valueOf("1500ms")).play();

        //After fade in, start fade out
        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        //After fade out, load actual content
        fadeOut.setOnFinished((e) -> {
            try {
                BorderPane pane =  FXMLLoader.load(getClass().getResource("../view/Loginpage.fxml"));
                Stage primaryStage = ((Stage)splashroot.getScene().getWindow());

                primaryStage.setScene(new Scene(pane));
                primaryStage.setTitle("Airport Manager by S_M_E");
                primaryStage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
    }

}
