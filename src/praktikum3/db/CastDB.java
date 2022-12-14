package praktikum3.db;

import praktikum3.Database;
import praktikum3.dto.CastDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CastDB {

    private Database db = null;

    public CastDB(Database db) {
        this.db = db;
    }

    public Database getDB() {
        return db;
    }

    public void setDB(Database db) {
        this.db = db;
    }

    public int insert(CastDTO cast) {
        String query = "INSERT INTO CAST (PID, MID, ROLE) VALUES (?, ?, ?)";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, cast.getPID());
            ps.setInt(2, cast.getMID());
            ps.setString(3, cast.getRole());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int delete(CastDTO cast) {
        String query = "DELETE FROM CAST WHERE PID = ? AND MID = ?";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, cast.getPID());
            ps.setInt(2, cast.getMID());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int clear() {
        String query = "DELETE FROM CAST";
        try (PreparedStatement ps = db.preparedStatement(query)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public CastDTO findByID(int MID, int PID) {

        String query = "SELECT * FROM CAST WHERE MID = ? AND PID = ?";
        CastDTO cast = new CastDTO();

        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, MID);
            ps.setInt(2, PID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cast.setMID(rs.getInt("MID"));
                cast.setPID(rs.getInt("PID"));
                cast.setRole(rs.getString("ROLE"));
                return cast;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CastDTO> findAllByMID(int MID) {
        List<CastDTO> castList = new ArrayList<>();
        String query = "SELECT * FROM CAST WHERE MID = ?";

        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, MID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    castList.add(new CastDTO(rs));
                }
                return castList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CastDTO> findAllByPID(int PID) {
        List<CastDTO> castList = new ArrayList<>();
        String query = "SELECT * FROM CAST WHERE PID = ?";

        try (PreparedStatement ps = db.preparedStatement(query)) {
            ps.setInt(1, PID);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    castList.add(new CastDTO(rs));
                }
                return castList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}