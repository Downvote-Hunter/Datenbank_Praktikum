package praktikum3.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CastDTO {

    private int MID;
    private int PID;
    private String role;

    public CastDTO(ResultSet rs) throws SQLException {
        setPID(rs.getInt("PID"));
        setMID(rs.getInt("MID"));
        setRole(rs.getString("ROLE"));
    }

    public CastDTO() {
    }

    public int getMID() {
        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
