package inGame;


import game.RunGame;
import game.Startup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import world.County;
import world.Person;
import world.Unit;
import world.World;

import java.util.Random;

/**
 * Created by Martin Karjus 1 on 17/04/2017... at 3 AM.. sigh..
 */
public class Game {

    /*
    //TODO should put the entire game into a rectangle so i would never have to refresh the county centers
    //TODO and would have to only do it at start
    //TODO + i could zoom so i could reduce the size of icons
    probably wont do it, lots of other things to do
     */
    private static World world;
    private static RunGame runGame;
    private County clickedCounty;
    private Person rightClickedPerson;
    private Stage countyStage = new Stage();
    private Stage subMenu = new Stage();
    private Stage stage = new Stage();
    private Stage charStage = new Stage();
    private Stage countyList = new Stage();
    private Stage plotScreen = new Stage();
    private Stage armyScreen = new Stage();
    private Stage inGameMenu = new Stage();
    private Stage diploMenu = new Stage();
    private Stage lawScreen = new Stage();
    private Stage summary = new Stage();
    private Person clickedPerson;
    private Unit unitClicked;
    private Pane stack = new Pane();
    private Pane stack2 = new Pane();
    private boolean selected = false;
    private BorderPane pane = new BorderPane();
    private Stage blockerStage = new Stage();

    public Stage getLawScreen() {
        return lawScreen;
    }

    public Stage getPlotScreen() {
        return plotScreen;
    }

    public Person getRightClickedPerson() {
        return rightClickedPerson;
    }

    public void setRightClickedPerson(Person rightClickedPerson) {
        this.rightClickedPerson = rightClickedPerson;
    }

    public Stage getInGameMenu() {
        return inGameMenu;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Unit getUnitClicked() {
        return unitClicked;
    }

    public void setUnitClicked(Unit unitClicked) {
        this.unitClicked = unitClicked;
    }

    public Stage getCountyList() {
        return countyList;
    }

    public Stage getMapStage() {
        return stage;
    }

    public static World getWorld() {
        return world;
    }

    public Person getClickedPerson() {
        return clickedPerson;
    }

    public void setClickedPerson(Person clickedPerson) {
        this.clickedPerson = clickedPerson;
    }

    public County getClickedCounty() {
        return clickedCounty;
    }

    public Stage getCountyStage() {
        return countyStage;
    }

    public Game(World world, RunGame runGame) {
        Game.world = world;
        Game.runGame = runGame;
    }

    /**
     * Probably wont be used anymore, instead of a seperate stage the submenu is added straight onto the primary stage.
     */
    private void startSubMenu() {
        subMenu.initOwner(stage);
        subMenu.initStyle(StageStyle.UNDECORATED);
        subMenu.setX(0);
        subMenu.setY(0);
        subMenu.setAlwaysOnTop(true);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuSubMenu/subMenu.fxml"));
            subMenu.setTitle("subMenu");
            subMenu.setScene(new Scene(root, 400, 50));
            subMenu.show();
        } catch (java.io.IOException error) {
            System.out.println("Failed to open submenu: " + error);
        }
    }

    public void saveGame() {
        // TODO save game
    }

    public void closeSecondary() {
        //selected = false;
        if(summary.isShowing()) {
            summary.close();
        }
        if(countyStage.isShowing()) {
            countyStage.close();
        }
        if(charStage.isShowing()) {
            charStage.close();
        }
        if(armyScreen.isShowing()) {
            armyScreen.close();
        }
        if(inGameMenu.isShowing()) {
            inGameMenu.close();
        }
        if(diploMenu.isShowing()) {
            diploMenu.close();
        }
        if(blockerStage.isShowing()) {
            blockerStage.close();
        }
        if(plotScreen.isShowing()) {
            plotScreen.close();
        }
        if(lawScreen.isShowing()) {
            lawScreen.close();
        }
    }

