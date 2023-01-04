package model;

import java.io.Serializable;
import javax.persistence.*;

import database.Model;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the PERSON database table.
 * 
 */
@Entity
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
@NamedQuery(name = "Person.findLastID", query = "SELECT p FROM Person p ORDER BY p.pid DESC")
public class Person implements Serializable, Model {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pid;

	@Temporal(TemporalType.DATE)
	@Column(name = "GEB_DATUM")
	private Date gebDatum;

	private String geschlecht;

	private String name;

	// bi-directional many-to-one association to Cast
	@OneToMany(mappedBy = "person")
	private List<Cast> casts;

	// bi-directional many-to-many association to Movie
	@ManyToMany
	@JoinTable(name = "\"CAST\"", joinColumns = {
			@JoinColumn(name = "PID")
	}, inverseJoinColumns = {
			@JoinColumn(name = "MID")
	})
	private List<Movie> movies;

	public Person() {
	}

	public Date getGebDatum() {
		return this.gebDatum;
	}

	public void setGebDatum(Date gebDatum) {
		this.gebDatum = gebDatum;
	}

	public String getGeschlecht() {
		return this.geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Cast> getCasts() {
		return this.casts;
	}

	public void setCasts(List<Cast> casts) {
		this.casts = casts;
	}

	public Cast addCast(Cast cast) {
		getCasts().add(cast);
		cast.setPerson(this);

		return cast;
	}

	public Cast removeCast(Cast cast) {
		getCasts().remove(cast);
		cast.setPerson(null);

		return cast;
	}

	public List<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public void addMovie(Movie movie) {
		getMovies().add(movie);
		movie.getPersons().add(this);
	}

	public void removeMovie(Movie movie) {
		getMovies().remove(movie);
		movie.getPersons().remove(this);
	}

	public String toString() {

		String message = "Person [pid=" + pid + ", name=" + name + ", geschlecht=" + geschlecht + ", gebDatum="
				+ gebDatum;

		String movies = "Movies: [";

		for (Movie movie : getMovies()) {
			movies += ", " + movie.toStringSimple();
		}

		message += ", " + movies + "]";

		return message;

	}

	// Wie toString, aber ohne Filme. Dient zum vermeiden von Endlosschleifen
	public String toStringSimple() {
		return "Person [pid=" + pid + ", name=" + name + ", geschlecht=" + geschlecht + ", gebDatum=" + gebDatum + "]";
	}

	@Override
	public long getId() {
		return this.pid;
	}

	@Override
	public void setId(long id) {
		this.pid = id;
	}

}