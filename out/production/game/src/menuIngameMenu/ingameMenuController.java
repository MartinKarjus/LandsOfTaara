package menuIngameMenu;

import game.Startup;
import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Almost done with UI.. Then again.. I've been Almost done for 5 days so...
 */
public class ingameMenuController implements Initializable {

    @FXML
    void continueGame(ActionEvent event) {
        Game.getGame().closeSecondary();
    }

    @FXML
    void escPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            Game.getGame().closeSecondary();
        }
    }

    @FXML
    void exitGame(ActionEvent event) {
        Game.getGame().closeSecondary();
        Game.getGame().getMapStage().close();
    }

    @FXML
    void goMainMenu(ActionEvent event) {
        Game.getGame().getMapStage().close();
        Startup startup = new Startup();
        startup.startUp(Startup.getPrimaryStage());
    }

    @FXML
    void saveGame(ActionEvent event) {
        Game.getGame().saveGame();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

    }
}
