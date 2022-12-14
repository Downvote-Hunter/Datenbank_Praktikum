package praktikum3.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    private List<MovieDTO> movieList = new ArrayList<>();

    private int PID;
    private String name;
    private Date gebDatum;
    private String geschlecht;

    public PersonDTO(ResultSet rs) throws SQLException {

        setPID(rs.getInt("PID"));
        setName(rs.getString("NAME"));
        setGebDatum(rs.getDate("GEB_DATUM"));
        setGeschlecht(rs.getString("GESCHLECHT"));
    }

    public PersonDTO() {
    }


    public List<MovieDTO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDTO> movieDTO) {
        this.movieList = movieDTO;
    }

    public void addMovie(MovieDTO movieDTO) {
        this.movieList.add(movieDTO);
    }

    public void removeMovie(MovieDTO movieDTO) {
        this.movieList.remove(movieDTO);
    }

    public void removeMovie(int MID) {

        if (getMovie(MID) != null) {
            movieList.remove(getMovie(MID));
            System.out.println("Person " + PID + " removed from movie " + MID);
        } else {
            System.out.println("Person " + PID + " not found in movie " + MID);
        }
    }

    public MovieDTO getMovie(int id) {
        for (MovieDTO m : movieList) {
            if (m.getMID() == id) {
                return m;
            }
        }
        return null;
    }

    public void clearMovieDTO() {
        this.movieList.clear();
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGebDatum() {
        return gebDatum;
    }

    public void setGebDatum(Date gebDatum) {
        this.gebDatum = gebDatum;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }


    @Override
    public String toString() {
        return "PersonDTO{" +
                "PID=" + PID +
                ", name='" + name + '\'' +
                ", gebDatum=" + gebDatum +
                ", geschlecht='" + geschlecht + '\'' +
                ", \n\tmovieList=" + movieList +
                "}\n";
    }
}
