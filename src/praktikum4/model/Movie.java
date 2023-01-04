package model;

import java.io.Serializable;
import javax.persistence.*;

import database.Model;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the MOVIE database table.
 * 
 */
@Entity
@NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
@NamedQuery(name = "Movie.findLastID", query = "SELECT m FROM Movie m ORDER BY m.mid DESC")
@NamedQuery(name = "Movie.findBetweenYear", query = "SELECT m FROM Movie m WHERE m.year BETWEEN :year1 AND :year2")
public class Movie implements Serializable, Model {
	private static final long serialVersionUID = 1L;

	@Id
	private long mid;

	private String title;

	@Column(name = "\"YEAR\"")
	private BigDecimal year;

	// bi-directional many-to-one association to Cast
	@OneToMany(mappedBy = "movie")
	private List<Cast> casts;

	// bi-directional many-to-many association to Person
	@ManyToMany(mappedBy = "movies")
	private List<Person> persons;

	public Movie() {
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public List<Cast> getCasts() {
		return this.casts;
	}

	public void setCasts(List<Cast> casts) {
		this.casts = casts;
	}

	public Cast addCast(Cast cast) {
		getCasts().add(cast);
		cast.setMovie(this);

		return cast;
	}

	public Cast removeCast(Cast cast) {
		getCasts().remove(cast);
		cast.setMovie(null);

		return cast;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public void addPerson(Person person) {
		getPersons().add(person);
		person.getMovies().add(this);
	}

	public void removePerson(Person person) {
		getPersons().remove(person);
		person.getMovies().remove(this);
	}

	@Override
	public long getId() {
		return this.mid;

	}

	@Override
	public void setId(long id) {
		this.mid = id;
	}

	public void set(Movie movie) {
		this.mid = movie.mid;
		this.title = movie.title;
		this.year = movie.year;
	}

	public String toString() {
		String message = "Movie [mid=" + mid + ", title=" + title + ", year=" + year;

		String persons = "Persons: [";

		for (Person person : getPersons()) {
			persons += ", " + person.toStringSimple();
		}

		message += ", " + persons + "]";

		return message;
	}

	// Wie toString, aber ohne Personen. Dient zum vermeiden von Endlosschleifen
	public String toStringSimple() {
		return "Movie [mid=" + mid + ", title=" + title + ", year=" + year + "]";
	}

}