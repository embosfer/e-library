package com.embosfer.library.model;

/**
 * @author embosfer
 *
 */
public class User {

	private final long id;
	private final String name;

	public User(long id, String name) {
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
	public String toString() {
		return new StringBuilder(name).append(" (Id: ").append(id).append(")").toString();
	}
}
