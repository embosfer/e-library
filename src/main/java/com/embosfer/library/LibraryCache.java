package com.embosfer.library;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.embosfer.library.model.LibraryItem;

/**
 * @author embosfer
 *
 */
public class LibraryCache {

	private final ConcurrentMap<Long, LibraryItem> itemsByUniqueID;
	
	LibraryCache(Collection<LibraryItem> libraryItems) {
		itemsByUniqueID = new ConcurrentHashMap<>(libraryItems.size());
	}
	
	void removeItem(long uniqueID) {
		itemsByUniqueID.remove(uniqueID);
	}
	
}
