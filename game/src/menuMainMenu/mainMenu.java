package menuMainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martin Karjus 1 on 15/04/2017.
 */
public class mainMenu extends Application {
    /*
    ############# FOR TESTING ONLY ################
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        primaryStage.setTitle("plz work");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }
    public static void startTrait() {
        launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
