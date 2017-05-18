package game; /**
 * Created by Martin Karjus 1 on 14/03/2017.
 */
import inGame.Game;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import summaryScreen.startSummary;
import world.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RunGame {
    // ##########################################################################################################
    /** Road probably ran into infinite loop(maybe invalid connections initialized?). */
    public static final int TOO_LONG_ROAD = 1000;

    public static final int EVENT_CHANCE = 50;

    /** Set to true if testing is in progress, used in some functions to skip stuff that needs generated world.*/
    private boolean testing = false;// DO CHANGE THIS IF YOU DONT KNOW EXACTLY WHAT FUNCTIONS GET CHANGED AND HOW


    // All random values are taken from 0 to 99
    // OLD_DEATH_CHANCE = 20 means that every person over or at 40, younger than 60 has 21% chance to die
    // All things happen once per season(all turns passed) so if these values are too high it may cause the game
    //      to end very fast
    public static final boolean RANDOMDEATHS = true;
    public static final int CHILD_DEATH_CHANCE = 2;
    public static final int YOUNG = 16;
    public static final int YOUNG_DEATH_CHANCE = 3;
    public static final int OLD = 40;
    public static final int OLD_DEATH_CHANCE = 20;
    public static final int ANCIENT = 60;
    public static final int ANCIENT_DEATH_CHANCE = 60;

    // children have a chance to be born each season.. so you may end up with 4 kids in a year
    //  it would be easy to fix but at the rate ppl die or get killed, this is actually pretty fine
    public static final int CHILD_CHANCE = 80;
    public static final int CHILD_BEARING_MIN = 16;
    public static final int CHILD_BEARING_MAX = 50;

    // a weak siege is a siege when attackers numbers are lower than or equal to defenders
    public static final int LOSE_ON_WEAK_SIEGE = 0; // defenders might generate faster than attackers kill them :)
    public static final int LOSE_ON_STRONG_SIEGE = 0; // thats why its currently disabled
    public static final int DEFENDER_LOSE_WEAK_SIEGE = 500;
    public static final int DEFENDER_LOSE_STRONG_SIEGE = 750;

    public static final int INFAMY_LOWERED_EACH_SEASON = 5;

    // ##########################################################################################################
    private Person currentTurn;
    private World world;

    private List<Person> players = new ArrayList<>();
    private AI ai;

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public Person getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Person currentTurn) {
        this.currentTurn = currentTurn;
    }

    public List<Person> getPlayers() {
        return players;
    }

    public void testInherit() {
        // TODO remove this / move this / add it as a test
        Person t = new Person();
        LordInfo l = new LordInfo();
        t.setLordinfo(l);
        l.setLaw("Agnatic-Cognatic");

        Person son = new Person();
        son.setGender("M");
        son.setName("youngSon");
        son.setBirthdate(new YearSeason("W", 100));
        t.getRelations().addChild(son);

        Person son2 = new Person();
        son2.setGender("M");
        son2.setName("oldSon");
        son2.setBirthdate(new YearSeason("W", 91));
        t.getRelations().addChild(son2);

        Person daughter = new Person();
        daughter.setName("oldDaughter");
        daughter.setGender("F");
        daughter.setBirthdate(new YearSeason("W", 90));
        t.getRelations().addChild(daughter);

        System.out.println(t + " " + son + " " + daughter);
        Person deadPerson = inherit(t);
        if(deadPerson != null) {
            System.out.println(deadPerson.getName());
        } else {
            System.out.println("Noone inherited.");
        }

        System.out.println(daughter.getBirthdate().isOlder(son.getBirthdate()));
    }

    public static void main(String[] args) {
        //TODO
        //TODO i wonder what i was gonna write to this todo...
        RunGame t = new RunGame();
        t.testing = true;
        t.testInherit();
    }
    /** For testing only.*/
    public RunGame() {

    }

    public Person inherit(Person deadGuy) {
        System.out.println("in inherit");
        checked2 = new HashSet<>();
        if(!testing) {
            if(!world.getLords().contains(deadGuy)) {
                System.out.println("Invalid person inheriting.");
                return null;
            }
        }
        //Set<Person> checked2 = new HashSet<>();
        Person current;
        List<Set<Person>> a = new ArrayList<>();
        current = deadGuy;
        int i = 0;
        a.add(current.getRelations().getChildren());
        a.add(current.getRelations().getSiblings());
        // add all connected ppl to list of lists to check
        while(i+1 < a.size()) {
            for(Person p : a.get(i)) {
                a.add(p.getRelations().getChildren());
            }
            i++;
        }
        if(deadGuy.getRelations().getFather() != null) {
            Set<Person> temp = new HashSet<>();
            temp.add(deadGuy.getRelations().getFather());
            a.add(temp);
        }
        if(deadGuy.getRelations().getMother() != null) {
            Set<Person> temp = new HashSet<>();
            temp.add(deadGuy.getRelations().getMother());
            a.add(temp);
        }

        // loop through lvl, find best(cant be in checked2Person), continue search, mark person as checked2
        for(Set<Person> s : a) {
            Person inheritor = inherit2(s, deadGuy.getLordinfo().getLaw());
            if(inheritor != null) {
                if(inheritor.getAlive()) {
                    return inheritor;
                }
            }
        }
        return null;
    }

    private Set<Person> checked2 = new HashSet<>();

    private Person inherit2(Set<Person> s, String law) {
        /*
        current laws:
        "Agnatic"
        "Agnatic-Cognatic"
        "Cognatic"
         */
        int limit = 0;
        //System.out.println("set in inherit2: " + s);
        Person best = null;
        for(Person p : s) {
            limit += 1;
            if(limit == 100) {
                // if inheritor is too far(or logic failed and entered infinite loop), return null
                return null;
            }
            if(!checked2.contains(p)) {
                if(best != null) {
                    if(!best.getAlive() && p.getAlive()) {
                        if(!law.equals("Agnatic") || p.getGender().equals("M")) {
                            System.out.println("dead person replaced");
                            // this makes sure dead ppl dont inherit anymore... i hope.. zombies are a problem..
                            best = p;
                        }
                    }
                }
                if(law.equals("Agnatic") && p.getGender().equals("M")) {
                    if(best == null) {
                        best = p;
                    } else if(!p.getBirthdate().isOlder(best.getBirthdate())) {
                        if(p.getAlive() && best.getAlive()) {
                            best = p;
                        } else if(!p.getAlive() && !best.getAlive()) {
                            best = p;
                        }
                    }
                }
                else if(law.equals("Agnatic-Cognatic")) {
                    if (best == null) {
                        best = p;
                    } else if (best.getGender().equals("F") && p.getGender().equals("M") || (p.getAlive() && !best.getAlive())) {
                        best = p;
                    } else if(!p.getBirthdate().isOlder(best.getBirthdate()) && p.getGender().equals(best.getGender())) {
                        if(p.getAlive() && best.getAlive()) {
                            best = p;
                        } else if(!p.getAlive() && !best.getAlive()) {
                            best = p;
                        }
                    }
                }
                else if(law.equals("Cognatic")) {
                    System.out.println("in here");
                    if(best == null) {
                        best = p;
                    } else if(!p.getBirthdate().isOlder(best.getBirthdate())) {
                        if(p.getAlive() && best.getAlive()) {
                            best = p;
                        } else if(!p.getAlive() && !best.getAlive()) {
                            best = p;
                        }
                    }
                } else {
                    System.out.println("INVALID LAW! or law is agnatic and checking female");
                }
            }
        }
        return best;
    }



    private void checkWars() {
        List<War> temp = new ArrayList<>();
        temp.addAll(world.getWars());
        for(War w : temp) {
            w.tryEndWar();
        }
    }
    private void moveUnits() {
        for(Unit u : world.getUnits()) {
            if(u.getMove() != null) {
                u.moveUnit(u.getMove());
            }
        }
        Game.getGame().setMovables();
    }

    private void doSieges() {
        Set<Unit> losetroops = new HashSet<>();
        Set<Unit> loseTroopsLots = new HashSet<>();
        Set<County> loseDefenders = new HashSet<>();
        Set<County> loseDefendersLots = new HashSet<>();
        for(County c : world.getCounties().values()) {
            for(Unit u : world.getUnits()) {
                for(War w : u.getOwner().getLordinfo().getWars()) {
                    if (w.getAttackers().contains(u.getOwner().getLordinfo()) && c == w.getAttackTarget()) {
                        if (u.getTotal() > c.getDef()) {
                            u.increaseSiegeCounter();
                            loseDefendersLots.add(c);
                            losetroops.add(u);
                        } else {
                            loseDefenders.add(c);
                            //u.loseTroops(150);
                            loseTroopsLots.add(u);
                        }
                        if (u.getSiegeCounter() > 5) {
                            u.setSiegeCounter(0);
                            c.setOccupier(u.getOwner());
                        }
                    } else if (w.getDefenders().contains(u.getOwner().getLordinfo()) && c == w.getDefenseTarget()) {
                        if (u.getTotal() > c.getDef()) {
                            u.increaseSiegeCounter();
                            loseDefendersLots.add(c);
                            losetroops.add(u);
                        } else {
                            loseDefenders.add(c);
                            //u.loseTroops(150);
                            loseTroopsLots.add(u);
                        }
                        if (u.getSiegeCounter() > 5) {
                            u.setSiegeCounter(0);
                            c.setOccupier(u.getOwner());
                        }
                    }
                }
            }
        }
        for(Unit u : loseTroopsLots) {
            if(losetroops.contains(u)) {
                losetroops.remove(u);
            }
            u.loseTroops(LOSE_ON_WEAK_SIEGE);
        }
        for(Unit u : losetroops) {
            u.loseTroops(LOSE_ON_STRONG_SIEGE);
        }
        for(County c : loseDefendersLots) {
            if(loseDefenders.contains(c)) {
                loseDefenders.remove(c);
            }
            c.setDef(c.getDef() - DEFENDER_LOSE_STRONG_SIEGE);
        }
        for(County c : loseDefenders) {
            c.setDef(c.getDef() - DEFENDER_LOSE_WEAK_SIEGE);
        }
    }

    // TODO use War class instead for this stuff
    // TODO test if this works instead, if it does, keep it as it is..
    private void doFightsAndSieges() {
        doSieges();
        List<County> temp = new ArrayList<>();
        temp.addAll(world.getCounties().values());
        for(County c : temp) {
            if(c.getUnitsAt().size() > 0) {
                List<Unit> temp2 = new ArrayList<>();
                temp2.addAll(c.getUnitsAt());
                for(Unit u : temp2) {
                    List<Unit> temp3 = new ArrayList<>();
                    temp3.addAll(c.getUnitsAt());
                    for(Unit u2 : temp3) {
                        for(War w : u.getOwner().getLordinfo().getWars()) {
                            if(w.getAttackers().contains(u2.getOwner().getLordinfo()) &&
                                    w.getDefenders().contains(u.getOwner().getLordinfo())) {
                                // EPIC FIGHT TIME!
                                if(u.getTotal() >= u2.getTotal()) {
                                    u2.loseTroops(u2.getTotal() / 2);
                                    u.loseTroops(u2.getTotal() / 3);
                                    u2.getOwner().getLordinfo().lowerArmy();
                                } else {
                                    u.loseTroops(u.getTotal() / 2);
                                    u2.loseTroops(u.getTotal() / 3);
                                    u.getOwner().getLordinfo().lowerArmy();
                                }
                            }
                            if(w.getDefenders().contains(u2.getOwner().getLordinfo()) &&
                                    w.getAttackers().contains(u.getOwner().getLordinfo())) {
                                // EPIC FIGHT TIME!
                                if(u.getTotal() >= u2.getTotal()) {
                                    u2.loseTroops(u2.getTotal() / 2);
                                    u.loseTroops(u2.getTotal() / 3);
                                    u2.getOwner().getLordinfo().lowerArmy();
                                } else {
                                    u.loseTroops(u.getTotal() / 2);
                                    u2.loseTroops(u.getTotal() / 3);
                                    u.getOwner().getLordinfo().lowerArmy();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns to main menu, usually not giving any reason as to why because
     * 1) you won and u know it anyway
     * 2) you died and for pure roleplay purposes(and surely nothing else) you wont know what happened
     *      after your death
     * 3) a random bug occured and everything got messed up so it reset... but chances are good that
     *      you will think you just died and lost.. which Could happen, the probablity if actually rly high
     *      TODO make murders more common so this would be belivable, also myb add this as tooltip
     */
    private void resetGame() {
        Game.getGame().getMapStage().close();
        Startup startup = new Startup();
        startup.startUp(Startup.getPrimaryStage());
    }

    private void randomPersonInherits(Person p) {
        Random r = new Random();
        int loopCount = 0;
        if(world.getLords().size() - 1 < 0) {
            return;
        }
        int random = r.nextInt(world.getLords().size() - 1);
        while(!world.getLords().get(random).getAlive()) {
            random = r.nextInt(world.getLords().size() - 1);
            loopCount += 1;
            if (loopCount > 10000000) {
                System.out.println("*******************Everyone has died***********************************");
                restart();
                return;
            }
        }
        if(random < 0) {
            restart();
        }
        p.getLordinfo().giveAllToPerson(world.getLords().get(random));
    }

    private List<Person> checkedDeaths = new ArrayList<>();

    private void checkDeaths() {
        List<Person> temp = new ArrayList<>();
        temp.addAll(world.getLords());
        for(Person p : temp) {
            if(!p.getAlive()) {
                checkedDeaths.add(p);
                System.out.println("in death 1");
                p.getLordinfo().lowerArmy();
                Person inheritor = inherit(p);
                if(inheritor == null) {
                    randomPersonInherits(p);
                } else if (!inheritor.getAlive()) {
                    randomPersonInherits(p);
                } else {
                    p.getLordinfo().giveAllToPerson(inheritor);
                    if(players.contains(p)) {
                        players.remove(p);
                        players.add(inheritor);
                    }
                }

            }
        }
        if(world.getLords().size() == 1) {
            // TODO add end game screen
            resetGame();
        }
    }

    private void lowerInfamy() {
        List<Person> temp = new ArrayList<>();
        temp.addAll(world.getLords());
        for(Person p : temp) {
            if(p.getLordinfo() != null) {
                p.getLordinfo().addInfamy(-INFAMY_LOWERED_EACH_SEASON);
            }
        }
    }

    /**
     * Used in child spawning.
     */
    private Set<Person> checked = new HashSet<>();

    /**
     *
     * @param p1 Lord or lucky peasant(surname gets inherited from person 1)
     * @param p2 Consort
     * @return
     */
    private Person genChild(Person p1, Person p2) {
        Person temp = new Person();

        Random r = new Random();

        int result = r.nextInt(world.getPortraits().get("C").size());
        temp.setPortrait(world.getPortraits().get("C").get(result));
        temp.setTraits(GeneratePeople.genTraits(1, 3, world));

        if(r.nextBoolean()) {
            temp.setGender("F");
            temp.setName(world.getNamesF().get(0));
            world.backF();
            temp.setSurname(p1.getSurname());
        } else {
            temp.setGender("M");
            temp.setName(world.getNamesM().get(0));
            world.backM();
            temp.setSurname(p1.getSurname());
        }
        temp.setBirthdate(world.getCurrentDate());

        //parrent setting might be redundant, gets done in childBirth method(called for both parents)
        if(p1.getGender().equals("F")) {
            temp.setMom(p1);
            temp.setDad(p2);
        } else {
            temp.setMom(p2);
            temp.setDad(p1);
        }

        p1.childBirth(temp);
        p2.childBirth(temp);

        // the following is also possibly redundant, should get done in childBirth
        if(p1.getRelations().getChildren().size() > 0) {
            for(Person c : p1.getRelations().getChildren()) {
                if(c.getGender().equals("F")) {
                    temp.addSister(c);
                } else {
                    temp.addBrother(c);
                }
                if(temp.getGender().equals("F")) {
                    c.addSister(temp);
                } else {
                    c.addBrother(temp);
                }
            }
        }
        return temp;
    }


    private void spawnNew2(Person p, Random r) {
        if(!checked.contains(p)) {
            checked.add(p);
            if(p.getRelations().getConsort() != null && p.getAge() > CHILD_BEARING_MIN && p.getAge() < CHILD_BEARING_MAX) {
                checked.add(p.getRelations().getConsort());
                int lust = 0;
                for (Traits t : p.getTraits()) {
                    lust += t.getLust();
                    // NOTE THAT CURRENTLY OLDER LORD in LORD-LORD RELATION GETS HIS/HER LUST ADDED
                    //  IN LORD-PEASANT, ALWAYS THE LORD
                    //  IN PEASANT-PEASANT, ALWAYS OLDER
                }
                if(r.nextInt(100) - lust < CHILD_CHANCE) {
                    world.addPeasant(genChild(p, p.getRelations().getConsort()));
                }
            }
        }
    }

    private void spawnNew() {
        checked = new HashSet<>();
        Random r = new Random();
        List<Person> temp = new ArrayList<>();
        // without temp, peasants get modified turning generation
        temp.addAll(world.getLords());
        temp.addAll(world.getPeasants());
        for(Person p : temp) {
            spawnNew2(p, r);
        }
    }

    private void checkIfLord() {
        for(Person p : world.getLords()) {
            p.getLordinfo().tryRemoveLord();
        }
    }


    public void playerTurn () {

    }

    public static boolean hasTrait(Person target, String traitName) {
        for(int x = 0; x < target.getTraits().size(); x++) {
            if(target.getTraits().get(x).getName().equals(traitName)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Check for expired dates on traits and remove if needed.
     */
    private void cleanTraits() {
        for(Person p : world.getLords()) {
            for(Traits t : p.getTraits()) {
                if(t.getExpireDate() != null) {
                    if (t.getExpireDate().getSeason().equals(world.getCurrentDate().getSeason())
                            && t.getExpireDate().getYear() == world.getCurrentDate().getYear()) {
                        p.getTraits().remove(t);
                    }
                }
            }
        }
        for(Person p: world.getPeasants()) {
            for(Traits t : p.getTraits()) {
                if(t.getExpireDate() != null) {
                    if (t.getExpireDate().getSeason().equals(world.getCurrentDate().getSeason())
                            && t.getExpireDate().getYear() == world.getCurrentDate().getYear()) {
                        p.getTraits().remove(t);
                    }
                }
            }
        }
    }

    private void refreshTroops() {
        for(County u : Game.getWorld().getCounties().values()) {
            int temp = 0;
            for(Traits t : u.getOwner().getTraits()) {
                temp += t.getTroopB();
            }
            u.addTroops((u.getTroopLevel() * 25) + temp);
            u.setDef((u.getDef() + u.getDefLevel() * 25) + temp);
        }
    }


    private void killRandom2(Person p, Random r) {
        if(p.getAge() < YOUNG && r.nextInt(100) < CHILD_DEATH_CHANCE){
            p.death();
        } else if (p.getAge() < OLD && p.getAge() >= YOUNG  && r.nextInt(100) < YOUNG_DEATH_CHANCE) {
            p.death();
        } else if (p.getAge() < ANCIENT && p.getAge() >= OLD && r.nextInt(100) < OLD_DEATH_CHANCE) {
            p.death();
        } else if (p.getAge() >= ANCIENT && r.nextInt(100) < ANCIENT_DEATH_CHANCE) {
            p.death();
        }
    }
    /**
     * THIS DOES NOT :: NOT :: CHECK FOR LORD DEATHS!!! INHERITANCE MUST BE CALLED SPERATELY
     */
    private void killRandom() {
        if(RANDOMDEATHS) {
            Random r = new Random();
            for(Person p : world.getPeasants()) {
                killRandom2(p, r);
            }
            for(Person p : world.getLords()) {
                killRandom2(p, r);
            }
        }
    }

    private List<Person> changedChildPor = new ArrayList<>();

    private void checkPortraits() {
        Random r = new Random();
        for(Person p : world.getPeasants()) {
            if(p.getAge() == 16 && p.getAlive() && !changedChildPor.contains(p)) {
                GeneratePeople.getPortrait(r, p, world);
            }
        }
        for(Person p : world.getLords()) {
            if(p.getAge() == 16 && p.getAlive() && !changedChildPor.contains(p)) {
                GeneratePeople.getPortrait(r, p, world);
            }
        }
    }

    private void checkAllies () {
        for(Person l : world.getLords()) {
            Set<LordInfo> allies = new HashSet<>();
            for(Person p : l.getRelations().getClose()) {
                if(p.getLordinfo() != null) {
                    allies.add(p.getLordinfo());
                }
            }
            allies.addAll(l.getLordinfo().getVassals());
            allies.remove(l.getLordinfo());
            l.getLordinfo().setAlliances(allies);
        }

    }

    private void addEvents() {
        Random r = new Random();
        for(Person p : world.getLords()) {
            if(EVENT_CHANCE < r.nextInt(100)) {
                Object[] events = world.getEvents().values().toArray();
                Event randomevent = (Event) events[r.nextInt(events.length)];
                p.getLordinfo().setLastEvent(randomevent);
            }
        }
    }

    private void giveGold() {
        for(County c : world.getCounties().values()) {
            c.getOwner().getLordinfo().addCash((int)c.getIncome());
        }
    }

    private List<Person> turnPassedList = new ArrayList<>();
    private Person startP;

    public void gameContinue() {
        Game.getGame().setMovables();
        if(world.getLords().size() == 0) {
            return;
        }
        Game.getGame().blockScene();
        if(lords.size() == 0) {
            seasonComplete();
            boolean playerFound = false;
            for(Person p : lords) {
                if(players.contains(p)) {
                    playerFound = true;
                    break;
                }
            }
            if(!playerFound) {
                restart();
                return;
            }
            // if after refresh too few lords remain, the game is over(0 is VERY unlikely, but possible)
            if(lords.size() < 1 || lords.size() == 1) {
                // TODO add game over screen.. maybe..
                restart();
                return;
            }
        }
        currentTurn = lords.get(0);
        lords.remove(0);
        if (players.contains(currentTurn)) {
            Game.getGame().closeSecondary();
            startSummary.summary(currentTurn);
            Game.getGame().setMovables();
        } else {
            //Game.getGame().blockScene();
            //System.out.println("IN AI TURN");
            try {
                ai.AIturn(currentTurn);
            } catch (Exception e) {
                System.out.println("AI turn failed: " + e);
            }
            gameOn();
        }
    }

    private List<Person> lords;

    private void seasonComplete() {
        System.out.println("SEASON HAS PASSED - NEW SEASON: " + world.getCurrentDate().getSeason());
        addEvents();
        checkWars();
        refreshTroops();
        doFightsAndSieges();
        moveUnits();
        //cleanTraits(); // clean traits DOESNT WORK right now, but since no traits use it, ignore it
        // fixed i think, should test if i ever use it
        // note: problem was that traits didnt get assigned expire dates by default but still got checked(nullpointer)
        killRandom(); // kill off old people
        checkDeaths(); // this is inheritance
        spawnNew(); // i never had this here.. this is why children didnt spawn..
        giveGold(); // gives income to owner of each county
        checkPortraits(); //add new portraits to children upon adulthood
        checkIfLord(); // usually does nothing but sometimes cleans up lords who lost all their lands
        checkAllies(); // resets all alliances
        lowerInfamy();
        world.passSeason();
        world.removeDuplicates();

        lords = new ArrayList<>();
        // TODO maybe add startP (start player) first, so that the start can be randomized
        // TODO decide if start in Harju is a feature or not... i guess its fine
        // TODO NEW IDEA! remake the lord list at game start after it is generated :D
        //   that way All players get random locations
            /*
            // this might work
            if(startP != null) {
                if(startP.getAlive()) {
                    Set<Person> temp = new HashSet<>();
                    temp.add(startP);
                }
            }
            */

        lords.addAll(world.getLords());
    }

    public void gameOn() {
        // ########################### ENTRY POINT INTO THIS CLASS
        //                              TODO: Stop forgetting stuff like this..
        if(lords == null) {
            // NOTE THAT THIS ONLY GETS CALLED ONCE, AT GAME START, WHEN THE LIST IS NOT INITIALIZED
            lords = new ArrayList<>();
            // TODO maybe add startP (start player) first, so that the start is randomized
            /*
            // this might work
            if(startP != null) {
                if(startP.getAlive()) {
                    Set<Person> temp = new HashSet<>();
                    temp.add(startP);
                }
            }
            */
            lords.addAll(world.getLords());
        }
        gameContinue();
    }

    public void restart() {
        System.out.println("restart");
        world.setLords(new ArrayList<>());
        Game.getGame().closeSecondary();
        lords = null;
        Game.getGame().getMapStage().close();
        Startup startup = new Startup();
        startup.startUp(Startup.getPrimaryStage());
    }

    public RunGame(int playerAmmount) {
        worldGen(playerAmmount);
    }

    private void worldGen(int playerAmmount) {
        //TODO When a game is loaded from saved file, check all traits and remove those that dont exist
        // TODO (custom traits might be modified and no longer exist!)
        //TODO generators for random family members for testing - NOTE: probably not needed anymore
        ai = new AI();
        world = GeneratePeople.PopulateWorld();
        for(Person p :world.getLords()) {
            changedChildPor.add(p);
        }
        for(Person p : world.getPeasants()) {
            changedChildPor.add(p);
        }
        world.makeColorMap();

        world.setGame(new Game(world, this));
        world.getGame().buildUI();

        for(Person c : world.getLords()) {
            // sets default law to Agnatic-Cognatic
            c.getLordinfo().setLaw(world.getLaws().get(1));
        }

        // sets lords to players for the ammount entered pre game, to a max of county ammount
        for(int x = 0; x < playerAmmount; x++) {
            if(x == world.getLords().size() || players.size() == playerAmmount) {
                break;
            }
            players.add(world.getLords().get(x));
        }
        System.out.println(players.size());
        //currentTurn = players.get(0); // TODO i wonder if commenting this out breaks everything..hmm.. o well.

        // TODO use smth like this to randomize player
        //Random r = new Random();
        //Integer result = r.nextInt(world.getLords().size());
        //Person player = world.getLords().get(result);
        //currentTurn = player;

        /* ****************************************** ENTRY POINT TO GAME ************************************** */
        gameOn();
    }




}
