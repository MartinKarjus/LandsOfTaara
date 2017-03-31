package world;

import world.County;
import world.Event;
import world.Traits;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
// ./resources/yourfile.txt
public class readPremade {
    // TODO add reading of portrait pictures
    /**
     * Use for reading firstnames(F and M) and surnames.
     * @param fileName Name of the txt file in resource folder that needs to be read.
     * @return List of names.
     */
    public static LinkedList<String> names(String fileName) {
        fileName = "./resources/" + fileName;
        LinkedList<String> nameL = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(nameL::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameL.removeAll(Arrays.asList("", null));
        return nameL;
    }

    /**
     * Read traits.
     * @param fileName should be traits.txt.
     * @return Linked map of traits with <Name, traitID>.
     */
    public static Map<String, Traits> traits(String fileName) {
        fileName = "./resources/" + fileName;
        Map<String, Traits> traits = new LinkedHashMap<String, Traits>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            String name = null;
            Integer angry = 0;
            Integer troopB = 0;
            Integer goodEventB = 0;
            Integer warB = 0;
            Integer lust = 0;
            Integer ambition = 0;
            Integer good = 0;
            Boolean visible = true;
            String opposing = null;
            String icon = null;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll(";", "\n");
                if(line.contains("END OF READ")) {
                    break;
                }
                if(line.contains("#") || line.contains("{")) {
                    continue;
                }
                if(line.contains("}")) {
                        traits.put(name, new Traits(name, angry, troopB, goodEventB, warB, lust, ambition, good, visible, opposing, icon));
                        name = null;
                        angry = 0;
                        troopB = 0;
                        goodEventB = 0;
                        warB = 0;
                        lust = 0;
                        ambition = 0;
                        good = 0;
                        visible = true;
                        opposing = null;
                        icon = null;
                }
                if(line.contains("name:")) {
                    name = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("angry:")) {
                    angry = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("troopB:")) {
                    troopB = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("goodEventB:")) {
                    goodEventB = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("warB:")) {
                    warB = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("visible:")) {
                    if((line.substring(line.indexOf(":") + 1)).equals("N")) {
                        visible = false;
                    } else {
                        visible = true;
                    }
                }
                if(line.contains("lust:")) {
                    lust = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("ambition:")) {
                    ambition = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("good:")) {
                    good = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("opposing:")) {
                    opposing = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("icon:")) {
                    icon = line.substring(line.indexOf(":") + 1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return traits;
    }

    /**
     * Read events.
     * @param fileName events.txt.
     * @return Linked map where <eventID, EventObject>
     */
    public static Map<String, Event> events(String fileName) {
        fileName = "./resources/" + fileName;
        Map<String, Event> events = new LinkedHashMap<String, Event>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            String ID = null;
            String heading = null;
            String text = null;
            String debugTxt = null;
            String picName = null;
            Boolean war = null;
            List<String> effects = new ArrayList<>();
            List<String> choices = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll(";", "\n");
                if(line.contains("END OF READ")) {
                    break;
                }
                if(line.contains("#") || line.contains("{")) {
                    continue;
                }
                if(line.contains("}")) {
                        events.put(ID, new Event(ID, heading, text, debugTxt, picName, war, choices, effects));
                        ID = null;
                        heading = null;
                        text = null;
                        debugTxt = null;
                        picName = null;
                        war = null;
                        effects = new ArrayList<>();
                        choices = new ArrayList<>();
                }
                if(line.contains("ID:")) {
                    ID = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("heading:")) {
                    heading = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("text:")) {
                    text = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("debugTxt:")) {
                    debugTxt = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("picName:")) {
                    picName = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("war:")) {
                    if((line.substring(line.indexOf(":") + 1)).equals("N")) {
                        war = false;
                    } else {
                        war = true;
                    }
                }
                if(line.contains("choice:")) {
                    choices.add(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("effect:")) {
                    effects.add(line.substring(line.indexOf(":") + 1));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }
    public static Map<String, County> counties(String fileName) {
        fileName = "./resources/" + fileName;
        Map<String, County> counties = new LinkedHashMap<String, County>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            String name = null;
            Integer def = 0;
            Integer troops = 0;
            Double income = 0.0;
            while ((line = br.readLine()) != null) {
                /*#name:#NAME#
                #def:#NR#
                #troops:#NR#
                #income:#FLOAT#*/
                line = line.replaceAll(";", "\n");
                if(line.contains("END OF READ")) {
                    break;
                }
                if(line.contains("#") || line.contains("{")) {
                    continue;
                }
                if(line.contains("}")) {
                    counties.put(name, new County(name, def, troops, income));
                    name = null;
                    def = 0;
                    troops = 0;
                    income = 0.0;
                }
                if(line.contains("name:")) {
                    name = line.substring(line.indexOf(":") + 1);
                }
                if(line.contains("def:")) {
                    def = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("troops:")) {
                    troops = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("income:")) {
                    income = Double.parseDouble(line.substring(line.indexOf(":") + 1));
                }
                if(line.contains("connected:")) {
                    String[] con = line.substring(line.indexOf(":") + 1).split("&");
                    counties.get(con[0]).addCon(counties.get(con[1]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return counties;
    }
    public static void main(String args[]) {
        //List<String> testL = names("readingpremade/src/testcase.txt");
        //List<String> testL = traits("testcase.txt");
        /*
        Map<String, Event> mappy = events("events.txt");
        for (String key : mappy.keySet()) {
            System.out.println(key + " " + mappy.get(key));
            System.out.println(mappy.get(key).getID());
            System.out.println(mappy.get(key).getText());
            System.out.println(mappy.get(key).getDebugTxt());
            System.out.println(mappy.get(key).getHeading());
            System.out.println(mappy.get(key).getEffect());
            System.out.println(mappy.get(key).getWar());
        }
        */
        /*
        Map<String, Traits> mappy = traits("traits.txt");
        for (String key : mappy.keySet()) {
            System.out.println(key + " " + mappy.get(key));
            System.out.println(mappy.get(key).getName());
            System.out.println(mappy.get(key).getAmbition());
            System.out.println(mappy.get(key).getWarB());
            System.out.println(mappy.get(key).getTroopB());
            System.out.println(mappy.get(key).getGood());
            System.out.println(mappy.get(key).getIcon());
        }
        */

        //for (int x = 0; x < testL.size(); x++) {
          //  System.out.println(testL.get(x));
        //}
    }
    /*
    Map<String, TstC> mappy = new LinkedHashMap<String, TstC>();
        mappy.put("bla", new TstC("meh"));
        mappy.put("trolololo", new TstC("mehlet"));
        for (String key : mappy.keySet()) {
            System.out.println(key + mappy.get(key));
        }
        System.out.println(mappy);
     */
}
