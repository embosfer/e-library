package com.embosfer.library.loan;

import java.util.Collection;

import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.User;

/**
 * Provides an API that allows the user to loan out or return items, as well as
 * query information about loaned items
 * 
 * @author embosfer
 *
 */
public interface LoanController {

	/**
	 * @param item
	 *            to be borrowed
	 * @param user
	 *            wanting to borrow
	 * @param days
	 *            number of days
	 * @return whether the item was borrowed successfully
	 */
	public boolean borrow(LibraryItemCopy item, User user, int days);

	/**
	 * @param item
	 *            to be returned
	 * @param user
	 *            returning the item
	 */
	public void returnItem(LibraryItemCopy item, User user);

	/**
	 * @return a collection of overdue items
	 */
	public Collection<LibraryItemCopy> getOverdueItems();

	/**
	 * @param user
	 * @return a collection of currently borrowed items for the given User
	 */
	public Collection<LibraryItemCopy> getBorrowedItemsFor(User user);

}
