package com.embosfer.library.model;

import java.time.LocalDateTime;

public class Loan {

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
}
