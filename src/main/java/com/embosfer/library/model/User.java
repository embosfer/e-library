package com.embosfer.library.model;

import java.util.Objects;

/**
 * Represents the library user
 * 
 * @author embosfer
 *
 */
public class User {

	private final long id;
	private final String name;

	public User(long id, String name) {
		Objects.requireNonNull(name);
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;

		User that = (User) obj;
		if (this.id != that.id)
			return false;
		return this.name.equals(that.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return new StringBuilder(name).append(" (Id: ").append(id).append(")").toString();
	}
}
