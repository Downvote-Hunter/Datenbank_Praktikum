package praktikum3.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDTO {

    /**
     * List of persons who have played in this movie
     */
    private List<PersonDTO> personList = new ArrayList<>();
    private int MID;
    private String title;
    private int year;

    public MovieDTO(ResultSet rs) throws SQLException {

        setMID(rs.getInt("MID"));
        setTitle(rs.getString("TITLE"));
        setYear(rs.getInt("YEAR"));

    }

    public MovieDTO() {

    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "MID=" + MID +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", \n\tpersonDTO=" + personList +
                "}\n";
    }

    public List<PersonDTO> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonDTO> personDTO) {
        this.personList = personDTO;
    }

    public void addPerson(PersonDTO personDTO) {
        this.personList.add(personDTO);
    }

    public void removePerson(PersonDTO personDTO) {
        this.personList.remove(personDTO);
        System.out.println("Person " + personDTO.getPID() + " removed from movie " + MID);
    }

    public void removePerson(int PID) {

        if (getPerson(PID) != null) {
            personList.remove(getPerson(PID));
            System.out.println("Person " + PID + " removed from movie " + MID);
        } else {
            System.out.println("Person " + PID + " not found in movie " + MID);
        }
    }

    public PersonDTO getPerson(int id) {
        for (PersonDTO p : personList) {
            if (p.getPID() == id) {
                return p;
            }
        }
        return null;
    }


    public void clearPersonDTO() {
        this.personList.clear();
    }

    public int getMID() {

        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
