package menuPlots;


import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Almost done with UI.. Then again.. I've been Almost done for 5 days so...
 */
public class plotMenuController implements Initializable {


    @FXML
    private Button exitBtn;

    @FXML
    void escPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            Game.getGame().closeSecondary();
        }
    }

    @FXML
    void closeBtn(ActionEvent event) {
        Game.getGame().closeSecondary();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }
}
