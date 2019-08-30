package menuMainMenu;
import game.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin Karjus 1 on 16/04/2017.
 */
public class mainMenuController implements Initializable {

    @FXML
    private GridPane mainMenuPane;

    @FXML
    private Button newGameBtn;

    @FXML
    private Button loadGameBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button nameEditorBtn;

    @FXML
    private Button mapEditorBtn;

    @FXML
    private Button traitEditorBtn;

    @FXML
    private Button saveGameBtn;

    @FXML
    void exitGame(ActionEvent event) {
        Startup.getPrimaryStage().close();
    }

    @FXML
    void enteredButton(MouseEvent event) {
        Image image = new Image("menuMainMenu/button.png");
        mainMenuPane.setCursor(new ImageCursor(image, 0, 0));
        //System.out.println("glory");
    }

    @FXML
    void exitedButton(MouseEvent event) {
        Image image = new Image("menuMainMenu/default.png");
        mainMenuPane.setCursor(new ImageCursor(image, 0, 0));
        //System.out.println("cake");
    }

    @FXML
    void loadGame(ActionEvent event) {

    }

    @FXML
    void openMapEditor(ActionEvent event) {

    }

    @FXML
    void openNameEditor(ActionEvent event) {

    }

    @FXML
    void openTraitEditor(ActionEvent event) {
        Stage stage2 = new Stage();
        stage2.initOwner(Startup.getPrimaryStage());
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initStyle(StageStyle.UNDECORATED);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/editors/traitEditor.fxml"));
            stage2.setTitle("trait editor");
            stage2.setScene(new Scene(root));
            stage2.show();
        } catch(java.io.IOException a){
            System.out.println("Failed to open editor");
        }
    }

    @FXML
    private TextField playerCountTextField;

    @FXML
    void saveGame(ActionEvent event) {

    }

    @FXML
    void startNewGame(ActionEvent event) {
        int i = 1;
        try {
            i = Integer.valueOf(playerCountTextField.getText());
        } catch(NumberFormatException e) {
            i = 1;
        }
        RunGame game = new RunGame(i);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        mainMenuPane.setCursor(new ImageCursor(Startup.defaultCuror(), 0, 0));
    }
}
