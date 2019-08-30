package menuDiplomacy;

import game.RunGame;
import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import world.County;
import world.LordInfo;
import world.Person;
import world.Traits;
import world.War;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 17/04/2017.
 */
public class diploController implements Initializable {

    public static final int MURDER_CHANCE = 30;
    public static final int MURDER_COST = 150;

    @FXML
    private ScrollPane paneLeft;

    @FXML
    private Label relationLbl;

    @FXML
    private Circle targetCircle;

    @FXML
    private Circle selfCircle;

    @FXML
    private Button marryBtn;

    @FXML
    private Button sendRequestBtn;

    @FXML
    private Button warBtn;

    @FXML
    private Button murderBtn;

    @FXML
    private Label requestLbl;

    @FXML
    private Label offerLbl;

    @FXML
    private Circle marryOwnCircle;

    @FXML
    private Circle marryToCircle;

    @FXML
    private Label countyName;

    @FXML
    private Button confirmBtn;

    @FXML
    private ScrollPane paneRight;

    @FXML
    void closeButtonPress(ActionEvent event) {
        Game.getGame().closeDiplo();
    }

    @FXML
    void confirm(ActionEvent event) {
        if(clicked.equals("murder")) {
            Random r = new Random();
            if(!target.getAlive()) {
                resetAll();
                countyName.setText("Target dead.");
                countyName.setVisible(true);
            } else if(Game.getRunGame().getCurrentTurn().getLordinfo().getCash() >= MURDER_COST) {
                Game.getRunGame().getCurrentTurn().getLordinfo().addCash(-MURDER_COST);
                if (r.nextInt(100) < MURDER_CHANCE) {
                    murderTarget.death();
                    resetAll();
                    countyName.setText("Target killed.");
                    countyName.setVisible(true);
                } else {
                    resetAll();
                    countyName.setText("Plot failed.");
                    countyName.setVisible(true);
                }
            } else {
                resetAll();
                countyName.setText("Not enough gold.");
                countyName.setVisible(true);
            }
        } else if (clicked.equals("war")) {
            if(Game.getRunGame().getCurrentTurn().getLordinfo().getWars().size() > 0) {
                resetAll();
                countyName.setText("Already in war.");
                countyName.setVisible(true);
            } else if(Game.getRunGame().getCurrentTurn().getLordinfo().getAlliances().contains(target.getLordinfo())) {
                resetAll();
                countyName.setText("Don't attack allies...");
                countyName.setVisible(true);
            } else if(warType.equals("Independance") && (Game.getRunGame().getCurrentTurn().getLordinfo().getVassalTo() == null)) {
                resetAll();
                countyName.setText("Invalid war.");
                countyName.setVisible(true);
            } else if(Game.getRunGame().getCurrentTurn().getLordinfo().getVassalTo() != null) {
                if(warType.equals("Independance") && target.getLordinfo() == Game.getRunGame().getCurrentTurn().getLordinfo().getVassalTo()) {
                    Game.getWorld().addWar(new War(Game.getRunGame().getCurrentTurn().getLordinfo(),
                            target.getLordinfo(),
                            attackTarget,
                            Game.getRunGame().getCurrentTurn().getLordinfo().getCounties().get(0),
                            warType));
                    resetAll();
                    countyName.setText("Good luck.");
                    countyName.setVisible(true);
                } else {
                    resetAll();
                    countyName.setText("You are a vassal.");
                    countyName.setVisible(true);
                }
            } else if(target == Game.getRunGame().getCurrentTurn()) {
                resetAll();
                countyName.setText("Don't do that..");
                countyName.setVisible(true);
            } else {
                Game.getWorld().addWar(new War(Game.getRunGame().getCurrentTurn().getLordinfo(),
                        target.getLordinfo(),
                        attackTarget,
                        Game.getRunGame().getCurrentTurn().getLordinfo().getCounties().get(0),
                        warType));
                resetAll();
                countyName.setText("Good luck.");
                countyName.setVisible(true);
            }
        }
    }

