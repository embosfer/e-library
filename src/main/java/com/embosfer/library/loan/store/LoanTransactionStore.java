package com.embosfer.library.loan.store;

import com.embosfer.library.model.Loan;

/**
 * Component in charge of storing {@link Loan} transactions
 * 
 * @author embosfer
 *
 */
public interface LoanTransactionStore {

	/**
	 * @param loan
	 *            The {@link Loan} to store
	 */
	public void store(Loan loan);

	/**
	 * @param loan
	 *            The {@link Loan} to mark as ended
	 */
	public void end(Loan loan);
}
