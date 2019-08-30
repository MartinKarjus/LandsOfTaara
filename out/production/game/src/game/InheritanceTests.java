package game;

import junit.framework.TestCase;
import org.junit.Test;
import world.LordInfo;
import world.Person;
import world.YearSeason;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Martin Karjus 1 on 16/05/2017.
 */
public class InheritanceTests extends TestCase {
    @Test
    public void testInherit() throws Exception {
        System.out.println("1");
        RunGame rg = new RunGame();
        rg.setTesting(true);
        Person t = new Person();
        LordInfo l = new LordInfo();
        t.setLordinfo(l);
        l.setLaw("Cognatic");

        Person son = new Person();
        son.setGender("M");
        son.setName("youngSon");
        son.setBirthdate(new YearSeason("W", 100));
        t.getRelations().addChild(son);

        Person son2 = new Person();
        son2.setGender("M");
        son2.setName("oldSon");
        son2.setBirthdate(new YearSeason("W", 91));
        t.getRelations().addChild(son2);

        Person daughter = new Person();
        daughter.setName("oldDaughter");
        daughter.setGender("F");
        daughter.setBirthdate(new YearSeason("W", 1));
        t.getRelations().addChild(daughter);

        Person deadPerson = rg.inherit(t);
        if(deadPerson != null) {
            System.out.println("inheritor: " + deadPerson.getName());
        } else {
            System.out.println("Noone inherited.");
        }
        assertEquals(daughter, rg.inherit(t));
    }

    @Test
    public void testInherit2() {
        System.out.println("2");
        RunGame rg = new RunGame();
        rg.setTesting(true);
        Person t = new Person();
        LordInfo l = new LordInfo();
        t.setLordinfo(l);
        l.setLaw("Agnatic-Cognatic");

        Person son = new Person();
        son.setGender("M");
        son.setName("youngSon");
        son.setBirthdate(new YearSeason("W", 100));
        t.getRelations().addChild(son);

        Person son2 = new Person();
        son2.setGender("M");
        son2.setName("oldSon");
        son2.setBirthdate(new YearSeason("W", 91));
        t.getRelations().addChild(son2);

        Person daughter = new Person();
        daughter.setName("oldDaughter");
        daughter.setGender("F");
        daughter.setBirthdate(new YearSeason("W", 90));
        t.getRelations().addChild(daughter);

        Person deadPerson = rg.inherit(t);
        if(deadPerson != null) {
            System.out.println("inheritor: " + deadPerson.getName());
        } else {
            System.out.println("Noone inherited.");
        }
        assertEquals(son2, rg.inherit(t));
    }

    @Test
    public void testInherit3() throws Exception {
        System.out.println("3");
        RunGame rg = new RunGame();
        rg.setTesting(true);
        Person t = new Person();
        LordInfo l = new LordInfo();
        t.setLordinfo(l);
        l.setLaw("Agnatic-Cognatic");

        Person son = new Person();
        son.setGender("F");
        son.setName("d2");
        son.setBirthdate(new YearSeason("W", 100));
        t.getRelations().addChild(son);

        Person son2 = new Person();
        son2.setGender("F");
        son2.setName("d1");
        son2.setBirthdate(new YearSeason("W", 91));
        t.getRelations().addChild(son2);

        Person daughter = new Person();
        daughter.setName("oldDaughter");
        daughter.setGender("F");
        daughter.setBirthdate(new YearSeason("W", 1));
        t.getRelations().addChild(daughter);

        Person deadPerson = rg.inherit(t);
        if(deadPerson != null) {
            System.out.println("inheritor: " + deadPerson.getName());
        } else {
            System.out.println("Noone inherited.");
        }
        assertEquals(daughter, rg.inherit(t));
    }

    @Test
    public void testInherit4() throws Exception {
        System.out.println("4");
        RunGame rg = new RunGame();
        rg.setTesting(true);
        Person t = new Person();
        LordInfo l = new LordInfo();
        t.setLordinfo(l);
        l.setLaw("Agnatic");

        Person son = new Person();
        son.setGender("F");
        son.setName("d2");
        son.setBirthdate(new YearSeason("W", 100));
        t.getRelations().addChild(son);

        Person son2 = new Person();
        son2.setGender("F");
        son2.setName("d1");
        son2.setBirthdate(new YearSeason("W", 91));
        t.getRelations().addChild(son2);

        Person daughter = new Person();
        daughter.setName("oldDaughter");
        daughter.setGender("F");
        daughter.setBirthdate(new YearSeason("W", 1));
        t.getRelations().addChild(daughter);

        Person deadPerson = rg.inherit(t);
        if(deadPerson != null) {
            System.out.println("inheritor: " + deadPerson.getName());
        } else {
            System.out.println("Noone inherited.");
        }
        assertEquals(null, rg.inherit(t));
    }

}