import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

//Use Platform.exit() NOT window.close().﻿ (closes multiple windows)
public class test4 extends Application {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Title here - who are you?");
        window.setOnCloseRequest(e -> {
            e.consume(); //ignore close request, run own code instead
            closeProgram();
        });
        /*
        // called when X is pressed in the corner
        window.setOnCloseRequest(e -> closeProgram());
        */

        button = new Button("Close me");
        button.setOnAction(e -> closeProgram());

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();


    }

    private void closeProgram() {
        /*
        System.out.println("Exiting . . .");
        Platform.exit();
        //window.close()
        */
        Boolean answer = ConfirmBox.display("Title", "Sure you wanna exit?");
        if (answer) {
            Platform.exit();
        }

    }
}



