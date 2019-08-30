package world;

import inGame.Game;
import javafx.scene.paint.Color;

import java.util.*;


/**
 * Manually input values:
 *
 * warTypes.add("Raid");
 * warTypes.add("Conquest");
 * warTypes.add("Force vassalization");
 * warTypes.add("Independance");
 * warTypes.add("Defense"); // not used yet
 *
 *
 * TODO logic for all laws is done, should add way to change them
 * laws.add("Agnatic");
 * laws.add("Agnatic-Cognatic");
 * laws.add("Cognatic");
 *
 *
 *
 *
 *
 */


/**
 * The objects in the game world.
 * One day, a class like this will contain us all...
 */
public class World {
    private List<String> laws = new ArrayList<>();
    private Game game;
    private List<Person> lords = new ArrayList<>();
    private List<Person> peasants = new ArrayList<>();
    private LinkedList<String> namesF = new LinkedList<>();
    private LinkedList<String> namesM = new LinkedList<>();
    private LinkedList<String> surnames = new LinkedList<>();
    private Map<String, Traits> traits;
    private Map<String, Event> events;
    private Map<String, County> counties;
    private Set<Unit> units = new HashSet<>();
    private Map<String, List<String>> portraits = new HashMap<>();
    private Map<String, County> colorCounty = new HashMap<>();
    private YearSeason startDate;
    private YearSeason currentDate;
    private List<Color> lordColors = new ArrayList<>();
    private Set<String> warTypes = new HashSet<>();
    private Set<War> wars = new HashSet<>();
    private int warCounter;

    public void addWarCounter(int i) {
        warCounter += i;
    }
    public int getWarCounter() {
        return warCounter;
    }


    public void setLords(List<Person> lords) {
        this.lords = lords;
    }

    public Set<War> getWars() {
        return wars;
    }

    public void addWar(War war) {
        this.wars.add(war);
    }

    public void removeWar(War war) {
        this.wars.remove(war);
    }

    public Set<String> getWarTypes() {
        return warTypes;
    }

    public List<String> getLaws() {
        return laws;
    }

    public Set<Unit> getUnits() {
        return units;
    }
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public List<Color> getLordColors() {
        return lordColors;
    }

    public void addColor(Color color) {
        lordColors.add(color);
    }

    public void removeColor(Color color) {
        lordColors.remove(color);
    }

    public void backF() {
        namesF.offer(namesF.poll());
    }
    public void backM() {
        namesM.offer(namesM.poll());
    }
    public void backS() {
        surnames.offer(surnames.poll());
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Set owner of county to person.
     * @param key Target county.
     * @param owner Person.
     */
    public void setOwner(String key, Person owner) {
        counties.get(key).setOwner(owner);
    }
    /**
     * On birth and generation of non-lords, People get passed in here.
     * @param newPerson Another poor soul.
     */
    public void addPeasant(Person newPerson) {
        peasants.add(newPerson);
    }

    /**
     * Used on Lord generation and events.
     * @param newPerson He's a Lord that one. How do you know? Well, he doesn't smell of manure like the rest of us.
     */
    public void addLord(Person newPerson) {
        lords.add(newPerson);
    }

    /**
     * Demote to peasant if Lord, promote to Lord if peasant.
     * @param theOne You have been chosen, mortal!.
     */
    public void move(Person theOne) {
        if(peasants.contains(theOne)) {
            peasants.remove(theOne);
            lords.add(theOne);
        } else {
            lords.remove(theOne);
            peasants.add(theOne);
        }
    }
    /**
     * Probably an unnessasary function, used for testing certain... Stuff.
     * @param destroy Person sent to the oblivion.
     */
    public void delPerson(Person destroy) {
        if(lords.contains(destroy)) {
            lords.remove(destroy);
        } else {
            peasants.remove(destroy);
        }
    }

    public void removeDuplicates() {
        List<Person> temp = new ArrayList<>();
        List<Person> checked = new ArrayList<>();
        for(Person l : lords) {
            if(!checked.contains(l)) {
                if(l.getLordinfo() != null && l.getLordinfo().getCounties().size() > 0) {
                    temp.add(l);
                }
            }
        }
        lords = temp;
        temp = new ArrayList<>();
        for(Person p : peasants) {
            if(p.getLordinfo() != null) {
                System.out.println("world.java: LORD IS PRETENDING TO BE A PEASANTS! name: " + p.getName() + p.getSurname());
                p.setLordinfo(null);
            }
            if(lords.contains(p)) {
                System.out.println("LORDS CONTAIN A PEASANT!");
            }
            if(!checked.contains(p)) {
                temp.add(p);
            }
        }
        peasants = temp;
    }

    public void readAll(String fNames, String mNames, String lName, String traitTxt, String eventTxt, String countiesTxt) {
        namesF = readPremade.names(fNames);
        namesM = readPremade.names(mNames);
        surnames = readPremade.names(lName);
        traits = readPremade.traits(traitTxt);
        events = readPremade.events(eventTxt);
        counties = readPremade.counties(countiesTxt);
        portraits = readPremade.portraits();

        warTypes.add("Raid");
        warTypes.add("Conquest");
        warTypes.add("Force vassalization");
        warTypes.add("Independance");
        warTypes.add("Defense");

        laws.add("Agnatic");
        laws.add("Agnatic-Cognatic");
        laws.add("Cognatic");

    }




    public List<String> getNamesF() {
        return namesF;
    }

    public List<String> getNamesM() {
        return namesM;
    }

    public List<String> getSurnames() {
        return surnames;
    }

    public Map<String, Traits> getTraits() {
        return traits;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public Map<String, County> getCounties() {
        return counties;
    }

    public List<Person> getLords() {
        return lords;
    }

    public List<Person> getPeasants() {
        return peasants;
    }

    public Map<String, List<String>> getPortraits() {
        return portraits;
    }

    public YearSeason getStartDate() {
        return startDate;
    }

    public void setStartDate(YearSeason startDate) {
        this.startDate = startDate;
    }

    public YearSeason getCurrentDate() {
        return currentDate;
    }

    public void passSeason() {
        currentDate.nextSeason();
    }

    public World(YearSeason startDate) {
        this.startDate = startDate;
        this.currentDate = startDate;
    }

    public void makeColorMap() {
        for(County c : counties.values()) {
            colorCounty.put(c.getColorString(), c);
        }
    }

    public Map<String, County> getColorCounty() {
        return colorCounty;
    }
}
