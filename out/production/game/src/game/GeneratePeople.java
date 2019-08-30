package game;

import javafx.scene.paint.Color;
import world.*;

import java.util.*;

/**
 * Created by Martin Karjus 1 on 06/03/2017.
 */
public class GeneratePeople {
    // TODO add portraits to people

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
    private static final int FEMALE_CHANCE = 30;
    private static final int AGE_MIN = 16;
    private static final int AGE_MAX = 60;
    // NOTE SELF: should add lower bound, could currently end up with 16 yr old married to 12 yr old
    private static final int CONSORT_AGE_VARIATION = 4;



    private static YearSeason birthDate(int age, YearSeason start) {
        return (new YearSeason(start.getSeason(), start.getYear() - age));
    }

    public static void getPortrait(Random r, Person temp, World world) {
        int result;
        if(temp.getGender().equals("M")) {
            result = r.nextInt(world.getPortraits().get("M").size());
            temp.setPortrait(world.getPortraits().get("M").get(result));
        } else if(temp.getGender().equals("F")) {
            result = r.nextInt(world.getPortraits().get("F").size());
            temp.setPortrait(world.getPortraits().get("F").get(result));
        }
    }

    /**
     * Populate the world with lords.
     * @param world Game world.
     * @return Updated world.
     */
    private static World generateLords(World world) {

        List<String> strategies = new ArrayList<>();
        strategies.add("S");
        strategies.add("N");
        strategies.add("W");
        strategies.add("E");

        Random r = new Random();

        for (String key : world.getCounties().keySet()) {

            //System.out.println(key + world.getCounties().get(key));
            Person temp = new Person();
            Integer result = r.nextInt(100);

            //decide gender
            if (result < FEMALE_CHANCE) {
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
            // age
            result = r.nextInt(AGE_MAX - AGE_MIN);
            result += AGE_MIN;
            temp.setBirthdate(birthDate(result, world.getStartDate()));

            //get a portrait
            getPortrait(r, temp, world);


            // sometimes generate consort
            result = r.nextInt(100);
            if (result <= CONSORT_CHANCE) {
                Person consort = new Person();
                result = r.nextInt(CONSORT_AGE_VARIATION*2);
                result -= CONSORT_AGE_VARIATION;
                consort.setBirthdate(new YearSeason(temp.getBirthdate().getSeason(), temp.getBirthdate().getYear() + result));
                consort.getRelations().setConsort(temp);
                temp.getRelations().setConsort(consort);
                consort.setTraits(genTraits(C_T_Min, C_T_Max, world));
                if(temp.getGender().equals("F")) {
                    consort.setGender("M");
                    consort.setName(world.getNamesM().get(0));
                    world.backM();
                    consort.setSurname(temp.getSurname());
                } else {
                    consort.setGender("F");
                    consort.setName(world.getNamesF().get(0));
                    world.backF();
                    consort.setSurname(temp.getSurname());
                }
                world.addPeasant(consort);
                getPortrait(r, consort, world);
            }

            //set some traits
            temp.setTraits(genTraits(L_T_Min, L_T_Max, world));

            ///////////////////////////////////////////// LORD STUFF
            LordInfo lordinfo = new LordInfo();

            if (world.getLordColors().size() != 0) {
                result = r.nextInt(world.getLordColors().size());
                lordinfo.setColor(world.getLordColors().get(result));
                world.removeColor(world.getLordColors().get(result));
            } else {
                lordinfo.setColor(Color.web("0xFF0000ff"));
            }
            lordinfo.addCounty(world.getCounties().get(key));
            lordinfo.setCash(START_CASH);
            result = r.nextInt(3);
            lordinfo.setStrategy(strategies.get(result));
            temp.setLordinfo(lordinfo);
            //System.out.println("COUNTIES:" + world.getCounties());
            //System.out.println("LOOKING AT:" + world.getCounties().get(key));
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
        int loop = 0;

        for (int x = 0; x < nrOfTraits; x++) {
            String randomKey = "";
            boolean found = false;

            // ignore opposite traits
            while(!found) {
                found = true;
                loop += 1;
                if(loop > 200) {
                    return temp;
                }
                randomKey = keys.get(r.nextInt(keys.size()) );
                for(Traits t : temp) {
                    if(t.getOpposing().equals(world.getTraits().get(randomKey).getName())) {
                        found = false;
                    }
                }
            }

            temp.add(world.getTraits().get(randomKey));
        }

        return temp;
    }

    private static void genColors(World world) {
        String s = "#000000\t(0,0,0)\n" +
                " \tWhite\t#FFFFFF\t(255,255,255)\n" +
                " \tRed\t#FF0000\t(255,0,0)\n" +
                " \tBlue\t#0000FF\t(0,0,255)\n" +
                " \tYellow\t#FFFF00\t(255,255,0)\n" +
                " \tCyan / Aqua\t#00FFFF\t(0,255,255)\n" +
                " \tMagenta / Fuchsia\t#FF00FF\t(255,0,255)\n" +
                " \tSilver\t#C0C0C0\t(192,192,192)\n" +
                " \tGray\t#808080\t(128,128,128)\n" +
                " \tMaroon\t#800000\t(128,0,0)\n" +
                " \tOlive\t#808000\t(128,128,0)\n" +
                " \tGreen\t#008000\t(0,128,0)\n" +
                " \tPurple\t#800080\t(128,0,128)\n" +
                " \tTeal\t#008080\t(0,128,128)\n" +
                " \tNavy\t#000080\t(0,0,128)";
        String[] sl = s.split("\t");
        for (int x = 0; x < sl.length; x++) {
            if (sl[x].contains("#")) {
                world.addColor(Color.web("0x" + sl[x].substring(1) + "ff", 1));
            }
        }
    }


    public static World PopulateWorld() {
        World world = new World(new YearSeason("P", 1218));

        genColors(world);
        world.readAll("firstNamesF.txt", "firstNamesM.txt", "surnames.txt",
                "traits.txt", "events.txt", "counties.txt");
        world = generateLords(world);
        return world;
    }

    public void main(String[] args) {

    }
}
