package praktikum3.controller;

import praktikum3.Database;
import praktikum3.db.PersonDB;
import praktikum3.dto.PersonDTO;
import praktikum3.exception.InputNotInRange;
import praktikum3.util.ReadUtil;

import java.util.List;

import static praktikum3.controller.ControllerProperties.PROPFILE;

public class PersonController {
    public static void addPerson() {

        PersonDTO person = new PersonDTO();

        person.setName(ReadUtil.readString("Name: "));
        person.setGebDatum(ReadUtil.readDate("Geburtsdatum: "));
        person.setGeschlecht(ReadUtil.readChar("Geschlecht: ", new char[]{'M', 'm', 'F', 'f'}).toUpperCase());

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            int changes = personDB.insert(person);
            System.out.println("Person added");
            System.out.println(changes + " rows changed");
            System.out.println(personDB.findByID(person.getPID()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void removePerson() {

        PersonDTO person = new PersonDTO();

        try {
            person.setPID(ReadUtil.readInt("PID: "));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            person = personDB.findByID(person.getPID());
            int changes = personDB.delete(person);

            System.out.println("Person removed");
            System.out.println(changes + " rows changed");
            System.out.println(person);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void updatePerson() {

        PersonDTO person = new PersonDTO();

        try {
            person.setPID(ReadUtil.readInt("PID: "));
            person.setName(ReadUtil.readString("Name: "));
            person.setGebDatum(ReadUtil.readDate("Geburtsdatum: "));
            person.setGeschlecht(ReadUtil.readChar("Geschlecht: ", new char[]{'M', 'm', 'F', 'f'}).toUpperCase());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            int changes = personDB.update(person);

            System.out.println("Person updated");
            System.out.println(changes + " rows changed");
            System.out.println(personDB.findByID(person.getPID()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void clear() {
        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            int changes = personDB.clear();

            System.out.println("All Persons removed");
            System.out.println(changes + " rows changed");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void findPersonByID() {

        PersonDTO person = new PersonDTO();

        try {
            person.setPID(ReadUtil.readInt("PID: "));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            person = personDB.findByID(person.getPID());

            System.out.println("Person found");
            System.out.println(person);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void showPersonsPlayedInMovie() {

        List<PersonDTO> personList;

        int MID = 0;

        try {
            MID = ReadUtil.readInt("MID: ");
        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }


        try (Database db = new Database()) {
            db.connect(PROPFILE);
            PersonDB personDB = new PersonDB(db);
            personList = personDB.findPersonsByMovie(MID);

            System.out.println("Persons found");
            System.out.println(personList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
