package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class Person {
    //Person (name, surname, gender, consort, (children, father, mother, brothers, sisters)HAVE BEEN PUT TO RELATIONS,
    //  alive(y/n), deathdate, birthdate, traits(list), lordinfo(for county owners)
    private String name;
    private String surname;
    private String gender;
    private Relations relations = new Relations();
    private Boolean alive = true;
    private String deathdate;
    private String birthdate;
    private List<Traits> traits = new ArrayList<>();
    private LordInfo lordinfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Relations getRelations() {
        return relations;
    }

    public void setRelations(Relations relations) {
        this.relations = relations;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public String getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(String deathdate) {
        this.deathdate = deathdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<Traits> getTraits() {
        return traits;
    }

    public void setTraits(List<Traits> traits) {
        this.traits = traits;
    }

    public LordInfo getLordinfo() {
        return lordinfo;
    }

    public void setLordinfo(LordInfo lordinfo) {
        this.lordinfo = lordinfo;
    }

    private Person consort;
    private List<Person> children = new ArrayList<>();
    private Person father;
    private Person mother;
    private List<Person> brothers = new ArrayList<>();
    private List<Person> sisters = new ArrayList<>();

    public void setConsort(Person consort) {
        this.relations.setConsort(consort);
    }
    public void addChild(Person child) {
        this.relations.addChild(child);
    }
    public void addBrother(Person bro) {
        this.relations.addBrother(bro);
    }
    public void addSister(Person sis) {
        this.relations.addSister(sis);
    }
    public void setDad(Person dad) {
        this.relations.setFather(dad);
    }
    public void setMom(Person mom) {
        this.relations.setMother(mom);
    }

    public static void main(String[] args) {

    }
}
