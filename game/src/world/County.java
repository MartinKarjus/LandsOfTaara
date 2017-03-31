package world;

import java.util.ArrayList;
import java.util.List;

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

    public void upgrade(String type) {
        if (type.equals("troop")) {
            troopLevel += 1;
        } else if (type.equals("def")) {
            defLevel += 1;
        } else if (type.equals("income")) {
            incomeLevel += 1;
        } else {
            System.out.println("O NOES! INVALID UPGRADE!");
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

    public County(String name, int def, int troops, double income) {
        this.name = name;
        this.def = def;
        this.troops = troops;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getTroops() {
        return troops;
    }

    public void setTroops(int troops) {
        this.troops = troops;
    }

    public double getIncome() {
        return income;
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
}
