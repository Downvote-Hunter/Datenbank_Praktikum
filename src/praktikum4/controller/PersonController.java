package controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import database.Database;
import menue.Action;
import model.Person;
import util.ReadUtil;

public class PersonController {

    private static long getLastID() {

        AtomicLong lastID = new AtomicLong(1);

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    Person person = db.getEM().createNamedQuery("Person.findLastID",
                            Person.class).setMaxResults(1).getResultList().get(0);
                    lastID.set(person.getId());
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lastID.get();
    }

    public static void addPerson() {
        Person person = new Person();
        person.setName(ReadUtil.readString("Name: "));
        person.setGebDatum(ReadUtil.readDate("Geburtsdatum: "));
        person.setGeschlecht(ReadUtil.readChar("Geschlecht: ", new char[] { 'M', 'm', 'F', 'f' }).toUpperCase());

        try (Database db = new Database()) {
            person.setId(getLastID() + 1);
            db.add(person);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void removePerson() {

        Person person = new Person();
        person.setId(ReadUtil.readLong("PID: "));

        try (Database db = new Database()) {
            person = (Person) db.findByID(person);
            db.delete(person);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updatePerson() {

        Person person = new Person();
        person.setId(ReadUtil.readLong("PID: "));
        person.setName(ReadUtil.readString("Name: "));
        person.setGebDatum(ReadUtil.readDate("Geburtsdatum: "));
        person.setGeschlecht(ReadUtil.readChar("Geschlecht: ", new char[] { 'M', 'm', 'F', 'f' }).toUpperCase());

        try (Database db = new Database()) {
            db.update(person);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clear() {

        try (Database db = new Database()) {

            db.execute(new Action() {

                @Override
                public void execute() {
                    List<Person> persons = db.getEM().createNamedQuery("Person.findAll", Person.class).getResultList();

                    for (Person person : persons) {
                        db.delete(person);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Person findPersonByID() {
        Person person = new Person();

        try (Database db = new Database()) {
            person.setId(ReadUtil.readLong("PID: "));
            person = (Person) db.findByID(person);
            System.out.println(person);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;

    }

    public static void findPersonsPlayedInMovie() {

        List<Person> persons = MovieController.findMovieByID().getPersons();

        for (Person person : persons) {
            System.out.println(person);
        }

    }
}
