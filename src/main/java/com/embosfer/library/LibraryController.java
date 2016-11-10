package com.embosfer.library;

/**
 * Provides an API that allows the user to take actions against the library and
 * potentially change the state of it
 * 
 * @author embosfer
 *
 */
public interface LibraryController {

	/**
	 * @param uniqueID
	 *            of the item to be borrowed
	 * @return whether the item was borrowed successfully
	 */
	public boolean borrowBy(long uniqueID);

	/**
	 * @param uniqueID
	 *            of the item to be returned
	 * @return whether the item was returned successfully
	 */
	public boolean returnBy(long uniqueID);

}
