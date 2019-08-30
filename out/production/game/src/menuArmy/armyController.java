package menuArmy;

import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import world.Traits;
import world.Unit;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Martin Karjus 1 on 19/04/2017.
 */
public class armyController implements Initializable {

    private Unit clicked;

    @FXML
    private Label inf;

    @FXML
    private Label heavyInf;

    @FXML
    private Label cav;

    @FXML
    private Label archer;

    @FXML
    private Label special;

    @FXML
    private Label total;

    @FXML
    private Circle ownerCircle;

    @FXML
    private Button escBtn;

    @FXML
    private Label countyTo;

    @FXML
    private Label countyAt;

    @FXML
    void escButton(ActionEvent event) {
        Game.getGame().closeSecondary();
    }

    @FXML
    void escPress(KeyEvent event) {
        Game.getGame().closeSecondary();
    }

    @FXML
    void ownerClicked(MouseEvent event) {
        Game.getGame().setClickedPerson(clicked.getOwner());
        Game.getGame().startCharacterMenu();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        clicked = Game.getGame().getUnitClicked();
        countyAt.setText(clicked.getAt().getName());
        if(clicked.getMove() != null) {
            countyTo.setText(clicked.getMove().getName());
        } else {
            countyTo.setVisible(false);
        }
        total.setText(String.valueOf(clicked.getTotal()));
        inf.setText(String.valueOf(clicked.getInfantry()));
        heavyInf.setText(String.valueOf(clicked.getHeavyInfantry()));
        cav.setText(String.valueOf(clicked.getCavalry()));
        special.setText(String.valueOf(clicked.getSpecial()));
        archer.setText(String.valueOf(clicked.getArchers()));

        Image image = new Image("portraits/" + clicked.getOwner().getPortrait());
        ownerCircle.setFill(new ImagePattern(image));
    }
}
