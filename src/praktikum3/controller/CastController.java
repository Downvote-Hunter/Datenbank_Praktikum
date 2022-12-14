package praktikum3.controller;

import praktikum3.Database;
import praktikum3.db.CastDB;
import praktikum3.dto.CastDTO;
import praktikum3.exception.InputNotInRange;
import praktikum3.util.ReadUtil;

import java.util.List;

import static praktikum3.controller.ControllerProperties.PROPFILE;

public class CastController {
    public static void addCast() {

        CastDTO cast = new CastDTO();

        try {
            cast.setMID(ReadUtil.readInt("MID: "));
            cast.setPID(ReadUtil.readInt("PID: "));

        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        cast.setRole(ReadUtil.readString("Role: "));

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            CastDB castDB = new CastDB(db);


            int changes = castDB.insert(cast);
            System.out.println("Cast added");
            System.out.println(changes + " rows changed");
            System.out.println(castDB.findByID(cast.getMID(), cast.getPID()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeCast() {
        CastDTO cast = new CastDTO();

        try {
            cast.setMID(ReadUtil.readInt("MID: "));
            cast.setPID(ReadUtil.readInt("PID: "));

        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            CastDB castDB = new CastDB(db);
            cast = castDB.findByID(cast.getMID(), cast.getPID());
            int changes = castDB.delete(cast);

            System.out.println("Cast removed");
            System.out.println(changes + " rows changed");
            System.out.println(cast);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void clear() {
        try (Database db = new Database()) {
            db.connect(PROPFILE);
            CastDB castDB = new CastDB(db);
            int changes = castDB.clear();

            System.out.println("Cast cleared");
            System.out.println(changes + " rows changed");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showCastsByMovieID() {
        CastDTO cast = new CastDTO();

        try {
            cast.setMID(ReadUtil.readInt("MID: "));

        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            CastDB castDB = new CastDB(db);
            List<CastDTO> castList = castDB.findAllByMID(cast.getMID());

            System.out.println("Casts by MID " + cast.getMID());
            System.out.println(castList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void showCastsByPersonID() {
        CastDTO cast = new CastDTO();

        try {
            cast.setPID(ReadUtil.readInt("PID: "));

        } catch (InputNotInRange e) {
            throw new RuntimeException(e);
        }

        try (Database db = new Database()) {
            db.connect(PROPFILE);
            CastDB castDB = new CastDB(db);
            List<CastDTO> castList = castDB.findAllByPID(cast.getPID());

            System.out.println("Casts by PID " + cast.getPID());
            System.out.println(castList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
