package com.embosfer.library.model;

import java.util.Objects;

/**
 * This structure holds the details of the specific copy of the
 * {@link LibraryItem}, like its unique ID in the library. It also holds a
 * reference to the shared details for that item across all copies.
 * 
 * @author embosfer
 *
 */
public class LibraryItemCopy {

	private final long uniqueID;
	private final LibraryItem item;

	/**
	 * @param uniqueID
	 *            The item's unique ID.
	 */
	public LibraryItemCopy(long uniqueID, LibraryItem item) {
		Objects.requireNonNull(item);
		this.uniqueID = uniqueID;
		this.item = item;
	}

	/**
	 * @return the uniqueID
	 */
	public long getUniqueID() {
		return uniqueID;
	}

	public LibraryItem getItem() {
		return item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + item.hashCode();
		result = prime * result + (int) (uniqueID ^ (uniqueID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LibraryItemCopy))
			return false;
		LibraryItemCopy that = (LibraryItemCopy) obj;
		if (this.uniqueID != that.uniqueID)
			return false;
		return this.item.equals(that.item);
	}

}
