/**
 * Created by Martin Karjus 1 on 14/03/2017.
 */
import world.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class runGame {
    /** Road probably ran into infinite loop(maybe invalid connections initialized?). */
    public static final int TOO_LONG_ROAD = 1000;
    public static final int EVENT_CHANCE = 50;

    // reads files and generates people for counties
    private static World world;

    public static void inherit(Person deadGuy) {

    }

    public static void playerTurn (Person player) {

    }

    public static ArrayList<County> findPath(County from, County target) {
        Map<County, Integer> values = new HashMap<>();
        List<County> toScout = new ArrayList<>();
        toScout.add(from);
        values.put(from, 0);
        boolean found = false;
        Integer at = 0;
        while(!found) {
            at++;
            if(at > TOO_LONG_ROAD) {
                System.out.println("NO ROAD FOUND");
                return null;
            }
            for(int x = 0; x < toScout.get(0).getConnected().size(); x++) {
                if(toScout.get(0).getConnected().get(x) == target) {
                    values.put(target, at);
                    found = true;
                    break;
                }
                if(!values.containsKey(toScout.get(0).getConnected().get(x))) {
                    values.put(toScout.get(0).getConnected().get(x), at);
                }
            }
            toScout.remove(0);
            toScout.addAll(toScout.get(0).getConnected());
        }
        while(at != 0) {
            for (County key : values.keySet()) {
                // System.out.println(key + world.getCounties().get(key));
                if(key < )
            }
        }
    }

    public static void AIturn (Person AI) {
        /*
        upgrade buildings if over 100 gold and sufficient for upgrade.
        find marriage partner for(prio self, prio sons, daughters to nearby good relations)
            find alliances, preferring closer
        TODO make path finder between counties
        check to declare war, prefer closer
         */
        if (AI.getLordinfo().getCash() > 100) {
            List<County> counties = AI.getLordinfo().getCounties();
            for (int x = 0; x < counties.size(); x++) {
                if (AI.getLordinfo().getCash() > counties.get(x).getIncomeLevel()*100) {
                    AI.getLordinfo().getCounties().get(x).upgrade("income");
                } else if (AI.getLordinfo().getCash() > counties.get(x).getDefLevel()*100) {
                    AI.getLordinfo().getCounties().get(x).upgrade("def");
                } else if (AI.getLordinfo().getCash() > counties.get(x).getTroopLevel()*100) {
                    AI.getLordinfo().getCounties().get(x).upgrade("troop");
                }
            }
        }

        for (int x = 0; x < world.getLords().size(); x++) {
            //Integer distance
        }
    }


    public static void gameOn(Person player) {
        /*call gameOn(World, player) // player = some1 from Lords list
        while()
            for(personList)
                if personlist[x] == player
        if at war, check army loc, if at enemy cap or
        enemy army met, call event
        call a random event with 50% chance, 4 teh lulz(thats not war event)
        call playerTurn(player, traitList, countyList)
			else
        call AITurn(personList[x], traitList, countyList)
        call armiesTurn(personList[x],
                check ages, remove random old, add children randomly
		*** inheritances happen on death, if noone inherits a county the county is assigned to new random
                *** new people born get added to Person not Lord(Person subclass) class
		*** inheritors get assigned to Lords
                ***** births happen before deaths*/
        while(player != null) {
            //gets reset each round, all counties owned by a lord whose turn it is get passed in here
            // and already visited counties get ignored
            List<County> ownerPassed = new ArrayList<>();

            if (!player.getAlive() || player.getLordinfo().getCounties().size() == 0) {
                break;
            }

            for (String key : world.getCounties().keySet()) {

                // since some lords may cause the untimely demise of others after each turn all lives are checked
                // possibly redundant later
                for (int x = 0; x < world.getLords().size(); x++) {
                    if (!world.getLords().get(x).getAlive()) {
                        inherit(world.getLords().get(x));
                    }
                }
                if (!ownerPassed.contains(world.getCounties().get(key))) {
                    if (world.getCounties().get(key).getOwner() == player) {
                        playerTurn(player);
                    } else {
                        AIturn(world.getCounties().get(key).getOwner());
                    }
                }



                ownerPassed.addAll(world.getCounties().get(key).getOwner().getLordinfo().getCounties());
            }
        }

        System.out.println("ERROR: DEATH ENCOUNTERED, STOP CAUSING YOUR OWN DEATH");

    }

    public static void worldGen() {
        world = generatePeople.PopulateWorld();
        Random r = new Random();
        Integer result = r.nextInt(world.getLords().size());
        Person player = world.getLords().get(result);
        gameOn(player);
    }


    public static void main(String[] args) {
        world = generatePeople.PopulateWorld();
        System.out.println(world.getCounties());
        System.out.println(world.getPeasants());
        List<Person> ppl;
        ppl = world.getLords();
        System.out.println("--------------");
        for(int x = 0; x < ppl.size(); x++) {
            ppl.get(x).getName();
            List<County> cnts = ppl.get(x).getLordinfo().getCounties();
            for(int y = 0; y < cnts.size(); y++) {
                System.out.println(cnts.get(y).getName());
            }
        }
        System.out.println("------------");
        ppl = world.getPeasants();
        for(int x = 0; x < ppl.size(); x++) {
            System.out.println(ppl.get(x).getName());
        }
        System.out.println(world.getLords());

        Map<String, County> mappy = world.getCounties();
        for (String key : world.getCounties().keySet()) {
            System.out.println(key + " " + world.getCounties().get(key).getOwner().getName());
        }
    }


}
