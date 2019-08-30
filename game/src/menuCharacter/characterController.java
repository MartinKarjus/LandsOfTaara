package menuCharacter;

import com.sun.javafx.css.Style;
import inGame.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 17/04/2017.
 */
public class characterController implements Initializable {

    static final int PORTRAIT_RADIUS = 20;
    static final int TRAIT_RADIUS = 15;

    private Relations relations;
    private Person mainPerson;

    private void close() {
        Game.getGame().closeSecondary();
    }
    private void open(Person openedFor) {
        Game.getGame().setClickedPerson(openedFor);
        Game.getGame().startCharacterMenu();
    }

    @FXML
    private VBox backGroundVBox;

    @FXML
    private Label parentsLabel;

    @FXML
    private Circle personSelf;

    @FXML
    private Circle consort;

    @FXML
    private Circle lord;

    @FXML
    private Button exitBtn;

    @FXML
    private ScrollPane traitsPane;

    @FXML
    private Circle fatherFather;

    @FXML
    private Circle fatherMother;

    @FXML
    private Circle motherFather;

    @FXML
    private Circle motherMother;

    @FXML
    private Circle father;

    @FXML
    private Circle mother;

    @FXML
    private ScrollPane childrenPane;

    @FXML
    private ScrollPane siblingsPane;

    @FXML
    private ScrollPane alliancesPane;


