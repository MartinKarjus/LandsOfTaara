package menuCounty;

import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import world.County;
import world.Person;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin Karjus 1 on 17/04/2017.
 */
public class countyController implements Initializable {
    // TODO make buttons invisible for person who doesnt own county
    @FXML
    private Label occupierLabel;

    @FXML
    private Circle occupierCircle;

    @FXML
    private Label income;

    @FXML
    private Label army;

    @FXML
    private Label garrison;

    @FXML
    private Button incomeUpgrade;

    @FXML
    private Button garrisonUpgrade;

    @FXML
    private Button armyUpgrade;

    @FXML
    private Label incomeLvl;

    @FXML
    private Label armyLvl;

    @FXML
    private Label garrisonLvl;

    @FXML
    private Circle circlePortrait;

    @FXML
    private Label countyName;

    @FXML
    private Button exitBtn;

    @FXML
    private Label ownerName;

    @FXML
    private Label ownerSurname;

    @FXML
    void escKeyPressed(KeyEvent event) {
        Game.getGame().closeSecondary();
    }

    @FXML
    void occupierClick(MouseEvent event) {
        if(Game.getGame().getClickedCounty().getOccupier() != null) {
            Game.getGame().setClickedPerson(Game.getGame().getClickedCounty().getOccupier());
            Game.getGame().startCharacterMenu();
        }
    }

    @FXML
    void lordClicked(MouseEvent event) {
        Game.getGame().setClickedPerson(Game.getGame().getClickedCounty().getOwner());
        Game.getGame().startCharacterMenu();
    }

    @FXML
    void upgradeArmy(ActionEvent event) {
        if(clicked.getOwner() == Game.getRunGame().getCurrentTurn()) {
            setLvlLbls();
            clicked.upgrade("troop", owner.getLordinfo());
        }
    }

    @FXML
    void upgradeGarrison(ActionEvent event) {
        if(clicked.getOwner() == Game.getRunGame().getCurrentTurn()) {
            setLvlLbls();
            clicked.upgrade("def", owner.getLordinfo());
        }
    }

    @FXML
    void upgradeIncome(ActionEvent event) {
        if(clicked.getOwner() == Game.getRunGame().getCurrentTurn()) {
            setLvlLbls();
            clicked.upgrade("income", owner.getLordinfo());
        }

    }

    @FXML
    void exitMenu(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();;
    }

    private void setLvlLbls() {
        armyLvl.setText(String.valueOf(clicked.getTroopLevel()));
        incomeLvl.setText(String.valueOf(clicked.getIncomeLevel()));
        garrisonLvl.setText(String.valueOf(clicked.getDefLevel()));
        income.setText(String.valueOf(clicked.getIncome()));
    }
    private void refreshTips() {
        garUp.setText(
                "\nUpgrading garrison increases reinforcement of garrison by 25 and max size by 1000.\n" +
                        "\nBase reinforcement is 25 and base garrison is 1000.\n" +
                        "\nCost to upgrade: " + String.valueOf(clicked.getDefLevel()*100) + ".\n"
        );



        troopUp.setText(
                "\nUpgrading garrison increases reinforcement of troops by 25 and max size by 1000.\n" +
                        "\nBase reinforcement is 25 and base garrison is 1000.\n" +
                        "\nCost to upgrade: " + String.valueOf(clicked.getTroopLevel()*100) + ".\n"
        );


        incomeUp.setText(
                "\nUpgrading garrison increases gold income by 5.\n" +
                        "\nBase reinforcement is 5.\n" +
                        "\nCost to upgrade: " + String.valueOf(clicked.getIncomeLevel()*100) + ".\n"
        );
    }

    private County clicked;
    private Person owner;
    private Tooltip garUp;
    private Tooltip troopUp;
    private Tooltip incomeUp;

    private void hideNotOwner() {
        if(clicked.getOwner() != Game.getRunGame().getCurrentTurn()) {
            armyUpgrade.setVisible(false);
            garrisonUpgrade.setVisible(false);
            incomeUpgrade.setVisible(false);
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        clicked = Game.getGame().getClickedCounty();
        owner = clicked.getOwner();

        setLvlLbls();

        garUp = new Tooltip();
        troopUp = new Tooltip();
        incomeUp = new Tooltip();
        Tooltip.install(garrisonUpgrade, garUp);
        Tooltip.install(armyUpgrade, troopUp);
        Tooltip.install(incomeUpgrade, incomeUp);

        refreshTips();



        String por = owner.getPortrait();
        garrison.setText(String.valueOf(clicked.getDef()));
        army.setText(String.valueOf(clicked.getTroops()));
        countyName.setText(String.valueOf(clicked.getName()));
        ownerSurname.setText(owner.getSurname());
        if(owner.getGender().equals("F")) {
            ownerName.setText("Lady " + owner.getName());
        } else {
            ownerName.setText("Lord " + owner.getName());
        }

        Image image = new Image("portraits/" + por);
        circlePortrait.setFill(new ImagePattern(image));

        if(clicked.getOccupier() != null) {
            image = new Image("portraits/" + clicked.getOccupier().getPortrait());
            occupierCircle.setFill(new ImagePattern(image));
        } else {
            occupierCircle.setVisible(false);
            occupierLabel.setVisible(false);
        }
        hideNotOwner();

    }
}
