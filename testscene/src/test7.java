import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class test7 extends Application{

    Stage window;
    Scene scene;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Get some input");

        //Form
        TextField nameInput = new TextField();
        button = new Button("Click me");

        button = new Button("Click me");
        //button.setOnAction(e -> System.out.println(nameInput.getText()));
        button.setOnAction(e -> isInt(nameInput, nameInput.getText()));

        //Layout
        VBox layout = new VBox();
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(nameInput, button);

        scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }

    private boolean isInt(TextField input, String message) {
        try{
            int age = Integer.parseInt(input.getText());
            System.out.println("User is: " + age);
            return true;
        }catch(NumberFormatException e) {
            System.out.println("Error: " + message + " is not a nr.");
            return false;
        }
    }

}




