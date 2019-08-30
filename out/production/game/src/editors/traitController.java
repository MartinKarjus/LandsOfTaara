package editors;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import world.Traits;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class traitController implements Initializable {

    private Map<String, Traits> holder = new LinkedHashMap<String, Traits>();

    @FXML
    private Pane editorWindow;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField aggressiveTf;

    @FXML
    private TextField troopBTf;

    @FXML
    private TextField luckBTf;

    @FXML
    private TextField lustBTf;

    @FXML
    private TextField ambitionBTf;

    @FXML
    private TextField justiceBTf;

    @FXML
    private TextField opposingTf;

    @FXML
    private TextField iconTf;

    @FXML
    private CheckBox visibleT;

    @FXML
    private Button saveCreated;

    @FXML
    private Button addTraitBtn;

    @FXML
    private Button exitEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private Button removeLast;

    @FXML
    private TextField warBTf;

    @FXML
    private Label lastTraitLabel;

    private int parse(String in) {
        return Integer.parseInt(in);
    }

    private void setDefault() {
        warBTf.setText("0");
        aggressiveTf.setText("0");
        troopBTf.setText("0");
        luckBTf.setText("0");
        lustBTf.setText("0");
        ambitionBTf.setText("0");
        justiceBTf.setText("0");
        opposingTf.setText("");
        iconTf.setText("default.jpg");
        visibleT.setSelected(true);
    }


    @FXML
    void addAction(ActionEvent event) {
        try {
            holder.put(nameTf.getText(), new Traits(
                    nameTf.getText(),
                    parse(aggressiveTf.getText()),
                    parse(troopBTf.getText()),
                    parse(luckBTf.getText()),
                    parse(warBTf.getText()),
                    parse(lustBTf.getText()),
                    parse(ambitionBTf.getText()),
                    parse(justiceBTf.getText()),
                    visibleT.isSelected(),
                    opposingTf.getText(),
                    iconTf.getText()));
                    errorLabel.setText("Have fun creating traits!");
                    lastTraitLabel.setText(nameTf.getText());
                    setDefault();
        } catch (NumberFormatException error) {
                errorLabel.setText("Invalid input!");
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        Stage stage = (Stage) exitEditor.getScene().getWindow();
        stage.close();;
    }

    @FXML
    void removeAction(ActionEvent event) {
        if(holder.isEmpty()) {
            return;
        }
        String toRemove = "None";
        String toSet = "None";
        Iterator<Map.Entry<String, Traits>> iterator = holder.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Traits> entry = iterator.next();
            toSet = toRemove;
            toRemove = entry.getKey();
        }
        lastTraitLabel.setText(toSet);
        holder.remove(toRemove);
    }

    @FXML
    void saveAction(ActionEvent event) {

        try {
            FileWriter fstream = new FileWriter("/resources/customTraits.txt", false);
            BufferedWriter buffOut = new BufferedWriter(fstream);
            Iterator<Map.Entry<String, Traits>> iterator = holder.entrySet().iterator();
            buffOut.write("########### START OF CUSTOM TRAITS ####################");
            while(iterator.hasNext()){
                Map.Entry<String, Traits> entry = iterator.next();
                Traits trait = entry.getValue();
                buffOut.write("{" + "\n");
                buffOut.write("name:" + trait.getName() + "\n");
                buffOut.write("angry" + String.valueOf(trait.getAngry()) + "\n");
                buffOut.write("troopB:" + String.valueOf(trait.getTroopB()) + "\n");
                buffOut.write("goodEventB:" + String.valueOf(trait.getGoodEventB()) + "\n");
                buffOut.write("warB:" + String.valueOf(trait.getWarB()) + "\n");
                buffOut.write("lust:" + String.valueOf(trait.getLust()) + "\n");
                buffOut.write("ambition:" + String.valueOf(trait.getAmbition()) + "\n");
                buffOut.write("good:" + String.valueOf(trait.getGood()) + "\n");
                if(trait.getVisible()) {
                    buffOut.write("visible:" + "Y" + "\n");
                } else {
                    buffOut.write("visible:" + "N" + "\n");
                }
                buffOut.write("opposing:" + trait.getOpposing() + "\n");
                buffOut.write("icon:" + trait.getIcon() + "\n");
                buffOut.write("}" + "\n");
            }
            buffOut.write("##########END OF READ################" + "\n");
            buffOut.close();
            errorLabel.setText("Save successful");
        } catch (IOException e) {
            errorLabel.setText("Save failed");
            System.err.println("O noes: " + e.getMessage());
        }
        lastTraitLabel.setText("None");
    }


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        System.out.println("in trait controller");
        holder.putAll(world.readPremade.traits("customTraits.txt"));
        if(!holder.isEmpty()) {
            String toRemove = "None";
            String toSet = "None";
            Iterator<Map.Entry<String, Traits>> iterator = holder.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Traits> entry = iterator.next();
                toSet = toRemove;
                toRemove = entry.getKey();
            }
            lastTraitLabel.setText(toSet);
        }
    }

    public Pane getTraitPane() {
        return editorWindow;
    }

}

