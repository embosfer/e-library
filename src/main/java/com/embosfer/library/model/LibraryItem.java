package com.embosfer.library.model;

import java.util.Objects;

/**
 * Represents the loanable items in the library
 * 
 * @author embosfer
 *
 */
public class LibraryItem {

	private static final String SEP = " ";

	/**
	 * Book, DVD, VHS
	 */
	public static enum LibraryItemType {
		Book, DVD, VHS
	}

	private final long uniqueID;
	private final long itemID;
	private final LibraryItemType type;
	private final String title;

	/**
	 * @param uniqueID
	 *            The item's unique ID.
	 * @param itemID
	 *            The item ID. This item will be the same across multiple copies
	 *            of the item in the library
	 * @param type
	 *            The item's {@link LibraryItemType}. Must not be null
	 * @param title
	 *            The item's title. Must not be null
	 */
	public LibraryItem(long uniqueID, long itemID, LibraryItemType type, String title) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(title);
		this.uniqueID = uniqueID;
		this.itemID = itemID;
		this.type = type;
		this.title = title;
	}

	/**
	 * @return the uniqueID
	 */
	public long getUniqueID() {
		return uniqueID;
	}

	/**
	 * @return the item ID
	 */
	public long getItemID() {
		return itemID;
	}

	/**
	 * @return the type
	 */
	public LibraryItemType getType() {
		return type;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	// FIXME add iterable and iterator

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (itemID ^ (itemID >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (uniqueID ^ (uniqueID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LibraryItem))
			return false;
		LibraryItem that = (LibraryItem) obj;
		if (uniqueID != that.uniqueID)
			return false;
		if (itemID != that.itemID)
			return false;
		if (!this.title.equals(that.title))
			return false;
		if (type != that.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("UniqueID: ").append(uniqueID).append(SEP).append("ItemID: ").append(itemID)
				.append(SEP).append("Type: ").append(type).append(SEP).append("Title: ").append(title).toString();
	}

}
