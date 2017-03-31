import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*
terms:
window = stage
inside window = scene
 */

/*
public class test extends Application{

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title_of_window");
        button = new Button();
        button.setText("Click me");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene myscene = new Scene(layout, 300, 250);
        primaryStage.setScene(myscene);
        primaryStage.show();
    }
}
*/
/*
public class test extends Application implements EventHandler<ActionEvent>{

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title_of_window");
        button = new Button();
        button.setText("Click me");

        button.setOnAction(this); // look in This class(not in some other module)

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene myscene = new Scene(layout, 300, 250);
        primaryStage.setScene(myscene);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        // gets called when button is clicked

        // identify button
        // button is just name of my Button object
        if(event.getSource()==button){
            System.out.println("Trolololol");
        }
    }
}
*/

/*
public class test extends Application{

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Title_of_window");
        button = new Button();
        button.setText("Click me");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("I AM THE BUTTON! BOW BEFORE ME!");
            }
        });


        // shorter version :D
        button.setOnAction(e -> System.out.println("also works!"));

        button.setOnAction(e -> {
            System.out.println("O,o");
            System.out.println("o,O"));
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene myscene = new Scene(layout, 300, 250);
        primaryStage.setScene(myscene);
        primaryStage.show();
    }
}
*/




















