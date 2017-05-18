package world;

import inGame.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 08/03/2017.
 */
public class War {
    // TODO realize method to create list of enemy units in a county
    private static final int RAIDINFAMY = 10;
    private static final int RAIDWIN = 100;
    private static final int CONQUESTINFAMY = 50;
    private static final int VASSALINFAMY = 30;
    /*
    TODO remember to refresh war owners when a combatant dies
    */
    private LordInfo attacker;
    private LordInfo defender;
    private County attackTarget;
    private County defenseTarget;
    private String warTypeString;
    private Set<LordInfo> attackers = new HashSet<>();
    private Set<LordInfo> defenders = new HashSet<>();
    private String warType;


    public void addAttacker(LordInfo l) {
        attackers.add(l);
    }
    public void removeAttacker(LordInfo l) {
        attackers.remove(l);
    }
    public void addDefender(LordInfo l) {
        defenders.add(l);
    }
    public void removeDefender(LordInfo l) {
        defenders.remove(l);
    }

    public Set<LordInfo> getAttackers() {
        return attackers;
    }

    public Set<LordInfo> getDefenders() {
        return defenders;
    }

    public County getAttackTarget() {
        return attackTarget;
    }

    public County getDefenseTarget() {
        return defenseTarget;
    }

    public War(LordInfo attacker, LordInfo defender, County attackTarget, County defenseTarget, String warType) {
        if(warType.equals("Independance")) {
            defender.removeVassal(attacker);
        }
        this.warType = warType;
        this.attackTarget = attackTarget;
        this.defenseTarget = defenseTarget;
        this.attacker = attacker;
        this.defender = defender;
        defenders.add(defender);
        attackers.add(attacker);
        attacker.addWar(this);
        defender.addWar(this);

        System.out.println("Attacker: " + attacker.getSelf().getName() + " , target: " + attackTarget.getName());
        System.out.println("Defender: " + defender.getSelf().getName() + " , defenseTarget: " + defenseTarget.getName());
        System.out.println("War type: " + warType);

        // raids don't get allies called
        if(!warType.equals("Raid")) {
            for (LordInfo l : defender.getAlliances()) {
                defenders.add(l);
                l.addWar(this);
            }
            for (LordInfo l : attacker.getAlliances()) {
                if(!defenders.contains(l)) {
                    attackers.add(l);
                    l.addWar(this);
                }
            }
        }
    }

    //* warTypes.add("Raid");
    //* warTypes.add("Conquest");
    //* warTypes.add("Force vassalization");
    //* warTypes.add("Independance");
    //* warTypes.add("Defense"); // not used yet

    public void endWar(String winner) {
        Game.getWorld().removeWar(this);
        endWar();

        if(warType.equals("Independance") && winner.equals("whitePeace")) {
            attacker.setVassalTo(defender);
        }

        if(warType.equals("Raid")) {
            if(winner.equals("attacker")) {
                attacker.addCash(RAIDWIN);
                if(defender.getCash() < 100) {
                    defender.loseCashTo(100, attacker);
                } else {
                    defender.loseCashTo(100 + defender.getCash() / 2, attacker);
                }
                attacker.addInfamy(RAIDINFAMY);
            } else {
                attacker.loseCashTo(attacker.getCash(), defender);
            }
        }
        if(warType.equals("Conquest")) {
            if(winner.equals("attacker")) {
                defender.loseCounty(attackTarget);
                defender.tryRemoveLord();
                attacker.addCounty(attackTarget);
                attacker.addInfamy(CONQUESTINFAMY);
            } else {
                attacker.loseCashTo(attacker.getCash() / 2, defender);
                attacker.getSelf().death();
            }
        }
        if(warType.equals("Force vassalization")) {
            if(winner.equals("attacker")) {
                defender.setVassalTo(attacker);
                attacker.addInfamy(VASSALINFAMY);
                removeFromWars(defender);
            } else {
                attacker.loseCashTo(attacker.getCash(), defender);
            }
        }
        if(warType.equals("Independance")) {
            if(winner.equals("defender")) {
                removeFromWars(attacker);
                attacker.setVassalTo(defender);
            }
        }
    }

    public static void removeFromWars (LordInfo lord) {
        Set<War> temp = new HashSet<>();
        temp.addAll(lord.getWars());
        for(War w : temp) {
            if(w.getDefender() == lord || w.getAttacker() == lord) {
                w.endWar();
            } else {
                w.removeFromWar(lord);
            }
        }
    }

    public void endWar() {
        attackTarget.removeOccupier();
        defenseTarget.removeOccupier();
        for(LordInfo l : attackers) {
            for(County c : l.getCounties()) {
                if(c.getOccupier() != null) {
                    if (defenders.contains(c.getOccupier())) {
                        c.setOccupier(null);
                    }
                }
            }
        }
        for(LordInfo l : defenders) {
            for(County c : l.getCounties()) {
                if(c.getOccupier() != null) {
                    if (attackers.contains(c.getOccupier())) {
                        c.setOccupier(null);
                    }
                }
            }
        }
        if(Game.getWorld().getWars().contains(this)) {
            Game.getWorld().removeWar(this);
        }
        for(LordInfo l : attackers) {
            l.removeWar(this);
        }
        for(LordInfo l: defenders) {
            l.removeWar(this);
        }
    }

    public void tryEndWar() {
        for(LordInfo l : attackers) {
            if(defenders.contains(l)) {
                endWar("whitePeace");
                System.out.println("AN ATTACKER ENTERED DEFENDER SIDE; WAR ENDED");
            }
        }
        if(attackTarget.getOwner().getLordinfo() != defender) {
            endWar("whitePeace");
        }
        if(attackTarget == null || defenseTarget == null || attacker == null || defender == null) {
            System.out.println("INVALID WAR TRIED TO END!");
            return;
        }
        if(attackTarget.getOccupier() != null && defenseTarget.getOccupier() != null) {
            endWar("whitePeace");
        }

        if(attackTarget.getOccupier() != null) {
            if(attackers.contains(attackTarget.getOccupier().getLordinfo())) {
                endWar("attacker");
            }
        }
        if(defenseTarget.getOccupier() != null) {
            if(defenders.contains(defenseTarget.getOccupier().getLordinfo())) {
                endWar("defender");
            }
        }
        if(warType.equals("Raid")) {
            for(County c : defender.getCounties()) {
                if(c.getOccupier() != null) {
                    if(c.getOccupier().getLordinfo() == attacker) {
                        endWar("attacker");
                    }
                }
            }
        }
    }

    public void removeFromWar(LordInfo l) {
        for(County c : l.getCounties()) {
            c.setOccupier(null);
        }
        for(County c : Game.getWorld().getCounties().values()) {
            if(c.getOccupier() == l.getSelf()) {
                c.setOccupier(null);
            }
        }
        if(attackers.contains(l)) {
            attackers.remove(l);
        }
        if(defenders.contains(l)) {
            defenders.remove(l);
        }
        l.removeWar(this);
    }

    public LordInfo getAttacker() {
        return attacker;
    }

    public void setAttacker(LordInfo attacker) {
        this.attacker = attacker;
    }

    public LordInfo getDefender() {
        return defender;
    }

    public void setDefender(LordInfo defender) {
        this.defender = defender;
    }

    public String getWarTypeString() {
        return warType;
    }

    public void setWarTypeString(String warTypeString) {
        this.warTypeString = warTypeString;
    }
}
