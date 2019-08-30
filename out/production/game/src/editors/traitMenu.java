package editors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class traitMenu extends Application {
    /*
    ############# FOR TESTING ONLY ################
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("traitEditor.fxml"));
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

