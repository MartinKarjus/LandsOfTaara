package world;

import java.util.*;

/**
 * The objects in the game world.
 * One day, a class like this will contain us all...
 */
public class World {
    private List<Person> lords = new ArrayList<>();
    private List<Person> peasants = new ArrayList<>();
    private LinkedList<String> namesF = new LinkedList<>();
    private LinkedList<String> namesM = new LinkedList<>();
    private LinkedList<String> surnames = new LinkedList<>();
    private Map<String, Traits> traits;
    private Map<String, Event> events;
    private Map<String, County> counties;

    public void backF() {
        namesF.offer(namesF.poll());
    }
    public void backM() {
        namesM.offer(namesM.poll());
    }
    public void backS() {
        surnames.offer(surnames.poll());
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

    public void readAll(String fNames, String mNames, String lName, String traitTxt, String eventTxt, String countiesTxt) {
        namesF = readPremade.names(fNames);
        namesM = readPremade.names(mNames);
        surnames = readPremade.names(lName);
        traits = readPremade.traits(traitTxt);
        events = readPremade.events(eventTxt);
        counties = readPremade.counties(countiesTxt);
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
}
