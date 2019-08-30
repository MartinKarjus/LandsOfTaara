package summaryScreen;


import game.RunGame;
import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import menuCharacter.charCountiesController;
import world.Event;
import world.Person;
import world.War;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Almost done with UI.. Then again.. I've been Almost done for 5 days so...
 */
public class summaryController implements Initializable {

    @FXML
    private Label yearLbl;

    @FXML
    private TextArea eventText;

    @FXML
    private Label seasonLbl;

    @FXML
    private Circle selfCircle;

    @FXML
    private Label nameLbl;

    @FXML
    private Label capitalLbl;

    @FXML
    private VBox warBox;

    @FXML
    private Label eventNameLbl;

    @FXML
    void closeBtn(ActionEvent event) {
        Game.getGame().closeSecondary();
    }

    @FXML
    void escPress(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
            Game.getGame().closeSecondary();
        }
    }


    private void dealWithEvent(Person p, Event e) {
        if(e.getEffect() != null) {
            for(String s : e.getEffect()) {
                if(s.contains("gold")) {
                    int i = Integer.parseInt(s.substring(s.indexOf("d") + 1));
                    p.getLordinfo().addCash(i);
                }
            }
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        yearLbl.setText(String.valueOf(Game.getWorld().getCurrentDate().getYear()));
        seasonLbl.setText(Game.getWorld().getCurrentDate().getSeasonFullName());
        Person p = Game.getRunGame().getCurrentTurn();
        if(p.getAlive()) {
            Image image2 = new Image("portraits/" + p.getPortrait());
            selfCircle.setFill(new ImagePattern(image2));
        } else {
            Image image1 = new Image("portraits/dead.png");
            selfCircle.setFill(new ImagePattern(image1));
        }
        nameLbl.setText(p.getName() + " " + p.getSurname());
        capitalLbl.setText(p.getLordinfo().getCounties().get(0).getName() + " " + "maakond");
        if(p.getLordinfo().getLastEvent() != null) {
            eventNameLbl.setText(p.getLordinfo().getLastEvent().getHeading());
            eventText.setText(p.getLordinfo().getLastEvent().getText());
            dealWithEvent(p, p.getLordinfo().getLastEvent());
            p.getLordinfo().setLastEvent(null);
        } else {
            eventNameLbl.setVisible(false);
            eventText.setVisible(false);
        }

        for(War w : Game.getRunGame().getCurrentTurn().getLordinfo().getWars()) {
            Label label = new Label(w.getAttackTarget().getName() + " - " + w.getDefenseTarget().getName() + " war");
            Label label2 = new Label(" war of " + w.getWarTypeString());
            charCountiesController.style(label);
            warBox.getChildren().addAll(label2);
        }
    }
}
