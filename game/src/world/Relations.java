package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Karjus 1 on 08/03/2017.
 */
// TODO need to change relations for all people connected to person when he/she is added to relationship network(not call new relation
    // TODO for all already exisisting relationships(example: adding brother to a baby girl should add sister to the brother aswell)
public class Relations {
    private Person consort;
    private List<Person> children = new ArrayList<>();
    private Person father;
    private Person mother;
    private List<Person> brothers = new ArrayList<>();
    private List<Person> sisters = new ArrayList<>();

    public void addChild(Person child) {
        children.add(child);
    }
    public void addBrother(Person bro) {
        brothers.add(bro);
    }
    public void addSister(Person sis) {
        sisters.add(sis);
    }

    public Person getConsort() {
        return consort;
    }

    public void setConsort(Person consort) {
        this.consort = consort;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public Person getFather() {
        return father;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getMother() {
        return mother;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public List<Person> getBrothers() {
        return brothers;
    }

    public void setBrothers(List<Person> brothers) {
        this.brothers = brothers;
    }

    public List<Person> getSisters() {
        return sisters;
    }

    public void setSisters(List<Person> sisters) {
        this.sisters = sisters;
    }
}
