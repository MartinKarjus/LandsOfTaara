package world;

import game.RunGame;
import inGame.Game;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class LordInfo {

    public static final int EXTRA_OPINION = 70;

    private String law;
    private String nickname;
    private List<County> counties = new ArrayList<>();
    private List<County> occupied = new ArrayList<>();
    private String strategy;
    private LordInfo vassalTo;
    private Set<LordInfo> alliances = new HashSet<>();
    private Set<War> wars = new HashSet<>();
    private Integer cash;
    private Integer extraTroops = 0;
    private Integer infamy = 0;
    private Integer honor = 0;
    private Color color;
    private boolean armyRaised = false;
    private Person self;
    private Set<LordInfo> vassals = new HashSet<>();
    private Event lastEvent;
    private County warTargetAI;


    public County getWarTargetAI() {
        return warTargetAI;
    }

    public void setWarTargetAI(County warTargetAI) {
        this.warTargetAI = warTargetAI;
    }

    public Event getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(Event lastEvent) {
        this.lastEvent = lastEvent;
    }

    public void giveAllToPerson(Person inheritor) {
        lowerArmy();
        if(inheritor == null) {
            System.out.println("INVALID giveAllToPerson.. and there's actually nothing to deal with this so..");
            System.out.println("    There's probably a zombie lord somewhere now.. this Should never happen though");
            return;
        }
        if(inheritor.getLordinfo() == null) {
            System.out.println("Non-lord inherits");
            Game.getWorld().move(self);
            Game.getWorld().move(inheritor);
            inheritor.setLordinfo(this);
            nickname = null;
            self = inheritor;
            for(County c : counties) {
                c.setOwner(inheritor);
            }
            return;
        }
        LordInfo info = inheritor.getLordinfo();

        // end all wars that deposed/dead guy is a leader of
        // remove him as attacker and defender if he isnt
        War.removeFromWars(this);

        for(County c : occupied) {
            c.setOccupier(null);
        }
        Set<County> temp = new HashSet<>();
        temp.addAll(counties);
        temp.addAll(info.getCounties());
        for(County c : temp) {
            c.setOwner(info.getSelf());
        }
        List<County> temp3 = new ArrayList<>();
        temp3.addAll(temp);
        info.setCounties(temp3);

        if(vassalTo != null) {
            vassalTo.removeVassal(this);
        }
        info.addCash(cash);
        info.setExtraTroops(info.getExtraTroops() + extraTroops);
        Set<LordInfo> temp2 = vassals;
        for(LordInfo l : temp2) {
            removeVassal(l);
            l.setVassalTo(info);
        }
        Game.getWorld().move(self);
    }

    public int getOpinion(LordInfo opinionTo) {
        // TODO need better logic..
        // totally legit opinion calculator
        int anger = 0;
        int warB = 0;
        int ambition = 0;
        int good = 0;
        int lust = 0;

        int opinion = 0;
        for(Traits t : self.getTraits()) {
            anger += t.getAngry();
            warB += t.getWarB();
            ambition += t.getAmbition();
            good += t.getGood();
            lust += t.getLust();
            for(Traits other : opinionTo.getSelf().getTraits()) {
                if(t.getName().equals(other.getName())) {
                    opinion += 10;
                } else if (t.getOpposing().equals(other.getName())) {
                    opinion -= 20;
                }
            }
        }
        if(anger < 0) {
            opinion += (anger*-1*10);
        } else {
            opinion -= anger*10;
        }
        if(warB > 5) {
            if(opinionTo.getCounties().size() < counties.size()) {
                opinion -= 100;
            }
        }
        if(ambition > 0 && opinionTo.getCounties().get(0).getConnected().contains(counties.get(0))) {
            opinion -= 20;
            opinion -= ambition*2;
        }
        opinion += good * 10;
        if(lust > 0 && !opinionTo.getSelf().getGender().equals(self.getGender())) {
            opinion += lust*2;
        }

        opinion -= opinionTo.getInfamy();
        if(opinionTo.getInfamy() > 50) {
            return opinion;
        }

        for(Person p :Game.getWorld().getLords()) {
            if(p.getLordinfo().getInfamy() > 25 && p != opinionTo.getSelf() && p != self && !alliances.contains(p)) {
                boolean found = false;
                for(County c : p.getLordinfo().getCounties()) {
                    for(County mine : counties) {
                        if(mine.getConnected().contains(c)) {
                            found = true;
                        }
                    }
                }
                if(found) {
                    opinion += 50;
                }
            } else if (p.getLordinfo().getInfamy() > 75 && p != opinionTo.getSelf() && p != self && !alliances.contains(p)) {
                opinion += 75;
            }
        }
        return opinion + EXTRA_OPINION;
    }

    public boolean willMarry(LordInfo offerMaker) {
        // TODO make better logic
        if(getOpinion(offerMaker) > 75) {
            return true;
        }
        boolean evilExists = false;
        for(Person p : Game.getWorld().getLords()) {
            if(p.getLordinfo().getInfamy() > 25 && p != self && !alliances.contains(p)) {
                evilExists = true;
            }
        }
        if (evilExists && getOpinion(offerMaker) > 25) {
            return true;
        }
        return false;
    }

    public Integer getInfamy() {
        return infamy;
    }

    public void removeVassal(LordInfo l) {
        vassals.remove(l);
        if(l.getVassalTo() == this) {
            l.setVassalTo(null);
        }
    }

    public void addVassal(LordInfo l) {
        vassals.add(l);
        if(l.getVassalTo() != this) {
            l.setVassalTo(this);
        }
    }

    public void tryRemoveLord() {
        if(counties.size() == 0) {
            if(vassalTo != null) {
                vassalTo.removeVassal(this);
            }
            lowerArmy();
            War.removeFromWars(this);
            Game.getWorld().getLords().remove(self);
            self.setLordinfo(null);
            Game.getWorld().move(this.getSelf());
        }
    }

    public void addHonor(int i) {
        honor += i;
    }

    public void addInfamy(int i) {
        infamy += i;
        if(infamy < 0) {
            infamy = 0;
        }
    }

    public int getIncome() {
        int temp = 0;
        for(County c : counties) {
            temp += c.getIncome();
        }
        return temp;
    }

    public void lowerArmy() {
        System.out.println("Lowering army, owner: " + self.getName() + " " + self.getSurname());
        List<Unit> temp = new ArrayList<>();
        temp.addAll(Game.getWorld().getUnits());
        for(Unit u : temp) {
            if (u.getOwner().getLordinfo() == this) {
                Game.getWorld().removeUnit(u);
                u.getAt().removeUnit(u);
                u.getOriginatesFrom().addTroops(u.getTotal()/10*9);
            }
        }
        Game.getGame().setMovables();
        armyRaised = false;
    }

    public void raiseArmy() {
        int temp = 0;
        County capital = counties.get(0);
        for(County c : counties) {
            System.out.println("Raising from: " + c.getName());
            temp += c.getTroops();
            c.setTroops(0);
        }
        if(temp > 0) {
            /*
            Unit unit = new Unit(self, temp / 10 * 2, temp / 10 * 4, temp / 10,
                    temp / 10 * 2, temp / 10, capital);
            */
            Unit unit = new Unit(self, temp, 0, 0, 0, 0, capital);
            Game.getWorld().addUnit(unit);
            armyRaised = true;
        }
        System.out.println("total raised: " + temp);
        Game.getGame().setMovables();
    }

    public void raiseArmyIndividualCounties() {
        int temp;
        for(County c : counties) {
            temp = c.getTroops();
            c.setTroops(0);
            if(temp > 0) {
                Unit unit = new Unit(self, temp / 10 * 2, temp / 10 * 4, temp / 10,
                        temp / 10 * 2, temp / 10, c);
                Game.getWorld().addUnit(unit);
                armyRaised = true;
            }
        }
    }

    public boolean isArmyRaised() {
        return armyRaised;
    }

    public void setArmyRaised(boolean armyRaised) {
        this.armyRaised = armyRaised;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Person getSelf() {
        return self;
    }

    public void setSelf(Person self) {
        this.self = self;
    }

    public void loseCashTo(int ammount, LordInfo target) {
        target.addCash(ammount);
        cash -= ammount;
        if(cash < 0) {
            cash = 0;
        }
    }

    public void addHonor(Integer honor) {
        this.honor += honor;
    }

    public void addInfamy(Integer infamy) {
        this.infamy += infamy;
    }

    public void addCounty(County county) {
        counties.add(county);
        county.setOwner(self);
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

    public LordInfo getVassalTo() {
        return vassalTo;
    }

    public Set<LordInfo> getVassals() {
        return vassals;
    }

    public void setVassalTo(LordInfo vassalTo) {
        if(!vassalTo.getVassals().contains(this)) {
            vassalTo.addVassal(this);
        }
        this.vassalTo = vassalTo;
    }

    public Set<LordInfo> getAlliances() {
        return alliances;
    }

    public void setAlliances(Set<LordInfo> alliances) {
        this.alliances = alliances;
    }

    public void addAlliance(LordInfo l) {
        this.alliances.add(l);
        if(!l.getAlliances().contains(this)) {
            l.addAlliance(this);
        }
    }

    public void removeAlliance(LordInfo l) {
        this.alliances.remove(l);
        l.removeAlliance(this);
    }

    public Set<War> getWars() {
        return wars;
    }

    public void setWars(Set<War> wars) {
        this.wars = wars;
    }

    public void removeWar(War war) {
        this.wars.remove(war);
    }

    public void addWar(War war) {
        this.wars.add(war);
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public void addCash(Integer cash) {
        this.cash += cash;
        if(this.cash < 0) {
            this.cash = 0;
        }
    }

    public Integer getExtraTroops() {
        return extraTroops;
    }

    public void setExtraTroops(Integer extraTroops) {
        this.extraTroops = extraTroops;
    }

}
