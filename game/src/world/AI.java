package world;


import game.GeneratePeople;
import game.RunGame;
import inGame.Game;
import menuDiplomacy.diploController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AI {

    public static boolean aiOn = true;
    public static final int GREEDY_EXTRA_BUILD_CHANCE = 20;
    public static final int BASE_BUILD_CHANCE = 50;

    public static final int BASE_MARRY_CHANCE = 5;
    public static final int EXTRA_MARRY_NO_CHILDREN = 20;
    public static final int OPINION_MARRY_TRESHHOLD = 50;
    public static final boolean PREFER_HIGH_INFAMY = true; // try to ally agressive neighbors to save yourself
    public static final int BASE_MARRY_FAMILY_MEMBERS = 10; // chance to marry others than yourself
    public static final int MIN_MARRY_AGE = 16;

    public static final int BASE_MURDER_CHANCE = 2;
    public static final int NASTYNESS_THRESSHOLD = 10; // a person with certain traits is more likely to start a murder spree
    public static final int NASTY_EXTRA_MURDER = 30;

    public static final int BASE_WAR_CHANCE = 10;
    public static final int CHANCE_TO_NOT_MOVE_DEFENDER = 25; // defender has a chance to not attack but stay and defend
    // if wants to wage war, attacks a target that has infamy above threshhold, if such a target exists
    public static final int INFAMY_TRIGGERED_WAR_THRESHHOLD = 75;

    public static int increasingWarChance = 0; // chance for war increases for every lord who stays at peace
    public static final int WAR_DECREASE = 10; // chance for war gets decreased by this each time war is declared
    public static final int WAR_INCREASE_TICK = 1; // ammount peace increases war chance


    private int getMurderChance(Person AI) {
        int temp = 0;
        for(Traits t : AI.getTraits()) {
            temp += t.getAngry();
            temp += t.getAmbition();
            temp -= t.getGood();
        }
        if(temp >= NASTYNESS_THRESSHOLD) {
            temp += NASTY_EXTRA_MURDER;
        }
        temp += BASE_MURDER_CHANCE;
        return temp;
    }

    private int getWarChance(Person AI) {
        int temp = 0;
        for(Traits t : AI.getTraits()) {
            temp += t.getAngry();
            temp += t.getAmbition();
            temp -= t.getGood();
            temp += t.getWarB();
        }
        temp += BASE_WAR_CHANCE;
        temp += increasingWarChance;
        return temp;
    }

    private boolean canMarry(Person a, Person b) {
        if(a.getRelations().getConsort() != null || b.getRelations().getConsort() != null) {
            return false;
        }
        if(a.getGender().equals(b.getGender())) {
            return false;
        }
        if(a.getAge() < MIN_MARRY_AGE || b.getAge() < MIN_MARRY_AGE) {
            return false;
        }
        if(!a.getAlive() || !b.getAlive()) {
            return false;
        }
        return true;
    }

    private void marry(Person a, Person b) {
        a.getRelations().setConsort(b);
        b.getRelations().setConsort(a);
    }

    private boolean tryMarry(Person person, Person lord) {
        List<Person> neighbors = getNeighbors(lord);

        List<Person> peasants = new ArrayList<>();
        HashMap<Person, Person> peasantLord = new HashMap<>();

        Person marryTarget = null;
        for(Person l : neighbors) {
            peasants.addAll(l.getRelations().getCloseUnmarriedLandless());
            peasants.remove(l);
            for(Person p : peasants) {
                peasantLord.put(p, l);
            }
            if(canMarry(person, l)) {
                if(l.getLordinfo().willMarry(lord.getLordinfo())) {
                    if(marryTarget == null) {
                        marryTarget = l;
                        if(!PREFER_HIGH_INFAMY) {
                            break;
                        }
                    } else if(marryTarget.getLordinfo().getInfamy() < l.getLordinfo().getInfamy()) {
                        marryTarget = l;
                    }
                }
            }
        }
        if(marryTarget != null) {
            marry(person, marryTarget);
            return true;
        }
        for(Person p : peasantLord.keySet()) {
            if(canMarry(person, p)) {
                if(peasantLord.get(p).getLordinfo() != null) {
                    if(peasantLord.get(p).getLordinfo().willMarry(lord.getLordinfo())) {
                        marryTarget = p;
                        break;
                    }
                }
            }
        }
        if(marryTarget != null) {
            marry(person, marryTarget);
            return true;
        }
        return false;
        // TODO look beyond neighbors.. maybe?
    }


    public static ArrayList<County> findPath(County from, County target) {
        ArrayList<County> path = new ArrayList<>();
        Map<County, Integer> values = new HashMap<>();
        List<County> toScout = new ArrayList<>();
        toScout.add(from);
        values.put(from, 0);
        boolean found = false;
        Integer at = 1;

        for (Map.Entry<County, Integer> entry : values.entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
        //System.out.println("*********************");

        boolean foundSmth = false;
        while(!found) {
            foundSmth = false;
            //System.out.println("**************");
            //System.out.println(" at  " + at);
            for (Map.Entry<County, Integer> entry : values.entrySet()) {
                System.out.println(entry.getKey().getName() + " " + entry.getValue());
            }
            //System.out.println("**************");
            if(at > RunGame.TOO_LONG_ROAD) {
                System.out.println("NO ROAD FOUND");
                return null;
            }
            for(County c : toScout.get(0).getConnected()) {
                if(c == target) {
                    values.put(target, values.get(toScout.get(0)) + 1);
                    found = true;
                    break;
                }
                if(!values.containsKey(c)) {
                    values.put(c, values.get(toScout.get(0)) + 1);
                    toScout.add(c);
                }
            }
            toScout.remove(0);
        }

        for (Map.Entry<County, Integer> entry : values.entrySet()) {
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
        //System.out.println("*******************");
        System.out.println("finished");

        County current = target;
        int best = -1;
        at = values.get(target);
        while(at != 0) {
            for (County key : values.keySet()) {
                // System.out.println(key + world.getCounties().get(key));
                if(values.get(key) == (at-1) && key.getConnected().contains(current)) {
                    at -= 1;
                    path.add(key);
                    current = key;
                    break;
                }
            }
            /*
            best = -1;
            List<County> temp = current.getConnected();
            for(County c : temp) {
                if(values.keySet().contains(c)) {
                    if(best == -1) {
                        current = c;
                        best = values.get(c);
                    } else if (values.get(c) < best) {
                        current = c;
                    }
                }
            }
            path.add(current);
            */
        }
        Collections.reverse(path);
        path.remove(from);
        if(!path.contains(target)) {
            path.add(target);
        }

        System.out.println("From " + from.getName() + " to " + target.getName());
        for(County c : path) {
            System.out.println(c.getName());
        }
        System.out.println("");

        return path;
    }

    public static void main(String[] args) {
        World world = GeneratePeople.PopulateWorld();
        ArrayList<County> path = findPath(world.getCounties().get("Hiiu"), world.getCounties().get("Lääne"));
        for(County c : path) {
            System.out.println(c.getName());
        }
    }

    private void upgradeHoldings(Person AI) {
        // Upgrade holdings
        Integer cash = AI.getLordinfo().getCash();
        List<County> counties = AI.getLordinfo().getCounties();
        for (int x = 0; x < counties.size(); x++) {
            cash = AI.getLordinfo().getCash();
            if (cash >= counties.get(x).getIncomeLevel() * 100) {
                AI.getLordinfo().getCounties().get(x).upgrade("income", AI.getLordinfo());
            } else if (cash >= counties.get(x).getTroopLevel() * 100) {
                AI.getLordinfo().getCounties().get(x).upgrade("troop", AI.getLordinfo());
            } else if (cash >= counties.get(x).getDefLevel() * 100) {
                AI.getLordinfo().getCounties().get(x).upgrade("def", AI.getLordinfo());
            }
        }
    }

    private void murderSomeone(Person AI) {
        /*
        Current strategy is to murder all parents of those close to you(increasing chance of your family inheriting)
        Also murders spouse if you have children and spouse is a ruler(increasing the power in the family)

        If nastyness is above threshold, also murder a random lord(might make u inherit his lands, good strat btw :) )
         */
        if(AI.getLordinfo().getCash() >= diploController.MURDER_COST) {
            List<Person> targets = new ArrayList<>();
            Random r = new Random();
            if(AI.getRelations().getChildren().size() > 0 && AI.getRelations().getConsort() != null) {
                if(AI.getRelations().getConsort().getLordinfo() != null) {
                    // if have children and spouse is ruler, kill her/him!
                    targets.add(AI.getRelations().getConsort());
                }
            }
            for(Person p : AI.getRelations().getClose()) {
                if(Person.isLord(p.getRelations().getFather())) {
                    targets.add(p.getRelations().getFather());
                }
                if(Person.isLord(p.getRelations().getMother())) {
                    targets.add(p.getRelations().getMother());
                }
            }

            if(!(targets.size() > 0) && getMurderChance(AI) >= NASTYNESS_THRESSHOLD) {
                targets.add(Game.getWorld().getLords().get(r.nextInt(Game.getWorld().getLords().size())));
            }

            targets.remove(AI);
            targets.removeAll(AI.getRelations().getChildren());

            if(targets.size() > 0) {
                AI.getLordinfo().addCash(-diploController.MURDER_COST);
                targets.get(r.nextInt(targets.size())).death();
                System.out.println("AI commited murder :O");
            }
        }
    }

    private List<Person> getNeighbors(Person AI) {
        List<Person> temp = new ArrayList<>();
        for(County c : AI.getLordinfo().getCounties()) {
            for(County t : c.getConnected()) {
                if(t.getOwner() != AI) {
                    temp.add(t.getOwner());
                }
            }
        }
        return temp;
    }

    public AI() {
        increasingWarChance = 0;
    }

    public void AIturn (Person AI) {
        /*
        //TODO maybe assassinate before upgrade to not waste gold!
        upgrade buildings if over 100 gold and sufficient for upgrade
        TODO find marriage partner for(prio self, prio sons, daughters to nearby good relations)
            TODO max marriages based on ambition from traits, any1 more than 3+(sizeOfOwnrealm/2) away is ignored
            TODO score for marriage = common hatered(hatered = < 0 relations, +

            find alliances, preferring closer
        check to declare war, prefer closer.
         */
        if(!aiOn) {
            System.out.println("AI turn - skipping");
            return;
        }

        System.out.println("                                     IN AI: " + AI.getName() + " " + AI.getSurname() + " :: " + AI.getLordinfo().getCounties().get(0).getName());

        // MURDER
        Random r = new Random();
        if(getMurderChance(AI) > r.nextInt(100)) {
            murderSomeone(AI);
        }

        // UPGRADE
        if(AI.getTraits().contains("Greedy")) {
            if(BASE_BUILD_CHANCE + GREEDY_EXTRA_BUILD_CHANCE > r.nextInt(100)) {
                upgradeHoldings(AI);
            }
        } else if (BASE_BUILD_CHANCE > r.nextInt(100)){
            upgradeHoldings(AI);
        }

        // MARRY
        int temp = 0;
        if(AI.getRelations().getChildren().size() == 0) {
            temp += EXTRA_MARRY_NO_CHILDREN;
        }
        if(BASE_MARRY_CHANCE + temp > r.nextInt(100)) {
            tryMarry(AI, AI);
        }
        if(AI.getRelations().getCloseUnmarriedLandless().size() > 0) {
            if(BASE_MARRY_FAMILY_MEMBERS > r.nextInt(100)) {
                for(Person p : AI.getRelations().getCloseUnmarriedLandless()) {
                    boolean married = tryMarry(p, AI);
                    if(married) {
                        break;
                    }
                }
            }
        }

        LordInfo l = AI.getLordinfo();
        // START WARS
        if(l.getWars().size() == 0) {
            if(getWarChance(AI) > r.nextInt(100)) {

                List<Person> neighbors = getNeighbors(AI);
                if(neighbors.size() == 0) {
                    System.out.println("FAILED TO FIND NEIGHBORS, EXITING AI");
                    return;
                }
                String warType;
                if(r.nextBoolean()) {
                    warType = "Raid";
                } else {
                    warType = "Conquest";
                }
                Person target = neighbors.get(r.nextInt(neighbors.size()));
                for(Person p : Game.getWorld().getLords()) {
                    if(p.getLordinfo().getInfamy() > INFAMY_TRIGGERED_WAR_THRESHHOLD) {
                        target = p;
                        break;
                    }
                }
                Game.getWorld().addWar(new War(AI.getLordinfo(),
                        target.getLordinfo(),
                        target.getLordinfo().getCounties().get(0),
                        Game.getRunGame().getCurrentTurn().getLordinfo().getCounties().get(0),
                        warType));
                increasingWarChance += WAR_DECREASE;
            } else {
                increasingWarChance += WAR_INCREASE_TICK;
            }
        }

        // DEAL WITH CURRENT WARS
        if(l.getWars().size() > 0) {
            boolean validTarget = false;
            if(l.getWarTargetAI() != null) {
                for(War w : l.getWars()) {
                    if(l.getWarTargetAI() == w.getDefenseTarget() || l.getWarTargetAI() == w.getAttackTarget()) {
                        validTarget = true;
                    }
                }
            }
            if(!false) {
                l.setWarTargetAI(null);
            }

            if(!l.isArmyRaised()) {
                l.raiseArmy();
            }
            if(l.getWarTargetAI() == null) {
                for(War w : l.getWars()) {
                    // TODO make it favor targets close to army
                    if(w.getDefenders().contains(l)) {
                        l.setWarTargetAI(w.getDefenseTarget());
                        if(w.getDefender() == l && CHANCE_TO_NOT_MOVE_DEFENDER > r.nextInt(100)) {
                            l.setWarTargetAI(w.getAttackTarget());
                            System.out.println("AI CHOSE TO DEFEND AT: " + l.getWarTargetAI().getName());
                        }
                        break;
                    } else if (w.getAttackers().contains(l)) {
                        l.setWarTargetAI(w.getAttackTarget());
                        break;
                    }
                }
                System.out.println("AI TARGET IN WAR IS: " + l.getWarTargetAI().getName());
            }
            List<Unit> units = new ArrayList<>();
            for(Unit u : Game.getWorld().getUnits()) {
                if(u.getOwner() == AI) {
                    units.add(u);
                }
            }
            for(Unit u : units) {
                if(u.getAt() != l.getWarTargetAI()) {
                    ArrayList<County> path = findPath(u.getAt(), l.getWarTargetAI());
                    System.out.println("WANT TO GO THROUGH: ");
                    for(County c : path) {
                        System.out.println("     path:  " + c.getName());
                    }
                    if(path.size() > 0) {
                        u.moveTo(path.get(0));
                        System.out.println("Moving from - " + u.getAt().getName() + " to - " + path.get(0).getName());
                    }

                }
            }
        } else if (l.isArmyRaised()) {
            // lower army if not at war
            l.lowerArmy();
        }

    }
}
