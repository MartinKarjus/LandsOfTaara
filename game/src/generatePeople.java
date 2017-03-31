import world.*;

import java.util.*;

/**
 * Created by Martin Karjus 1 on 06/03/2017.
 */
public class generatePeople {
    // TODO add portraits to people
    //int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
    /*
    C_T = consort trait nr(min max)
    L_T = lord trait nr(min max)
     */
    private static final int C_T_Min = 1;
    private static final int C_T_Max = 5;
    private static final int L_T_Min = 1;
    private static final int L_T_Max = 6;
    private static final int CONSORT_CHANCE = 30;
    private static final int START_CASH = 30;

    public static World generateLords(World world) {
        List<String> strategies = new ArrayList<>();
        strategies.add("S");
        strategies.add("N");
        strategies.add("W");
        strategies.add("E");
        for (String key : world.getCounties().keySet()) {

            System.out.println(key + world.getCounties().get(key));
            Person temp = new Person();
            /*Random r = new Random();
            int Low = 10;
            int High = 100;
            int Result = r.nextInt(High-Low) + Low;*/
            Random r = new Random();
            Integer result = r.nextInt(100);
            if (result > 30) {
                temp.setGender("F");
                temp.setName(world.getNamesF().get(0));
                world.backF();
            } else {
                temp.setGender("M");
                temp.setName(world.getNamesM().get(0));
                world.backM();
            }
            temp.setSurname(world.getSurnames().get(0));
            world.backS();

            // random chance to gen consort
            result = r.nextInt(100);
            if (result <= CONSORT_CHANCE) {
                Person consort = new Person();
                consort.setConsort(temp);
                temp.setConsort(consort);
                consort.setTraits(genTraits(C_T_Min, C_T_Max, world));
                if(temp.getGender().equals("F")) {
                    consort.setGender("M");
                    consort.setName(world.getNamesF().get(0));
                    world.backF();
                    consort.setSurname(temp.getSurname());
                } else {
                    consort.setGender("F");
                    consort.setName(world.getNamesM().get(0));
                    world.backM();
                    consort.setSurname(temp.getSurname());
                }
                world.addPeasant(consort);
            }
            temp.setTraits(genTraits(L_T_Min, L_T_Max, world));

            ///////////////////////////////////////////// LORD STUFF
            LordInfo lordinfo = new LordInfo();
            lordinfo.addCounty(world.getCounties().get(key));
            lordinfo.setCash(START_CASH);
            result = r.nextInt(3);
            lordinfo.setStrategy(strategies.get(result));
            temp.setLordinfo(lordinfo);
            System.out.println("COUNTIES:" + world.getCounties());
            System.out.println("LOOKING AT:" + world.getCounties().get(key));
            world.setOwner(key,temp);
            world.addLord(temp);
        }

        return world;
    }

    public static List<Traits> genTraits(Integer min, Integer max, World world) {
        List<Traits> temp = new ArrayList<>();
        Random r = new Random();
        Integer nrOfTraits = r.nextInt(max - min) + min;
        List<String> keys = new ArrayList<String>(world.getTraits().keySet());
        for (int x = 0; x < nrOfTraits; x++) {
            String randomKey = keys.get(r.nextInt(keys.size()) );
            temp.add(world.getTraits().get(randomKey));
        }
        return temp;
    }
    public static Person generateChild() {
        return new Person();
    }

    public static World PopulateWorld() {
        World world = new World();
        world.readAll("firstNamesF.txt", "firstNamesM.txt", "surnames.txt",
                "traits.txt", "events.txt", "counties.txt");
        world = generateLords(world);
        return world;
    }

    public static void main(String[] args) {

    }
}
