package com.embosfer.library.test.loan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.loan.LoanManager;
import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.LibraryItem.LibraryItemType;
import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.User;

@RunWith(JUnit4.class)
public class LoanManagerTest {

	@Test(expected = NullPointerException.class)
	public void borrowNullBook() {
		LoanManager loanManager = new LoanManager();

		loanManager.borrow(null, new User(1, "Bob"), 7);
	}

	@Test(expected = NullPointerException.class)
	public void borrowNullUser() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, item);
		loanManager.borrow(uniqueCopy, null, 7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void borrowInvalidDays() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, item);
		loanManager.borrow(uniqueCopy, new User(1, "Bob"), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void borrowInvalidDays_2() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, item);
		loanManager.borrow(uniqueCopy, new User(1, "Bob"), -1);
	}

	@Test
	public void borrowSingleThreaded_BorrowSuccess() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, item);
		boolean loaned = loanManager.borrow(uniqueCopy, new User(1, "Bob"), 1);
		Assert.assertTrue(loaned);
	}

	@Test
	public void borrowSingleThreaded_BorrowFail() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, item);
		User bob = new User(1, "Bob");
		boolean loaned = loanManager.borrow(uniqueCopy, bob, 1);
		Assert.assertTrue(loaned);

		// try to loan it again with same user
		loaned = loanManager.borrow(uniqueCopy, bob, 1);
		Assert.assertFalse(loaned);

		// try to loan it again with different user
		loaned = loanManager.borrow(uniqueCopy, new User(2, "Alice"), 1);
		Assert.assertFalse(loaned);
	}

	@Test
	public void borrowMultiThreaded_JustOneUserSucceds() throws InterruptedException {
		LoanManager loanManager = new LoanManager();
		LibraryItem itemToLoan = new LibraryItem(1, LibraryItemType.Book, "Java Concurrency In Practice");
		LibraryItemCopy uniqueCopy = new LibraryItemCopy(1, itemToLoan);

		int noThreads = 10;
		CountDownLatch startGate = new CountDownLatch(1);
		CountDownLatch endGate = new CountDownLatch(noThreads);
		AtomicInteger numTimesLoaned = new AtomicInteger(0);

		Runnable loanTask = () -> {
			try {
				startGate.await();
			} catch (InterruptedException ignored) {
			}
			// FIXME different users
			boolean loaned = loanManager.borrow(uniqueCopy, new User(1, "Bob"), 1);
			if (loaned)
				numTimesLoaned.incrementAndGet();
			endGate.countDown(); // wait for all threads to be ready
		};

		IntStream.rangeClosed(1, noThreads).forEach(index -> {
			Thread loaner = new Thread(loanTask);
			loaner.start();
		});

		startGate.countDown(); // release all threads
		endGate.await(); // wait for all threads to finish
		Assert.assertEquals(1, numTimesLoaned.get());
	}
}
