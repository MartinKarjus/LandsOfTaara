package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The start of it all.
 */
public class Startup extends Application {

    /*
    public void startGame() {
        // should go to main menu from here, just here for testing
        //RunGame game = new RunGame();
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            primaryStage.setTitle("plz work");
            primaryStage.setScene(new Scene(root, 800, 500));
            primaryStage.show();
        } catch(java.io.IOException a){
            System.out.println("O NOES");
        }
    }*/
    private static Stage pStage;

    private static Image defCursor;

    private static Image redCursor;

    public static Stage getPrimaryStage() {
        return pStage;
    }

    public static Image defaultCuror() { return defCursor;}

    public static Image redCuror() { return redCursor;}

    private void setPrimaryStage(Stage pStage) {
        Startup.pStage = pStage;
    }

    private void setDefaultCursor(Image img) { Startup.defCursor = img;}

    private void setRedCursor(Image img) { Startup.redCursor = img;}

    public static void promptStage(Stage primaryStage) {
        // #################################### popup for closing
        Stage stage2 = new Stage();
        stage2.initOwner(primaryStage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initStyle(StageStyle.UNDECORATED);
        VBox vBox = new VBox(20);
        Label l = new Label("Are you sure you want to exit?");
        Button accept = new Button("Yes.");
        Button decline = new Button("No.");
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(l, accept, decline);
        stage2.setScene(new Scene(vBox, 400, 400));
        stage2.getScene().getStylesheets().clear();
        stage2.getScene().getStylesheets().add("prompt.css");

        accept.setOnMouseClicked(event -> {
            //Stage bla = stage2.get
            stage2.close();
            primaryStage.close();
            //primaryStage.close();
        });
        decline.setOnMouseClicked(event -> {
            stage2.close();
        });

        primaryStage.getScene().setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                stage2.show();
            }
        });
    }

    public void setCursors() {
        Image image = new Image("menuMainMenu/button.png");
        setRedCursor(image);
        image = new Image("menuMainMenu/default.png");
        setDefaultCursor(image);
    }


    public static double screenX;
    public static double screenY;

    public void setScreenXY(double x, double y) {
        screenX = x;
        screenY = y;
    }

    public static double getScreenX() {
        return screenX;
    }

    public static double getScreenY() {
        return screenY;
    }

    public void startUp(Stage primaryStage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        setScreenXY(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        primaryScreenBounds = null;

        /*
        System.out.println(primaryScreenBounds.getMinX());
        System.out.println(primaryScreenBounds.getMinY());
        System.out.println(primaryScreenBounds.getWidth());
        System.out.println(primaryScreenBounds.getHeight());
        */

        setCursors();


        setPrimaryStage(primaryStage);


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuMainMenu/mainMenu.fxml"));
            //FXMLLoader loader=new FXMLLoader(getClass().getResource("/menuMainMenu/mainMenu.fxml"));
            //Parent root =(Parent) loader.load();
            primaryStage.setTitle("plz work");
            Scene scene = new Scene(root, 800, 500);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(false);


            primaryStage.show();

            promptStage(primaryStage);
        } catch (java.io.IOException error) {
            System.out.println("Failed to open main menu: " + error);
        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        startUp(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
