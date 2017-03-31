import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author zoranpavlovic.blogspot.com
 */
public class testimage extends Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Load Image");

        StackPane sp = new StackPane();
        Image img = new Image("testimg.jpg");
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);

        //Adding HBox to the scene
        Scene scene = new Scene(sp, 1000,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}