package world;

import inGame.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private YearSeason deathdate;
    private YearSeason birthdate;
    private List<Traits> traits = new ArrayList<>();
    private LordInfo lordinfo;
    private String portrait;


    public static boolean isLord(Person p) {
        if(p == null) {
            return false;
        }
        if(p.getLordinfo() == null) {
            return false;
        }
        return true;
    }

    public void death() {
        alive = false;
        deathdate = Game.getWorld().getCurrentDate();
        if(relations.getConsort() != null) {
            relations.getConsort().getRelations().setConsort(null);
            relations.setConsort(null);
        }
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

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

    public YearSeason getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(YearSeason deathdate) {
        this.deathdate = deathdate;
    }

    public YearSeason getBirthdate() {
        return birthdate;
    }

    public int getAge(YearSeason currentYear) {
        int age;
        age = currentYear.getYear() - birthdate.getYear();
        if(birthdate.isEarlier(currentYear.getSeason())) {
            age += 1;
        }
        return age;
    }

    public int getAge() {
        YearSeason currentYear = Game.getWorld().getCurrentDate();
        int age;
        age = currentYear.getYear() - birthdate.getYear();
        if(birthdate.isEarlier(currentYear.getSeason())) {
            age += 1;
        }
        return age;
    }

    public void setBirthdate(YearSeason birthdate) {
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
        if(lordinfo != null) {
            lordinfo.setSelf(this);
        }
        this.lordinfo = lordinfo;
    }
    /*
    // ONLY HERE FOR REFERENCE, actually part of the relations class
    private Person consort;
    private List<Person> children = new ArrayList<>();
    private Person father;
    private Person mother;
    private List<Person> brothers = new ArrayList<>();
    private List<Person> sisters = new ArrayList<>();
    */

    public void childBirth(Person child) {

        Person tConsort = this.getRelations().getConsort();
        Set<Person> otherChildren = new HashSet<>();

        this.getRelations().addChild(child);
        // born to female ruler
        if(gender.equals("F")) {
            child.setMom(this);
            if(tConsort != null) {
                otherChildren.addAll(tConsort.getRelations().getChildren());
                child.setDad(tConsort);
                tConsort.getRelations().addChild(child);
            }
        } else {
            // male ruler
            child.setDad(this);
            if(tConsort != null) {
                otherChildren.addAll(tConsort.getRelations().getChildren());
                child.setMom(tConsort);
                tConsort.getRelations().addChild(child);
            }
        }

        otherChildren.addAll(this.getRelations().getChildren());
        if(!otherChildren.isEmpty()) {
            for(Person sibling : otherChildren) {
                child.getRelations().addBrothers(sibling.getRelations().getBrothers());
                child.getRelations().addSisters(sibling.getRelations().getSisters());
                if(child.getGender().equals("M")) {
                    sibling.getRelations().addBrother(child);
                } else {
                    sibling.getRelations().addSister(child);
                }
            }
        }
    }




    public void setDad(Person dad) {
        this.relations.setFather(dad);
    }
    public void setMom(Person mom) {
        this.relations.setMother(mom);
    }


    /**
     * Add brother and add sister are redundant, but left here incase i want to use them in events(adobtion event?)
     */
    public void addBrother(Person bro) {
        this.relations.addBrother(bro);
    }
    public void addSister(Person sis) {
        this.relations.addSister(sis);
    }

    public Person() {
        relations.setSelf(this);
    }

    public static void main(String[] args) {

    }
}
