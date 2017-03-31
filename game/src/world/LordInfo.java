package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class LordInfo {
    private String nickname;
    private List<County> counties = new ArrayList<>();
    private List<County> occupied = new ArrayList<>();
    private String strategy;
    private Person vassalTo;
    private List<Person> alliances = new ArrayList<>();
    private List<War> wars = new ArrayList<>();
    private Integer cash;
    private Integer extraTroops;

    public void addCounty(County county) {
        counties.add(county);
    }
    public void loseCounty(County county) {
        counties.remove(county);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Person getVassalTo() {
        return vassalTo;
    }

    public void setVassalTo(Person vassalTo) {
        this.vassalTo = vassalTo;
    }

    public List<Person> getAlliances() {
        return alliances;
    }

    public void setAlliances(List<Person> alliances) {
        this.alliances = alliances;
    }

    public List<War> getWars() {
        return wars;
    }

    public void setWars(List<War> wars) {
        this.wars = wars;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getExtraTroops() {
        return extraTroops;
    }

    public void setExtraTroops(Integer extraTroops) {
        this.extraTroops = extraTroops;
    }
}
