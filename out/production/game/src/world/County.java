package world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class County {
    //County (name, owner, def, troops, income, occupier, connected<list>)
    private String name;
    private Person owner;
    private int def;
    private int troops;
    private double income;
    private Person occupier;
    private List<County> connected = new ArrayList<>();
    private int troopLevel = 1;
    private int defLevel = 1;
    private int incomeLevel = 1;
    private String colorString;
    private int centerX = -1;
    private int centerY = -1;
    private Set<Unit> unitsAt = new HashSet<>();

    public void addTroops(int newTroops) {
        if(troops + newTroops < 0) {
            troops = 0;
        } else if (troops + newTroops > troopLevel * 1000) {
            troops = troopLevel * 1000;
        } else {
            troops += newTroops;
        }
    }

    public void removeUnit(Unit unit) {
        unitsAt.remove(unit);
    }
    public void addUnit(Unit unit) {
        unitsAt.add(unit);
    }

    public Set<Unit> getUnitsAt() {
        return unitsAt;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setXY(int x, int y) {
        centerX = x;
        centerY = y;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public void upgrade(String type, LordInfo lord) {
        if (type.equals("troop") && lord.getCash() >= troopLevel*100) {
            lord.addCash(-troopLevel*100);
            troopLevel += 1;
        } else if (type.equals("def") && lord.getCash() >= defLevel*100) {
            lord.addCash(-defLevel*100);
            defLevel += 1;
        } else if (type.equals("income") && lord.getCash() >= incomeLevel*100) {
            lord.addCash(-incomeLevel*100);
            incomeLevel += 1;
        }
    }

    public int getTroopLevel() {
        return troopLevel;
    }

    public void setTroopLevel(int troopLevel) {
        this.troopLevel = troopLevel;
    }

    public int getDefLevel() {
        return defLevel;
    }

    public void setDefLevel(int defLevel) {
        this.defLevel = defLevel;
    }

    public int getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(int incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public void addCon(County c) {
        connected.add(c);
    }

    public County(String name, int def, int troops, double income, String colorString) {
        this.name = name;
        this.def = def;
        this.troops = troops;
        this.income = income;
        this.colorString = colorString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDef() {
        return def + troops;
    }

    public void setDef(int def) {
        if(def > defLevel * 1000) {
            this.def = defLevel * 1000;
        } else {
            this.def = def;
        }
    }

    public int getTroops() {
        return troops;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public double getIncome() {
        return income * incomeLevel;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public List<County> getConnected() {
        return connected;
    }

    public void setConnected(List<County> connected) {
        this.connected = connected;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOccupier() {
        return occupier;
    }

    public void setOccupier(Person occupier) {
        this.occupier = occupier;
    }

    public void removeOccupier() {
        this.occupier = null;
    }
}
