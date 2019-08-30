package world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Martin Karjus 1 on 08/03/2017.
 */
public class Relations {
    private Person consort;
    private Set<Person> children = new HashSet<>();
    private Person father;
    private Person mother;
    private Set<Person> brothers = new HashSet<>();
    private Set<Person> sisters = new HashSet<>();
    private Person self;


    public Person getSelf() {
        return self;
    }

    public void setSelf(Person self) {
        this.self = self;
    }

    public Set<Person> getClose() {
        Set<Person> temp = new HashSet<>();
        Set<Person> temp2 = new HashSet<>();

        if(consort != null && self.getAlive()) {
            temp.add(consort);
        }

        for(Person c : children) {
            if(c.getAlive()) {
                temp.add(c);
            }
        }
        for(Person c : temp)
            for(Person cc : c.getRelations().getChildren()) {
                if(cc.getAlive()) {
                    temp2.add(cc);
                }
            }
        for(Person c : getSiblings()) {
            if(c.getAlive()) {
                temp.add(c);
            }
        }
        if(father != null) {
            if(father.getAlive()) {
                temp.add(father);
            }
        }
        if(mother != null) {
            if(mother.getAlive()) {
                temp.add(mother);
            }
        }
        temp.addAll(temp2);
        return temp;
    }

    public Set<Person> getCloseUnmarriedLandless() {
        Set<Person> temp = new HashSet<>();
        Set<Person> temp2 = new HashSet<>();

        if(consort == null && self.getAge() > 16) {
            temp.add(self);
        }

        for(Person c : children) {
            if(c.getRelations().getConsort() == null && c.getLordinfo() == null && c.getAlive()) {
                if(c.getAge() > 16) {
                    temp.add(c);
                }
            }
        }
        for(Person c : temp)
            for(Person cc : c.getRelations().getChildren()) {
                if(cc.getRelations().getConsort() == null && cc.getAlive()) {
                    if(cc.getAge() > 16) {
                        temp2.add(cc);
                    }
                }
            }
        for(Person c : getSiblings()) {
            if(c.getRelations().getConsort() == null && c.getLordinfo() == null) {
                if(c.getAge() > 16 && c.getAlive()) {
                    temp.add(c);
                }
            }
        }
        if(father != null) {
            if(father.getRelations().getConsort() == null || !father.getRelations().getConsort().getAlive()
                    && father.getAlive()) {
                temp.add(father);
            }
        }
        if(mother != null) {
            if(mother.getRelations().getConsort() == null || !mother.getRelations().getConsort().getAlive()
                    && mother.getAlive()) {
                temp.add(mother);
            }
        }
        temp.addAll(temp2);
        return temp;
    }

    public Set<Person> getSiblings() {
        Set<Person> sib = new HashSet<>();
        sib.addAll(brothers);
        sib.addAll(sisters);
        return sib;
    }

    public int nrOfUnmarriedOwnedCloseRelatives() {
        //TODO implement
        return 0;
    }

    public void addChild(Person child) {
        children.add(child);
    }
    public void addChildren(Set<Person> childList) {
        children.addAll(childList);
    }

    public void addBrothers(Set<Person> bro) {
        brothers.addAll(bro);
    }
    public void addSisters(Set<Person> sis) {
        sisters.addAll(sis);
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
        if(consort == null) {
            this.consort = null;
            return;
        }
        this.consort = consort;
        this.children.addAll(consort.getRelations().getChildren());
        if(self.getLordinfo() != null && consort.getLordinfo() != null) {
            self.getLordinfo().addAlliance(consort.getLordinfo());
        }
    }

    public Set<Person> getChildren() {
        return children;
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

    public Set<Person> getBrothers() {
        return brothers;
    }



    public Set<Person> getSisters() {
        return sisters;
    }



    /**
     * Probably redundant, people are added not set, keeping for possible event use(adoption/sudden appearance).
     */
    public void setChildren(Set<Person> children) {
        this.children = children;
    }
    public void setBrothers(Set<Person> brothers) {
        this.brothers = brothers;
    }
    public void setSisters(Set<Person> sisters) {
        this.sisters = sisters;
    }
}
