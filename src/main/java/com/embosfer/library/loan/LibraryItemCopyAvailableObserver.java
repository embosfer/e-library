package com.embosfer.library.loan;

import com.embosfer.library.model.LibraryItemCopy;

/**
 * Notifies users of the library of the availability of a
 * {@link LibraryItemCopy}
 * 
 * @author embosfer
 *
 */
public interface LibraryItemCopyAvailableObserver {

	/**
	 * @param copy
	 *            The copy that has become available
	 */
	public void onAvailable(LibraryItemCopy copy);
}