    public void startDiploScreen() {
        closeSecondary();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuDiplomacy/diploScreen.fxml"));
            diploMenu.setTitle("diplo");
            diploMenu.setScene(new Scene(root));

            diploMenu.show();

        } catch (java.io.IOException error) {
            System.out.println("Failed to open diplo screen: " + error);
        }
    }

    public void startArmyScreen() {
        closeSecondary();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuArmy/armyScreen.fxml"));
            armyScreen.setTitle("army");
            armyScreen.setX(0);
            armyScreen.setScene(new Scene(root, 400, 250));
            armyScreen.setY(stage.getHeight() - 250);

            armyScreen.show();

        } catch (java.io.IOException error) {
            System.out.println("Failed to open army screen: " + error);
        }
    }

    public void startCharacterMenu() {
        closeSecondary();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menuCharacter/character.fxml"));
            charStage.setTitle("character");
            charStage.setScene(new Scene(root, 500, 700));
            charStage.setX(0);
            charStage.setY(50);

            charStage.show();

        } catch (java.io.IOException error) {
            System.out.println("Failed to open char screen: " + error);
        }

    }

    private void setSubMenu(BorderPane pane) {
        try {
            GridPane page = (GridPane) FXMLLoader.load(getClass().getResource("/menuSubMenu/subMenu.fxml"));
            // ################## this part is useless
            // TODO ask why it bugs out without doing this(comment out to show)..
            Scene scene3 = new Scene(page);
            Stage cake = new Stage();
            cake.setScene(scene3);
            cake.show();
            cake.close();
            cake = null;
            scene3 = null;
            // #############
            pane.getChildren().add(page);
        } catch (java.io.IOException error) {
            System.out.println("error: " + error);
        }
    }

    public void setStages() {
        countyList.setTitle("county list");
        countyList.setAlwaysOnTop(true);
        countyList.initOwner(stage);
        countyList.initModality(Modality.APPLICATION_MODAL);
        countyList.initStyle(StageStyle.UNDECORATED);
        //stack.setMouseTransparent(true);
        //stack2.setMouseTransparent(true);
        stack.setPickOnBounds(false);
        stack2.setPickOnBounds(false);

        plotScreen.initOwner(stage);
        plotScreen.initStyle(StageStyle.UNDECORATED);
        plotScreen.initModality(Modality.APPLICATION_MODAL);

        summary.initOwner(stage);
        summary.initStyle(StageStyle.UNDECORATED);
        summary.initModality(Modality.APPLICATION_MODAL);
        
        charStage.initOwner(stage);
        charStage.initStyle(StageStyle.UNDECORATED);

        armyScreen.initOwner(stage);
        armyScreen.initStyle(StageStyle.UNDECORATED);

        countyStage.initOwner(stage);
        //countyStage.initModality(Modality.APPLICATION_MODAL);
        countyStage.initStyle(StageStyle.UNDECORATED);
        //countyStage.setAlwaysOnTop(true);

        inGameMenu.initOwner(stage);
        inGameMenu.initModality(Modality.APPLICATION_MODAL);
        inGameMenu.initStyle(StageStyle.UNDECORATED);

        diploMenu.initOwner(stage);
        diploMenu.initModality(Modality.APPLICATION_MODAL);
        diploMenu.initStyle(StageStyle.UNDECORATED);

        lawScreen.initOwner(stage);
        lawScreen.initModality(Modality.APPLICATION_MODAL);
        lawScreen.initStyle(StageStyle.UNDECORATED);

        StackPane stackpane = new StackPane();
        stackpane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.5); -fx-background-radius: 10;");
        stackpane.setMinWidth(pane.getWidth());
        stackpane.setMinHeight(pane.getHeight());
        Scene blockScene = new Scene(stackpane);
        //blockerStage.setScene(new Scene(stackpane));
        blockerStage.setScene(blockScene);

        blockerStage.initOwner(stage);
        //blockerStage.setFullScreen(true);
        blockerStage.initModality(Modality.APPLICATION_MODAL);
        blockerStage.initStyle(StageStyle.UNDECORATED);

        stage.initOwner(Startup.getPrimaryStage());
    }

    /**
     * Refresh position of stuff(incase of resize).
     * @param pane2 Pane to get snapshot of.
     */
    public void refreshOwned(Pane pane2) {
        final WritableImage a = pane2.snapshot(null, null);
        final PixelReader pR = a.getPixelReader();
        Color color;
        String lastKnown = "";
        for(int x = 0; x < a.getWidth(); x+=5) {
            for(int y = 0; y < a.getHeight(); y+=5) {
                color = pR.getColor(x, y);
                if(world.getColorCounty().containsKey(color.toString())) {
                    lastKnown = color.toString();
                }

                if (color.toString().equals("0x00ff00ff")) {
                    if(!lastKnown.equals("")) {
                        if(world.getColorCounty().get(lastKnown).getCenterX() == -1) {
                            world.getColorCounty().get(lastKnown).setXY(x, y);
                        }
                    }
                }
            }
        }
    }

    public void closeDiplo() {
        if(diploMenu.isShowing()) {
            diploMenu.close();
        }
    }

    /**
     * Refresh all the changing movables.
     */
    public void setMovables() {

        stack.getChildren().clear();
        stack2.getChildren().clear();

        for (County c : world.getCounties().values()) {
            int x = c.getCenterX();
            int y = c.getCenterY();
            if(c.getOwner() == null) {
                System.out.println("Game: COUNTY HAS NO OWNER " + c.getName());
                return;
            }
            if(c.getOwner().getLordinfo() == null) {
                System.out.println("Game: COUNTY OWNER HAS NO LORD INFO " + c.getName());
                System.out.println("    owner name is: " + c.getOwner().getName());
                return;
            }
            if(c.getOwner().getLordinfo().getColor() == null) {
                System.out.println("Game: COUNTY OWNER HAS NO COLOR " + c.getName());
                return;
            }
            Color circCol = c.getOwner().getLordinfo().getColor();

            Circle circ = new Circle(15);
            Circle circ2 = new Circle(5);
            circ2.setFill(circCol);

            circCol = Color.web(circCol.toString(), 0.5);
            circ.setFill(circCol);

            Pane ownerColor = new Pane();
            ownerColor.setLayoutX(x);
            ownerColor.setLayoutY(y);
            ownerColor.getChildren().addAll(circ, circ2);

            stack.getChildren().add(ownerColor);

            int addX = 25;
            int addY = 5;

            for (Unit u : c.getUnitsAt()) {
                Text text = new Text(String.valueOf(u.getThousands()));
                text.setX(5);
                text.setY(20);
                text.setStyle("-fx-font-size: 20px;");
                text.setBoundsType(TextBoundsType.VISUAL);
                Pane unitPane = new Pane();
                unitPane.setLayoutX(x + addX);
                unitPane.setLayoutY(y + addY);

                Rectangle r = new Rectangle(20, 25);
                r.setFill(u.getColor());
                unitPane.getChildren().addAll(r, text);

                stack2.getChildren().add(unitPane);

                addX += 25;

                unitPane.setOnMouseClicked(event -> {
                    unitClicked = u;
                    selected = true;
                    startArmyScreen();
                });
            }
        }
    }

    public void blockScene() {
        if(!blockerStage.isShowing()) {
            blockerStage.show();
        }
    }



    public void autoRefresh() {

        Task task = new Task<Void>() {
          @Override
          public Void call() throws Exception {
            while (true) {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setMovables();
                }
              });
              Thread.sleep(1000);
            }
          }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();


    }

    public void buildUI() {
        /*
        for(Person l : world.getLords()) {
            Unit u = new Unit(l, 1000, 1000, 500, 1000, 1000, l.getLordinfo().getCounties().get(0));
            world.addUnit(u);
        }
        for(Person l : world.getLords()) {
            Unit u = new Unit(l, 1000, 1000, 500, 1000, 1000, l.getLordinfo().getCounties().get(0));
            world.addUnit(u);
        }*/

        setStages();
        Startup.getPrimaryStage().setFullScreen(true);
        Startup.getPrimaryStage().close();
        stage.requestFocus();
        stage.setFullScreenExitHint("");
        //TODO this should be removed afterwards
        //TODO figure out what "this" is
        //TODO update 2 months later - I Think that "This" might be the keycombo qwe that exists fullscreen
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("qwe"));

        stage.setFullScreen(true);


        Pane paney = new Pane();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        // ######################## BACKGROUND IMAGE AND SEEN IMAGE ###########################
        Image image = new Image("inGame/seen3.png", stage.getWidth(), stage.getHeight(), false, false);
        ImageView img = new ImageView(image);

        //Image image2 = new Image("inGame/clicked3.png", stage.getWidth(), stage.getHeight(), false, false);
        Image image2 = new Image("inGame/readPositions.png", stage.getWidth(), stage.getHeight(), false, false);
        ImageView img2 = new ImageView(image2);

        img.fitWidthProperty().bind(pane.widthProperty());
        img.fitHeightProperty().bind(pane.heightProperty());
        img2.fitWidthProperty().bind(pane.widthProperty());
        img2.fitHeightProperty().bind(pane.heightProperty());

        paney.getChildren().addAll(img);
        pane.setCenter(paney);
        pane.setLeft(img2);

        BorderPane pane2 = new BorderPane();
        Pane paney2 = new Pane();
        paney2.getChildren().addAll(img2);
        pane2.setCenter(paney2);

        setSubMenu(pane);


        paney.getChildren().addAll(stack, stack2);
        refreshOwned(pane2);
        //refresh();
        setMovables();

        Image image3 = new Image("inGame/upload.png", 200, 200, false, false);
        ImageView img3 = new ImageView(image3);
        img3.setX(stage.getWidth()-200);
        img3.setY(stage.getHeight()-200);
        pane.getChildren().add(img3);


        img3.setOnMouseClicked(e -> {
            runGame.gameOn();
        });


        //###########################################################################################

        scene.setOnKeyPressed(e -> {

            //TODO remove cheat
            if(e.getCode() == KeyCode.G) {
                runGame.getCurrentTurn().getLordinfo().addCash(500);
            }

            if(e.getCode() == KeyCode.ESCAPE) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/menuIngameMenu/ingame.fxml"));
                    inGameMenu.setTitle("ingameMenu");
                    inGameMenu.setScene(new Scene(root, 500, 600));
                    inGameMenu.show();

                } catch (java.io.IOException error) {
                    System.out.println("Failed to open ingame menu: " + error);
                }
            }
        });

        // ##################          COUNTY SELECTER / UNIT MOVER / CHAR SELECTER       ########################
        img.setOnMouseClicked(event -> {
            closeSecondary();
            countyStage.toBack();

            final WritableImage a = pane2.snapshot(null, null);
            final PixelReader pR = a.getPixelReader();

            Color col = pR.getColor((int)event.getSceneX(), (int)event.getSceneY());

            if(event.getButton().equals(MouseButton.PRIMARY)) {

                /*
                System.out.println("\nColor at (" + event.getSceneX() + ";" + event.getSceneY() + ") "
                        + col.toString());
                */

                if(world.getColorCounty().containsKey(col.toString())) {
                    selected = false;
                    clickedCounty = world.getColorCounty().get(col.toString());
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/menuCounty/county.fxml"));
                        countyStage.setTitle("county");
                        countyStage.setScene(new Scene(root, 500, 600));
                        countyStage.setX(0);
                        if(stage.getHeight() - 600 > 0) {
                            countyStage.setY(stage.getHeight() - 600);
                        } else {
                            countyStage.setY(0);
                        }

                        countyStage.show();

                    } catch (java.io.IOException error) {
                        System.out.println("Failed to open county: " + error);
                    }
                }
                countyStage.toFront();
            } else if (event.getButton().equals(MouseButton.SECONDARY) && selected && unitClicked.getOwner() == runGame.getCurrentTurn()) {
                // TODO check that moving unit is current turn Person ::::::::::::::::::::. done, remember to test if works right
                selected = false;
                if(world.getColorCounty().containsKey(col.toString())) {
                    if(world.getColorCounty().get(col.toString()).getConnected().contains(unitClicked.getAt())) {
                        unitClicked.moveTo(world.getColorCounty().get(col.toString()));
                        closeSecondary();
                    } else if (world.getColorCounty().get(col.toString()) == unitClicked.getAt()) {
                        unitClicked.moveTo(null);
                        closeSecondary();
                    }
                }
            } else if (event.getButton().equals(MouseButton.SECONDARY) && !selected) {
                if(world.getColorCounty().containsKey(col.toString())) {
                    selected = false;
                    clickedCounty = world.getColorCounty().get(col.toString());
                    closeSecondary();
                    setClickedPerson(Game.getGame().getClickedCounty().getOwner());
                    startCharacterMenu();
                } else {
                    closeSecondary();
                }
            }
        });
    }

    public void setWorld(World world) {
        Game.world = world;
    }

    public static RunGame getRunGame() {
        return runGame;
    }

    public static Game getGame() {
        return world.getGame();
    }
}
