package world;

import java.util.List;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class Event {
    private String ID;
    private String heading;
    private String Text;
    private String debugTxt;
    private String picName;
    private Boolean war;
    private List<String> choices;
    private List<String> effect;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getDebugTxt() {
        return debugTxt;
    }

    public void setDebugTxt(String debugTxt) {
        this.debugTxt = debugTxt;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public Boolean getWar() {
        return war;
    }

    public void setWar(Boolean war) {
        this.war = war;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public List<String> getEffect() {
        return effect;
    }

    public void setEffect(List<String> effect) {
        this.effect = effect;
    }

    public Event(String ID, String heading, String text, String debugTxt, String picName, Boolean war, List<String> choices, List<String> effect) {
        this.ID = ID;
        this.heading = heading;
        Text = text;
        this.debugTxt = debugTxt;
        this.picName = picName;
        this.war = war;
        this.choices = choices;
        this.effect = effect;
    }
}
