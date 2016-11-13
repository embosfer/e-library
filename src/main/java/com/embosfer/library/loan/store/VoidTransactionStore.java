package com.embosfer.library.loan.store;

import com.embosfer.library.model.Loan;

public class VoidTransactionStore implements LoanTransactionStore {

	@Override
	public void store(Loan loan) {
		System.out.println("Successfully stored " + loan);
	}

}
