package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Danielle Droubay
 * @version 1.0
 */
public class HelloApplication extends Application {

    /**
     * Launches the main panel of the application where the user can
     * begin to interact with the application
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 945, 400);
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Launch the application
     * @param args
     */

    public static void main(String[] args) {
            launch();


        }

}

