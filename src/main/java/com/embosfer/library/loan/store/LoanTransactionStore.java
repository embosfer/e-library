package com.embosfer.library.loan.store;

import com.embosfer.library.model.Loan;

/**
 * Component in charge of storing {@link Loan} transactions
 * 
 * @author embosfer
 *
 */
public interface LoanTransactionStore {

	public void store(Loan loan);
}
