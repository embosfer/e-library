package com.embosfer.library.loan;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.User;

/**
 * Maintains a cache with only the {@link LibraryItemCopy}s that are currently
 * loaned out
 * 
 * @author embosfer
 *
 */
public class BorrowedItemsCache {

	private final ConcurrentMap<Long, LibraryItemCopy> borrowedItems = new ConcurrentHashMap<>();
	private final ConcurrentMap<User, Set<LibraryItemCopy>> borrowedItemsPerUser = new ConcurrentHashMap<>();

	public BorrowedItemsCache() {

	}

	/**
	 * @param item
	 *            The {@link LibraryItemCopy} that the user wants to borrow
	 * @param user
	 *            The {@link User} trying to borrow
	 * @return whether the item was borrowed successfully
	 */
	public boolean tryToAdd(LibraryItemCopy item, User user) {
		LibraryItemCopy itemToLoan = borrowedItems.putIfAbsent(item.getUniqueID(), item);
		if (itemToLoan == null) {
			borrowedItemsPerUser.merge(user, new CopyOnWriteArraySet<>(), (u, itemsSet) -> {
				itemsSet.add(item);
				return itemsSet;
			});
			return true;
		}
		return false;
	}

	/**
	 * @param item
	 *            The {@link LibraryItemCopy} to remove
	 * @param user
	 *            The {@link User} trying to borrow
	 */
	public void remove(LibraryItemCopy item, User user) {
		borrowedItems.remove(item.getUniqueID());
		borrowedItemsPerUser.get(user).remove(item);
	}

}
