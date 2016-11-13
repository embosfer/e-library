package com.embosfer.library.loan;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.embosfer.library.loan.store.LoanTransactionStore;
import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.Loan;
import com.embosfer.library.model.User;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class LoanManager implements LoanController {

	// TODO: tune it to size
	private final BorrowedItemsCache activeLoansCache;
	private final LoanTransactionStore loanStore;
	private final ScheduledExecutorService overdueItemsNotifier = Executors.newSingleThreadScheduledExecutor(
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("overdueNotifier-%d").build());

	public LoanManager(BorrowedItemsCache borrowedItemsCache, LoanTransactionStore loanStore) {
		Objects.requireNonNull(borrowedItemsCache, "BorrowedItemsCache cannot be null");
		Objects.requireNonNull(loanStore, "LoanTransactionStore cannot be null");
		this.activeLoansCache = borrowedItemsCache;
		this.loanStore = loanStore;
	}

	public void start() {
	}

	public void stop() {
		overdueItemsNotifier.shutdown();
	}

	@Override
	public boolean borrow(LibraryItemCopy item, User user, int days) {
		check(item, user);
		if (days < 1)
			throw new IllegalArgumentException("days must be > 0");

		if (activeLoansCache.tryToAdd(item)) {
			// borrowing successful
			LocalDateTime now = LocalDateTime.now();
			Loan newLoan = new Loan(user, item.getUniqueID(), now, now.plusDays(days));
			loanStore.store(newLoan);
			overdueItemsNotifier.schedule(() -> System.err.println("Item " + item + " is overdue!"), days,
					TimeUnit.DAYS);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void returnItem(LibraryItemCopy item, User user) {
		check(item, user);
		activeLoansCache.remove(item);
		// TODO find Loan by item and user and pass it as param below
		loanStore.end(null);
	}

	private void check(LibraryItemCopy item, User user) {
		Objects.requireNonNull(item, "LibraryItemCopy cannot be null");
		Objects.requireNonNull(user, "User cannot be null");
	}
}
