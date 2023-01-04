package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "CAST" database table.
 * 
 */
@Embeddable
public class CastPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long mid;

	private long pid;

	@Column(name = "\"ROLE\"")
	private String role;

	public CastPK() {
	}

	public long getMid() {
		return this.mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CastPK)) {
			return false;
		}
		CastPK castOther = (CastPK) other;
		return (this.mid == castOther.mid)
				&& (this.pid == castOther.pid)
				&& this.role.equals(castOther.role);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.mid ^ (this.mid >>> 32)));
		hash = hash * prime + ((int) (this.pid ^ (this.pid >>> 32)));
		hash = hash * prime + this.role.hashCode();

		return hash;
	}

	@Override
	public String toString() {
		String message = "CastPK [mid=" + mid + ", pid=" + pid + ", role=" + role + "]";

		return message;
	}

}