package com.embosfer.library.loan.store;

import com.embosfer.library.model.Loan;

public class VoidTransactionStore implements LoanTransactionStore {

	@Override
	public void store(Loan loan) {
		System.out.println("Successfully stored " + loan);
	}

	@Override
	public void end(Loan loan) {
		System.out.println("Successfully marked as ended " + loan);
	}

}
