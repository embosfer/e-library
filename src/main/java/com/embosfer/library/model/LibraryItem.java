package com.embosfer.library.model;

/**
 * @author embosfer
 *
 */
public class LibraryItem {

	private static final String SEP = " ";

	public static enum LibraryItemType {
		Book, DVD, VHS
	}

	private final long uniqueID;
	private final long bookID;
	private final LibraryItemType type;
	private final String title;
	private boolean loanable;

	public LibraryItem(long uniqueID, long bookID, LibraryItemType type, String title, boolean loanable) {
		this.uniqueID = uniqueID;
		this.bookID = bookID;
		this.type = type;
		this.title = title;
		this.loanable = loanable;
	}

	/**
	 * @return the uniqueID
	 */
	public long getUniqueID() {
		return uniqueID;
	}

	/**
	 * @return the bookID
	 */
	public long getBookID() {
		return bookID;
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

	// FIXME add equals
	// FIXME add hashcode
	// FIXME add iterable and iterator
	// FIXME add javadoc

	/**
	 * @return whether this item is loanable
	 */
	public boolean isLoanable() {
		return loanable;
	}

	/**
	 * @param is
	 *            loanable
	 */
	public void setLoanable(boolean loanable) {
		this.loanable = loanable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (bookID ^ (bookID >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (uniqueID ^ (uniqueID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		LibraryItem other = (LibraryItem) obj;
		if (bookID != other.bookID)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		if (uniqueID != other.uniqueID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder("UniqueID: ").append(uniqueID).append(SEP).append("BookID: ").append(bookID)
				.append(SEP).append("Type: ").append(type).append(SEP).append("Title: ").append(title).append(SEP)
				.append("Loanable? ").append(loanable).toString();
	}

}
