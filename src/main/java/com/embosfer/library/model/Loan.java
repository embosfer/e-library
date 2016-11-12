package com.embosfer.library.model;

import java.time.LocalDateTime;

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
	private final LocalDateTime loanEnd;

	public Loan(User user, long uniqueLibraryItemID, LocalDateTime loanStart, LocalDateTime loanEnd) {
		this.user = user;
		this.uniqueLibraryItemID = uniqueLibraryItemID;
		this.loanStart = loanStart;
		this.loanEnd = loanEnd;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("User ").append(user).append(" loaned out ").append(uniqueLibraryItemID)
				.append(" on ").append(loanStart).append(" and has to return it on ").append(loanEnd).toString();
	}

	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(new Loan(new User(1, "Emilio"), 2, now, now.plusDays(7)));
	}
}
