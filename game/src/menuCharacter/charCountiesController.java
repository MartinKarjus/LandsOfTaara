package menuCharacter;

import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import world.County;
import world.LordInfo;
import world.Person;
import world.Relations;
import world.Traits;
import world.War;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 18/04/2017.
 */
public class charCountiesController implements Initializable {

    @FXML
    private ScrollPane listPane;

    @FXML
    private ScrollPane warList;

    @FXML
    private ScrollPane occupyList;

    @FXML
    private ScrollPane vassalList;

    @FXML
    private Button exitBtn;

    @FXML
    void exit(ActionEvent event) {
        Game.getGame().getCountyList().close();
    }

    @FXML
    void escPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            Game.getGame().getCountyList().close();
        }
    }

    public static void style(Label label) {
        label.setStyle("-fx-text-fill: red;" +
                "-fx-font-family: \"Bell MT\";" +
                "-fx-font-size: 20px;");
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //System.out.println("In init char counties");
        VBox counties = new VBox(10);
        VBox wars = new VBox(10);
        VBox occupations = new VBox(10);
        VBox vassals = new VBox(10);

        for(LordInfo l : Game.getGame().getClickedPerson().getLordinfo().getVassals()) {
            Label label = new Label(l.getSelf().getName() + "-" + l.getCounties().get(0).getName());
            style(label);
            vassals.getChildren().add(label);
        }
        for(County c : Game.getWorld().getCounties().values()) {
            if(c.getOccupier() == Game.getRunGame().getCurrentTurn()) {
                Label label = new Label(c.getName() + " maakond");
                style(label);
                occupations.getChildren().add(label);
            }
        }
        for(County c : Game.getGame().getClickedPerson().getLordinfo().getCounties()) {
            Label label = new Label(c.getName() + " maakond");
            style(label);
            counties.getChildren().add(label);
        }
        for(War w : Game.getGame().getClickedPerson().getLordinfo().getWars()) {
            Label label = new Label(w.getAttackTarget().getName() + " - " + w.getDefenseTarget().getName() + " war");
            Label label2 = new Label("  war of " + w.getWarTypeString());
            style(label);
            wars.getChildren().addAll(label, label2);
        }

        warList.setContent(wars);
        listPane.setContent(counties);
        occupyList.setContent(occupations);
        vassalList.setContent(vassals);


    }
}
