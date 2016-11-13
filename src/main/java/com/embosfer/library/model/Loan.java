package com.embosfer.library.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Structure holding the information of a loan made by a {@link User}
 * 
 * @author embosfer
 *
 */
public class Loan {

	// TODO add unique ID
	private final User user;
	private final long uniqueLibraryItemID;
	private final LocalDateTime loanStart;
	private final LocalDateTime loanDueEnd;

	/**
	 * @param user
	 *            The {@link User}. Must not be null
	 * @param uniqueLibraryItemID
	 * @param loanStart
	 * @param loanDueEnd
	 */
	public Loan(User user, long uniqueLibraryItemID, LocalDateTime loanStart, LocalDateTime loanDueEnd) {
		Objects.requireNonNull(user);
		this.user = user;
		this.uniqueLibraryItemID = uniqueLibraryItemID;
		this.loanStart = loanStart;
		this.loanDueEnd = loanDueEnd;
	}

	// TODO complete javadoc
	// TODO provide equals and hashCode methods

	@Override
	public String toString() {
		return new StringBuilder().append("User ").append(user).append(" loaned out ").append(uniqueLibraryItemID)
				.append(" on ").append(loanStart).append(" and has to return it on ").append(loanDueEnd).toString();
	}

	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(new Loan(new User(1, "Emilio"), 2, now, now.plusDays(7)));
	}
}
