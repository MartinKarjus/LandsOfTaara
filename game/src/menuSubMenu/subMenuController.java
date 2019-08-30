package menuSubMenu;

import inGame.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import world.World;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin Karjus 1 on 17/04/2017.
 */
public class subMenuController implements Initializable {


    private static GridPane base;

    public static GridPane getBase() {
        return base;
    }

    public static void setBase(GridPane base) {
        subMenuController.base = base;
    }

    @FXML
    private GridPane basePane;

    @FXML
    private Circle personCircle;

    @FXML
    private Circle plotCircle;

    @FXML
    private Circle warCircle;

    @FXML
    private Circle lawCircle;

    @FXML
    void lawClick(MouseEvent event) {
        Game.getGame().closeSecondary();
        Stage listStage = Game.getGame().getLawScreen();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuLaws/laws.fxml"));
            listStage.setScene(new Scene(root));
            //listStage.setX(200);
            //listStage.setY(100);
            listStage.show();
        } catch (java.io.IOException error) {
            System.out.println("Failed to open law menu: " + error);
        }
    }


    @FXML
    void personClick(MouseEvent event) {
        Game.getGame().closeSecondary();
        Game.getGame().setClickedPerson(Game.getRunGame().getCurrentTurn());
        Game.getGame().startCharacterMenu();
    }

    @FXML
    private Label moneyLbl;

    @FXML
    void plotClick(MouseEvent event) {

        Game.getGame().closeSecondary();
        Stage listStage = Game.getGame().getPlotScreen();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuPlots/plot.fxml"));
            listStage.setScene(new Scene(root));
            //listStage.setX(200);
            //listStage.setY(100);
            listStage.show();
        } catch (java.io.IOException error) {
            System.out.println("Failed to open help menu: " + error);
        }

    }

    @FXML
    void warClick(MouseEvent event) {
        if(Game.getRunGame().getCurrentTurn().getLordinfo().isArmyRaised() &&
                Game.getRunGame().getCurrentTurn().getLordinfo().getWars().size() == 0) {
            Game.getRunGame().getCurrentTurn().getLordinfo().lowerArmy();
            System.out.println("Lowring army for: " + Game.getRunGame().getCurrentTurn().getName() + " of: "
            + Game.getRunGame().getCurrentTurn().getLordinfo().getCounties().get(0).getName());
            // done in lower army
            //Game.getGame().setMovables();
        } else if (!Game.getRunGame().getCurrentTurn().getLordinfo().isArmyRaised()) {
            Game.getRunGame().getCurrentTurn().getLordinfo().raiseArmy();
            System.out.println("raising army");
            // done in raise army
            //Game.getGame().setMovables();
        }
        Game.getGame().closeSecondary();
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        setBase(basePane);

        Tooltip moneyTip = new Tooltip();

        Tooltip.install(moneyLbl, moneyTip);

        Timeline checkMove = new Timeline();
        checkMove.getKeyFrames().add(new KeyFrame(Duration.millis(1000), event -> {
            if(Game.getRunGame().getCurrentTurn() != null) {
                if(Game.getRunGame().getPlayers().contains(Game.getRunGame().getCurrentTurn())) {
                    moneyLbl.setText(String.valueOf(Game.getRunGame().getCurrentTurn().getLordinfo().getCash()));
                    String income = String.valueOf(Game.getRunGame().getCurrentTurn().getLordinfo().getIncome());
                    moneyTip.setText(
                            "\nThe ammount of treasure you have.\n" +
                                    "\nYour income is: " + income + ".\n"
                    );
                }
            }
        }));
        checkMove.setCycleCount(Timeline.INDEFINITE);
        checkMove.play();

        Image image = new Image("menuSubMenu/law.png");
        lawCircle.setFill(new ImagePattern(image));
        image = new Image("menuSubMenu/intrigue.png");
        plotCircle.setFill(new ImagePattern(image));
        image = new Image("menuSubMenu/army.png");
        warCircle.setFill(new ImagePattern(image));
        image = new Image("menuSubMenu/person1.png");
        personCircle.setFill(new ImagePattern(image));

        Tooltip wartip = new Tooltip();
        wartip.setText(
                "\nClick to raise and send them home\n" +
                        "You lose 10% of your troops each time you send them home\n"
        );

        Tooltip plotTip = new Tooltip();
        plotTip.setText(
                "\nThis is the help screen.\n\nClick here to open the manual.\n"
        );
        Tooltip charTip = new Tooltip();
        charTip.setText(
                "\nClick to enter your own character's screen.\n"
        );
        Tooltip lawTip = new Tooltip();
        lawTip.setText(
                "\nThis is the law screen.\n" +
                        "\nSee the manual entry law for more information about different laws.\n" +
                        "\nCurrently inheritance law is by default agnatic cognatic.\n"
        );
        Tooltip.install(warCircle, wartip);
        Tooltip.install(plotCircle, plotTip);
        Tooltip.install(lawCircle, lawTip);
        Tooltip.install(personCircle, charTip);
    }
}
