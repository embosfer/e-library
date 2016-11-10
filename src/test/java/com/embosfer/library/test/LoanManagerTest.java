package com.embosfer.library.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.LoanManager;
import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.LibraryItem.LibraryItemType;
import com.embosfer.library.model.User;

@RunWith(JUnit4.class)
public class LoanManagerTest {

	@Test(expected = NullPointerException.class)
	public void testLoanNullBook() {
		LoanManager loanManager = new LoanManager();

		loanManager.loan(null, new User(), 7);
	}

	@Test(expected = NullPointerException.class)
	public void testLoanNullUser() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, 1, LibraryItemType.Book, "");
		loanManager.loan(item, null, 7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoanInvalidDays() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, 1, LibraryItemType.Book, "");
		loanManager.loan(item, new User(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoanInvalidDays_2() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, 1, LibraryItemType.Book, "");
		loanManager.loan(item, new User(), -1);
	}

	@Test
	public void testLoanSingleThreaded_LoanSuccess() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, 1, LibraryItemType.Book, "");
		boolean loaned = loanManager.loan(item, new User(), 1);
		Assert.assertTrue(loaned);
	}

	@Test
	public void testLoanSingleThreaded_LoanFail() {
		LoanManager loanManager = new LoanManager();

		LibraryItem item = new LibraryItem(1, 1, LibraryItemType.Book, "");
		boolean loaned = loanManager.loan(item, new User(), 1);
		Assert.assertTrue(loaned);

		// try to loan it again with same user
		loaned = loanManager.loan(item, new User(), 1);
		Assert.assertFalse(loaned);

		// try to loan it again with different user
		loaned = loanManager.loan(item, new User(), 1);
		Assert.assertFalse(loaned);
	}

	@Test
	public void testLoanMultiThreaded_JustOneSuccess() throws InterruptedException {
		LoanManager loanManager = new LoanManager();
		LibraryItem itemToLoan = new LibraryItem(1, 1, LibraryItemType.Book, "");

		int noThreads = 10;
		CountDownLatch startGate = new CountDownLatch(1);
		CountDownLatch endGate = new CountDownLatch(noThreads);
		AtomicInteger numTimesLoaned = new AtomicInteger(0);

		Runnable loanTask = () -> {
			try {
				startGate.await();
			} catch (InterruptedException ignored) {
			}
			boolean loaned = loanManager.loan(itemToLoan, new User(), 1);
			if (loaned)
				numTimesLoaned.incrementAndGet();
			endGate.countDown(); // wait for all threads to be ready
		};

		IntStream.rangeClosed(1, noThreads).forEach(index -> {
			Thread loaner = new Thread(loanTask);
			loaner.start();
		});

		startGate.countDown();
		endGate.await(); // wait for all threads to finish
		Assert.assertEquals(1, numTimesLoaned.get());
	}
}
