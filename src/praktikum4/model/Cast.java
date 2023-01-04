package model;

import java.io.Serializable;
import javax.persistence.*;

import database.CastModel;

/**
 * The persistent class for the "CAST" database table.
 * 
 */
@Entity
@Table(name = "\"CAST\"")
@NamedQuery(name = "Cast.findAll", query = "SELECT c FROM Cast c")
@NamedQuery(name = "Cast.findById", query = "SELECT c FROM Cast c WHERE c.id.mid = :mid AND c.id.pid = :pid")
@NamedQuery(name = "Cast.findByMovieId", query = "SELECT c FROM Cast c WHERE c.id.mid = :mid")
@NamedQuery(name = "Cast.findByPersonId", query = "SELECT c FROM Cast c WHERE c.id.pid = :pid")
public class Cast implements Serializable, CastModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CastPK id;

	// bi-directional many-to-one association to Movie
	@ManyToOne
	@JoinColumn(name = "MID", insertable = false, updatable = false)
	private Movie movie;

	// bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "PID", insertable = false, updatable = false)
	private Person person;

	public Cast() {
	}

	public Movie getMovie() {
		return this.movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public CastPK getId() {
		return this.id;
	}

	@Override
	public void setId(CastPK id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String message = "Cast [id=" + id + ", movie=" + movie + ", person=" + person + "]";


		return message;
	}

}