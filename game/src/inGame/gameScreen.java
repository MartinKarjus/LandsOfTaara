package inGame;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * ############################# CLASS IS ONLY FOR TESTING PURPOSES ################################
 */
public class gameScreen extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Image imgz = new Image("portraits/01m.jpg");
        imgz = new Image("portraits/dead.png");
        Circle circ = new Circle(20);

        circ.setFill(new ImagePattern(imgz));

        BorderPane pane = new BorderPane();
        Pane paney = new Pane();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

        Image image = new Image("inGame/seen3.png", stage.getWidth(), stage.getHeight(),
                false, false);
        ImageView img = new ImageView(image);

        Image image2 = new Image("game/readPositions.png", stage.getWidth(), stage.getHeight(),
                false, false);
        ImageView img2 = new ImageView(image2);

        img.fitWidthProperty().bind(pane.widthProperty());
        img.fitHeightProperty().bind(pane.heightProperty());
        img2.fitWidthProperty().bind(pane.widthProperty());
        img2.fitHeightProperty().bind(pane.heightProperty());

        paney.getChildren().addAll(img);
        pane.setCenter(paney);
        pane.setLeft(img2);

        BorderPane pane2 = new BorderPane();
        Pane paney2 = new Pane();
        paney2.getChildren().addAll(img2);
        pane2.setCenter(paney2);


        Stage stage2 = new Stage();
        stage2.initOwner(stage);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initStyle(StageStyle.UNDECORATED);

        img.setOnMouseClicked(event -> {

            final WritableImage a = pane2.snapshot(null, null);
            final PixelReader pR = a.getPixelReader();

            Color color = pR.getColor((int)event.getSceneX(), (int)event.getSceneY());

            System.out.println("\nPixel color at coordinates (" + event.getSceneX() + "," + event.getSceneY() + ") " + color.toString());
            System.out.println("R = " + color.getRed());
            System.out.println("G = " + color.getGreen());
            System.out.println("B = " + color.getBlue());

            System.out.println("end");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
