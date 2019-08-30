package menuLaws;


import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Almost done with UI.. Then again.. I've been Almost done for 5 days so...
 */
public class lawsMenuController implements Initializable {

    @FXML
    private Label currentLawLabel;

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

    @FXML
    void agnatic(ActionEvent event) {
        Game.getRunGame().getCurrentTurn().getLordinfo().setLaw(Game.getWorld().getLaws().get(0));
        currentLawLabel.setText(Game.getRunGame().getCurrentTurn().getLordinfo().getLaw());
    }

    @FXML
    void agnaticCognatic(ActionEvent event) {
        Game.getRunGame().getCurrentTurn().getLordinfo().setLaw(Game.getWorld().getLaws().get(1));
        currentLawLabel.setText(Game.getRunGame().getCurrentTurn().getLordinfo().getLaw());
    }

    @FXML
    void cognatic(ActionEvent event) {
        Game.getRunGame().getCurrentTurn().getLordinfo().setLaw(Game.getWorld().getLaws().get(2));
        currentLawLabel.setText(Game.getRunGame().getCurrentTurn().getLordinfo().getLaw());
    }



    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        currentLawLabel.setText(Game.getRunGame().getCurrentTurn().getLordinfo().getLaw());
    }
}
