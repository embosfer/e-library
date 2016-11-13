package com.embosfer.library.loan;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.embosfer.library.model.LibraryItemCopy;

/**
 * Maintains a cache with only the {@link LibraryItemCopy}s that are currently
 * loaned out
 * 
 * @author embosfer
 *
 */
public class BorrowedItemsCache {

	private final ConcurrentMap<Long, LibraryItemCopy> borrowedItems = new ConcurrentHashMap<>();

	public BorrowedItemsCache() {

	}

	/**
	 * @param item
	 *            The {@link LibraryItemCopy} that the user wants to borrow
	 * @return whether the item was borrowed successfully
	 */
	public boolean tryToAdd(LibraryItemCopy item) {
		LibraryItemCopy itemToLoan = borrowedItems.putIfAbsent(item.getUniqueID(), item);
		return itemToLoan == null;
	}

	/**
	 * @param item
	 *            The {@link LibraryItemCopy} to remove
	 */
	public void remove(LibraryItemCopy item) {
		borrowedItems.remove(item.getUniqueID());
	}
}
