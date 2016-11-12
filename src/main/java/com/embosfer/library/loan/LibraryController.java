package com.embosfer.library.loan;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.User;

/**
 * Provides an API that allows the user to take actions against the library and
 * potentially change the state of it
 * 
 * @author embosfer
 *
 */
public interface LibraryController {

	/**
	 * @param item
	 *            to be borrowed
	 * @param user
	 *            interested in borrowing
	 * @param days
	 *            number of days
	 * @return whether the item was borrowed successfully
	 */
	public boolean borrow(LibraryItem item, User user, int days);

	/**
	 * @param item
	 *            to be returned
	 * @param user
	 *            returning the item
	 * @return whether the item was returned successfully
	 */
	public boolean returnItem(LibraryItem item, User user);

}
