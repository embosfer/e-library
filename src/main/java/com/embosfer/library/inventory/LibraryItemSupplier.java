package com.embosfer.library.inventory;

import java.util.Collection;

import com.embosfer.library.model.LibraryItem;

/**
 * Provides methods that allow the user to get information about the state of
 * the library
 * 
 * @author embosfer
 *
 */
public interface LibraryItemSupplier {

	// You have been tasked with building part of a simple library system that
	// allows customers to
	// borrow books, DVDs, VHSes, and CDs. Your code should provide the
	// following functionality:

	//  Borrow and return items - items are loaned out for a period of one
	// week.
	// For example, a customer can borrow WarGames on 21st February and they
	// will be
	// expected to return it by 28th February.
	//  Determine current inventory - this should show you the current items
	// that are loanable.
	// You should make allowances for multiple copies of the same item (i.e.
	// there can be
	// multiple copies of the same book/movie). 
	// For example, if you choose to use the initial inventory, the current
	// inventory should return
	// the titles. 
	//  Determine overdue items. i.e. all items that should have been returned
	// before today.
	// For example, if a book was due on 12th February and today is 15th
	// February, that book
	// should be flagged as overdue. 
	//  Determine the borrowed items for a user.

	// TODO maybe List instead of Collection

	public Collection<LibraryItem> getCurrentInventory();
}
