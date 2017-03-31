import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class test2 extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        // label = just text
        Label label1 = new Label("1st scene!");
        Button button1 = new Button("Go to scene2");
        button1.setOnAction(e -> window.setScene(scene2));

        // Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);

        // Button 2
        Button button2 = new Button("Back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        // Display scene1 first
        window.setScene(scene1);
        window.setTitle("This is a title. Like. 4 r3a1, y0.");
        window.show();
    }
}