    @FXML
    void escPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            Game.getGame().closeDiplo();
        }
    }

    @FXML
    void marryDisplay(ActionEvent event) {
        resetAll();
        populateMarry(left, Game.getRunGame().getCurrentTurn());
        populateMarry(right, target);
        sendRequestBtn.setVisible(true);
        requestLbl.setVisible(true);
        offerLbl.setVisible(true);
    }

    @FXML
    void murderDisplay(ActionEvent event) {
        resetAll();
        clicked = "murder";
        populateMurder(left, target);
        offerLbl.setText("Target");
        confirmBtn.setVisible(true);
        offerLbl.setVisible(true);
    }

    @FXML
    void sendRequest(ActionEvent event) {
        if(leftPerson != null && rightPerson != null) {
            if (leftPerson.getGender().equals(rightPerson.getGender())) {
                resetAll();
                countyName.setText("Invalid match.");
                countyName.setVisible(true);
            } else if(!leftPerson.getAlive() || !rightPerson.getAlive()) {
                resetAll();
                countyName.setText("Dead ppl cant marry.");
                countyName.setVisible(true);
            } else if(target.getLordinfo().willMarry(Game.getRunGame().getCurrentTurn().getLordinfo())) {
                resetAll();
                countyName.setText("Accepted.");
                countyName.setVisible(true);
                leftPerson.getRelations().setConsort(rightPerson);
                rightPerson.getRelations().setConsort(leftPerson);

            } else {
                resetAll();
                countyName.setText("Rejected.");
                countyName.setVisible(true);
            }
        } else {
            resetAll();
            countyName.setText("Invalid match.");
            countyName.setVisible(true);
        }
    }




    @FXML
    void warDisplay(ActionEvent event) {
        resetAll();
        clicked = "war";
        populateWar(target);
        confirmBtn.setVisible(true);
        countyName.setVisible(true);
        offerLbl.setText("Force vassal");
        warType = "Force vassalization";
        offerLbl.setVisible(true);

    }

    private void populateWar(Person who) {
        for(County c : who.getLordinfo().getCounties()) {
            countyName.setText(c.getName());
            attackTarget = c;
            Button b = new Button(c.getName());

            right.getChildren().add(b);

            b.setOnMouseClicked(event -> {
                countyName.setText(c.getName());
                attackTarget = c;
            });
        }
        for(String s : Game.getWorld().getWarTypes()) {
            if(s.equals("Defense")) {
                continue;
            }
            Button b = new Button(s);

            left.getChildren().add(b);

            b.setOnMouseClicked(event -> {
                warType = s;
                if(s.equals("Force vassalization")) {
                    offerLbl.setText("Force vassal");
                } else {
                    offerLbl.setText(s);
                }
            });
        }
    }


    private void populateMurder(VBox box, Person who) {
        Set<Person> temp = who.getRelations().getClose();
        if(target.getAlive()) {
            temp.add(target);
        }
        for(Person y : temp) {
            Circle circ = new Circle(25);
            setFill(y, circ);
            if(!marryOwnCircle.isVisible()) {
                setFill(y, marryOwnCircle);
                marryOwnCircle.setVisible(true);
                murderTarget = y;
            }

            box.getChildren().add(circ);
            setPersonTip(circ, y);

            circ.setOnMouseClicked(event -> {
                murderTarget = y;
                setFill(y, marryOwnCircle);
            });
        }
    }

    private void populateMarry(VBox box, Person who) {
        for(Person y : who.getRelations().getCloseUnmarriedLandless()) {
            if(box == left && !marryOwnCircle.isVisible()) {
                setCircle(y, marryOwnCircle);
                marryOwnCircle.setVisible(true);
                leftPerson = y;
                Game.getGame().setClickedPerson(y);
            } else if (box == right && !marryToCircle.isVisible()) {
                rightPerson = y;
                setCircle(y, marryToCircle);
                marryToCircle.setVisible(true);
                Game.getGame().setClickedPerson(y);
            }
            Circle circ = new Circle(25);
            setFill(y, circ);

            box.getChildren().add(circ);
            setPersonTip(circ, y);
            if(box == left) {
                circ.setOnMouseClicked(event -> {
                    leftPerson = y;
                });
            } else {
                circ.setOnMouseClicked(event -> {
                    rightPerson = y;
                });
            }
        }
    }




    private void setTip(Circle toTip, String tip) {
        Tooltip temp = new Tooltip();
        temp.setText(tip);
        Tooltip.install(toTip, temp);
    }

    private void setPersonTip(Circle personCircle, Person person) {

        String temp = "";
        temp = temp + "\nName: " + person.getName() + " " + person.getSurname() + "\n";
        temp = temp + "\nAge: " + String.valueOf(person.getAge(Game.getWorld().getCurrentDate())) + "\n";
        if(person.getLordinfo() != null) {
            temp = temp + "\nOwner of:\n";
            for(County c : person.getLordinfo().getCounties()) {
                temp = temp + "\n   " + c.getName() + " maakond\n";
            }
        }
        if(person.getRelations().getFather() != null) {
            temp = temp + "\nFather: " + person.getRelations().getFather().getName() + " " +
                    person.getRelations().getFather().getSurname() + "\n";
        }
        if(person.getRelations().getMother() != null) {
            temp = temp + "\nMother: " + person.getRelations().getMother().getName() + " " +
                    person.getRelations().getMother().getSurname() + "\n";
        }
        setTip(personCircle, temp);
    }


    private void setCircle(Person who, Circle to) {

        setFill(who, to);
        setPersonTip(to, who);

        to.setOnMouseClicked(event -> {
            Game.getGame().setClickedPerson(who);
            Game.getGame().startCharacterMenu();
        });
    }

    private void setFill(Person who, Circle to) {
        if(!who.getAlive()) {
            Image image1 = new Image("portraits/dead.png");
            to.setFill(new ImagePattern(image1));
        } else {
            Image image2 = new Image("portraits/" + who.getPortrait());
            to.setFill(new ImagePattern(image2));
        }
    }

    private void setStart() {
        setCircle(Game.getRunGame().getCurrentTurn(), selfCircle);
        setCircle(target, targetCircle);
    }

    private void resetAll() {
        setStart();
        left.getChildren().clear();
        right.getChildren().clear();
        relationLbl.setVisible(true);
        relationLbl.setText(String.valueOf(target.getLordinfo().getOpinion(Game.getRunGame().getCurrentTurn().getLordinfo())));
        clicked = "";
        warType = "";

        if(target.getLordinfo() == null) {
            marryBtn.setVisible(false);
            warBtn.setVisible(false);
        } else {
            marryBtn.setVisible(true);
            warBtn.setVisible(true);
        }
        confirmBtn.setVisible(false);
        countyName.setVisible(false);
        sendRequestBtn.setVisible(false);

        marryOwnCircle.setVisible(false);
        marryToCircle.setVisible(false);
        requestLbl.setVisible(true);
        requestLbl.setVisible(false);
        offerLbl.setText("Offer");
        offerLbl.setVisible(true);
        offerLbl.setVisible(false);
    }

    private Person leftPerson;
    private Person rightPerson;
    private VBox left;
    private VBox right;
    private Person target;
    private String clicked;
    private Person murderTarget;
    private County attackTarget;
    private String warType;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        left = new VBox();
        right = new VBox();
        paneLeft.setContent(left);
        paneRight.setContent(right);

        target = Game.getGame().getRightClickedPerson();


        resetAll();


    }
}











