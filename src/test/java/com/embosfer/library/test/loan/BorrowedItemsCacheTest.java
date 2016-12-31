package com.embosfer.library.test.loan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.loan.BorrowedItemsCache;
import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.LibraryItem.LibraryItemType;
import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.User;

@RunWith(JUnit4.class)
public class BorrowedItemsCacheTest {

	private BorrowedItemsCache toTest;
	private User paul = new User(1, "Paul");;
	private LibraryItem starWarsDVD = new LibraryItem(1, LibraryItemType.DVD, "Star Wars: The Phantom Menace");
	private LibraryItemCopy starWarsDVDCopy1 = new LibraryItemCopy(1, starWarsDVD);
	private LibraryItemCopy starWarsDVDCopy2 = new LibraryItemCopy(2, starWarsDVD);

	@Before
	public void initialise() {
		toTest = new BorrowedItemsCache();
	}

	@Test
	public void tryToAddSingleThreaded() {
		// add star wars copy number 1
		boolean added = toTest.tryToAdd(starWarsDVDCopy1, paul);

		Assert.assertTrue(added);

		// try to add the same copy again
		// it shouldn't be added
		added = toTest.tryToAdd(starWarsDVDCopy1, paul);

		Assert.assertFalse(added);

		// add copy number 2
		added = toTest.tryToAdd(starWarsDVDCopy2, paul);

		Assert.assertTrue(added);

		// try to add the same copy again
		// it shouldn't be added
		added = toTest.tryToAdd(starWarsDVDCopy2, paul);

		Assert.assertFalse(added);
	}

	@Test
	public void tryToAddMultiThreaded() throws InterruptedException {
		int noUsers = 10;

		CountDownLatch startGate = new CountDownLatch(1);
		CountDownLatch endGate = new CountDownLatch(noUsers);
		AtomicInteger timesAdded = new AtomicInteger(0);

		// "noUsers" users trying to borrow the same item
		for (int i = 0; i < noUsers; i++) {
			User user = new User(i, "User" + i);
			new Thread(() -> {
				try {
					startGate.await();
				} catch (InterruptedException ignored) {
				}
				boolean added = toTest.tryToAdd(starWarsDVDCopy1, user);
				if (added) {
					timesAdded.incrementAndGet();
				}
				endGate.countDown();

			}).start();
		}

		startGate.countDown(); // all threads go!
		endGate.await();
		Assert.assertEquals(1, timesAdded.get()); // check that only one user made it
	}
}