    @FXML
    void escPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            close();
        }
    }

    @FXML
    void exitScreen(ActionEvent event) {
        close();
    }

    private void testPopulate(HBox populate, int radius) {
        for(int x = 0; x < 3; x++) {
            populateList(populate, Game.getWorld().getLords(), radius);
        }
    }

    private void populate(HBox populateTo, Set<Person> set, int radius) {
        for(Person y : set) {
            Circle circ = new Circle(radius);
            setFill(y, circ);

            populateTo.getChildren().add(circ);

            circ.setOnMouseClicked(event -> {
                open(y);
            });
        }
    }

    private void populateAllies(HBox populateTo, Set<LordInfo> set, int radius) {
        for(LordInfo y : set) {
            Circle circ = new Circle(radius);
            setFill(y, circ);

            populateTo.getChildren().add(circ);

            circ.setOnMouseClicked(event -> {
                open(y.getSelf());
            });
        }
    }

    private void populateList(HBox populateTo, List<Person> list, int radius) {
        Set<Person> set = new HashSet<>();
        set.addAll(list);
        populate(populateTo, set, radius);
    }

    private void setTip(Circle toTip, String tip) {
        Tooltip temp = new Tooltip();
        temp.setText(tip);
        Tooltip.install(toTip, temp);
    }

    private void setPersonTip(Circle personCircle, Person person) {

        String temp = "";
        temp = temp + "\nName: " + person.getName() + " " + person.getSurname() + "\n";
        if(person.getAlive()) {
            temp = temp + "\nAge: " + String.valueOf(person.getAge(Game.getWorld().getCurrentDate())) + "\n";
            if(person.getLordinfo() != null) {
                temp = temp + "\nOwner of:\n";
                for(County c : person.getLordinfo().getCounties()) {
                    temp = temp + "\n   " + c.getName() + " maakond\n";
                }
            }
        } else {
            if(person.getDeathdate() != null) {
                temp = temp + "\nDeath date: " + person.getDeathdate().getYear() + " " + person.getDeathdate().getSeasonFullName() + "\n";
            }
        }
        setTip(personCircle, temp);

    }

    private void setTraitTip(Circle circ, Traits trait) {

        String temp = "";
        temp = temp + "\n" + trait.getName() + "\n";
        if(trait.getAngry() != 0) {
            temp = temp + "\nAggressiveness: +" + String.valueOf(trait.getAngry()) + "\n";
        }
        if(trait.getTroopB() != 0) {
            temp = temp + "\nBonus troop gain(%)): +" + String.valueOf(trait.getTroopB()) + "\n";
            temp = temp + "\n   Counties still won't go over their maximum limit.\n";
        }
        if(trait.getGoodEventB() != 0) {
            temp = temp + "\nLuck: +" + String.valueOf(trait.getGoodEventB()) + "\n";
        }
        if(trait.getWarB() != 0) {
            temp = temp + "\nWar skill: +" + String.valueOf(trait.getWarB()) + "\n";
        }
        /*
        private String opposing;
        private String icon;
        private YearSeason expireDate;
        */
        if(trait.getLust() != 0) {
            temp = temp + "\nLust: +" + String.valueOf(trait.getLust()) + "\n";
        }
        if(trait.getAmbition() != 0) {
            temp = temp + "\nAmbition: +" + String.valueOf(trait.getAmbition()) + "\n";
        }
        if(trait.getGood() != 0) {
            temp = temp + "\nJust and good natured: +" + String.valueOf(trait.getGood()) + "\n";
        }
        if(!trait.getOpposing().equals("")) {
            temp = temp + "\nOpposing trait: " + trait.getOpposing() + "\n";
        }
        if(trait.getExpireDate() != null) {
            temp = temp + "\nExpires at: " + trait.getExpireDate().getYear() + " " + trait.getExpireDate().getSeasonFullName() + "\n";
        }
        setTip(circ, temp);
    }

    private void populateTraits(HBox populateTo, List<Traits> list, int radius) {
        for(Traits y : list) {
            if(y.getVisible()) {
                Circle circ = new Circle(radius);
                Image image = new Image("traitsImgs/" + y.getIcon());
                circ.setFill(new ImagePattern(image));
                populateTo.getChildren().add(circ);
                setTraitTip(circ, y);
            }
        }
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

    private void setFill(LordInfo who, Circle to) {
        if(!who.getSelf().getAlive()) {
            Image image1 = new Image("portraits/dead.png");
            to.setFill(new ImagePattern(image1));
        } else {
            Image image2 = new Image("portraits/" + who.getSelf().getPortrait());
            to.setFill(new ImagePattern(image2));
        }
    }

    private void setCircle(Person who, Circle to) {

        setFill(who, to);
        setPersonTip(to, who);

        if (!who.equals(mainPerson)) {
            to.setOnMouseClicked(event -> {
                open(who);
            });
        } else if (mainPerson.getLordinfo() != null) {
            Stage listStage = Game.getGame().getCountyList();

            to.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/menuCharacter/charCounties.fxml"));
                        listStage.setScene(new Scene(root));
                        listStage.setX(200);
                        listStage.setY(100);
                        listStage.show();
                    } catch (java.io.IOException error) {
                        System.out.println("Failed to open county list: " + error);
                    }
                }
                else if (event.getButton().equals(MouseButton.SECONDARY)) {
                    Game.getGame().setRightClickedPerson(mainPerson);
                    Game.getGame().startDiploScreen();
                }
            });
        }
    }

    private void setSeen() {
        setCircle(mainPerson, personSelf);

        if (relations.getFather() != null) {
            setCircle(relations.getFather(), father);
            if (relations.getFather().getRelations().getFather() != null) {
                setCircle(relations.getFather().getRelations().getFather(), fatherFather);
            } else {
                fatherFather.setVisible(false);
            }
            if (relations.getFather().getRelations().getMother() != null) {
                setCircle(relations.getFather().getRelations().getMother(), fatherMother);
            } else {
                fatherMother.setVisible(false);
            }
        } else {
            fatherFather.setVisible(false);
            fatherMother.setVisible(false);
            father.setVisible(false);
        }
        if (relations.getMother() != null) {
            setCircle(relations.getMother(), mother);
            if (relations.getMother().getRelations().getFather() != null) {
                setCircle(relations.getMother().getRelations().getFather(), motherFather);
            } else {
                motherFather.setVisible(false);
            }
            if (relations.getMother().getRelations().getMother() != null) {
                setCircle(relations.getMother().getRelations().getMother(), motherMother);
            } else {
                motherMother.setVisible(false);
            }
        } else {
            mother.setVisible(false);
            motherMother.setVisible(false);
            motherFather.setVisible(false);
        }
        if (relations.getConsort() != null) {
            setCircle(relations.getConsort(), consort);
        } else {
            consort.setVisible(false);
        }
        if (mainPerson.getLordinfo() != null) {
            if (mainPerson.getLordinfo().getVassalTo() != null) {
                setCircle(mainPerson.getLordinfo().getVassalTo().getSelf(), lord);
            } else {
                lord.setVisible(false);
            }
        } else {
            lord.setVisible(false);
        }
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //System.out.println("In init character");
        relations = Game.getGame().getClickedPerson().getRelations();
        mainPerson = Game.getGame().getClickedPerson();


        setSeen();
        HBox traits = new HBox();
        HBox children = new HBox();
        HBox siblings = new HBox();
        HBox allies = new HBox();

        /*
        testPopulate(children, PORTRAIT_RADIUS);
        testPopulate(siblings, PORTRAIT_RADIUS);
        testPopulate(allies, PORTRAIT_RADIUS);
        testPopulate(traits, TRAIT_RADIUS);
        */

        populate(children, relations.getChildren(), PORTRAIT_RADIUS);
        populate(siblings, relations.getBrothers(), PORTRAIT_RADIUS);
        populate(siblings, relations.getSisters(), PORTRAIT_RADIUS);
        if (mainPerson.getLordinfo() != null) {
            populateAllies(allies, mainPerson.getLordinfo().getAlliances(), PORTRAIT_RADIUS);
        }
        populateTraits(traits, mainPerson.getTraits(), TRAIT_RADIUS);

        childrenPane.setContent(children);
        alliancesPane.setContent(allies);
        traitsPane.setContent(traits);
        siblingsPane.setContent(siblings);

    }
}




















