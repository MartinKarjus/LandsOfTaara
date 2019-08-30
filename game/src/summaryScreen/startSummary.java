package summaryScreen;


import inGame.Game;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import world.Person;

public class startSummary {

    private startSummary(Person p) {
        Game.getGame().closeSecondary();
        Stage listStage = Game.getGame().getLawScreen();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/summaryScreen/summary.fxml"));
            listStage.setScene(new Scene(root));
            //listStage.setX(200);
            //listStage.setY(100);
            listStage.show();
        } catch (java.io.IOException error) {
            System.out.println("Failed to open summary: " + error);
        }
    }

    public static void summary(Person p) {
        startSummary summary = new startSummary(p);
    }
}
