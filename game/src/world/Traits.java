package world;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class Traits {
    private String name;
    private Integer angry;
    private Integer troopB;
    private Integer goodEventB;
    private Integer warB;
    private Integer lust;
    private Integer ambition;
    private Integer good;
    private Boolean visible;
    private String opposing;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAngry() {
        return angry;
    }

    public void setAngry(Integer angry) {
        this.angry = angry;
    }

    public Integer getTroopB() {
        return troopB;
    }

    public void setTroopB(Integer troopB) {
        this.troopB = troopB;
    }

    public Integer getGoodEventB() {
        return goodEventB;
    }

    public void setGoodEventB(Integer goodEventB) {
        this.goodEventB = goodEventB;
    }

    public Integer getWarB() {
        return warB;
    }

    public void setWarB(Integer warB) {
        this.warB = warB;
    }

    public Integer getLust() {
        return lust;
    }

    public void setLust(Integer lust) {
        this.lust = lust;
    }

    public Integer getAmbition() {
        return ambition;
    }

    public void setAmbition(Integer ambition) {
        this.ambition = ambition;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getOpposing() {
        return opposing;
    }

    public void setOpposing(String opposing) {
        this.opposing = opposing;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Traits(String name, Integer angry, Integer troopB, Integer goodEventB, Integer warB, Integer lust, Integer ambition, Integer good, Boolean visible, String opposing, String icon) {
        this.name = name;
        this.angry = angry;
        this.troopB = troopB;
        this.goodEventB = goodEventB;
        this.warB = warB;
        this.lust = lust;
        this.ambition = ambition;
        this.good = good;
        this.visible = visible;
        this.opposing = opposing;
        this.icon = icon;
    }


}
