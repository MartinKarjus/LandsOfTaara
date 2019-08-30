package world;

import javafx.scene.paint.Color;

/**
 * Created by Martin Karjus 1 on 16/04/2017.
 */
public class Unit {
    private Person owner;
    private int archers;
    private int infantry;
    private int heavyInfantry;
    private int cavalry;
    private int special;
    private County at;
    private Color color;
    private County move;
    private County originatesFrom;
    private int siegeCounter = 0;

    public void increaseSiegeCounter() {
        siegeCounter += 1;
    }

    public int getSiegeCounter() {
        return siegeCounter;
    }

    public void setSiegeCounter(int siegeCounter) {
        this.siegeCounter = siegeCounter;
    }

    public County getOriginatesFrom() {
        return originatesFrom;
    }

    public int getTotal() {
        int temp = archers + infantry + heavyInfantry + cavalry + special;
        if(temp > 0) {
            return temp;
        }
        return 0;
    }

    public County getMove() {
        return move;
    }

    public void moveTo(County to) {
        move = to;
    }

    public void stop() {
        move = null;
    }

    public Color getColor() {
        return color;
    }

    public int getThousands() {
        return ((archers + infantry + heavyInfantry + cavalry + special) / 1000);
    }

    public void moveUnit(County target) {
        siegeCounter = 0;
        at.removeUnit(this);
        at = target;
        move = null;
        target.addUnit(this);
    }

    public void loseTroops(int i) {
        while (i > 0) {
            if(archers > 0) {
                archers -= 50;
                if(archers < 0) {
                    archers = 0;
                }
            } else if(infantry > 0) {
                infantry -= 50;
                if(infantry < 0) {
                    infantry = 0;
                }
            } else if(heavyInfantry > 0) {
                heavyInfantry -= 50;
                if(heavyInfantry < 0) {
                    heavyInfantry = 0;
                }
            } else if(cavalry > 0) {
                cavalry -= 50;
                if(cavalry < 0) {
                    cavalry = 0;
                }
            } else if(special > 0) {
                special -= 50;
                if(special < 0) {
                    special = 0;
                }
            } else {
                break;
            }
            i -= 50;
        }
        if(getTotal() <= 0) {
            owner.getLordinfo().lowerArmy();
        }
    }

    public Unit(Person owner, int archers, int infantry, int heavyInfantry, int cavalry, int special, County at) {
        this.owner = owner;
        this.archers = archers;
        this.infantry = infantry;
        this.heavyInfantry = heavyInfantry;
        this.cavalry = cavalry;
        this.special = special;
        this.at = at;
        at.addUnit(this);
        color = owner.getLordinfo().getColor();
        originatesFrom = at;
    }

    public Unit() {
        this.archers = 0;
        this.infantry = 0;
        this.heavyInfantry = 0;
        this.cavalry = 0;
        this.special = 0;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public County getAt() {
        return at;
    }

    public void setAt(County at) {
        this.at = at;
    }

    public int getArchers() {
        return archers;
    }

    public void setArchers(int archers) {
        this.archers = archers;
    }

    public int getInfantry() {
        return infantry;
    }

    public void setInfantry(int infantry) {
        this.infantry = infantry;
    }

    public int getHeavyInfantry() {
        return heavyInfantry;
    }

    public void setHeavyInfantry(int heavyInfantry) {
        this.heavyInfantry = heavyInfantry;
    }

    public int getCavalry() {
        return cavalry;
    }

    public void setCavalry(int cavalry) {
        this.cavalry = cavalry;
    }

    public int getSpecial() {
        return special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }
}
