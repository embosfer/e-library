package com.embosfer.library.loan;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.Loan;
import com.embosfer.library.model.User;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class LoanManager implements LoanController {

	// TODO: tune it to size
	private final ConcurrentMap<Long, LibraryItemCopy> loanedItems = new ConcurrentHashMap<>();
	private final ScheduledExecutorService overdueItemsNotifier = Executors.newSingleThreadScheduledExecutor(
			new ThreadFactoryBuilder().setDaemon(true).setNameFormat("overdueNotifier-%d").build());

	public LoanManager() {

	}

	public void start() {
	}

	public void stop() {
		overdueItemsNotifier.shutdown();
	}

	// FIXME need the user dimension
	@Override
	public boolean borrow(LibraryItemCopy item, User user, int days) {
		check(item, user);
		if (days < 1)
			throw new IllegalArgumentException("days must be > 0");

		LibraryItemCopy itemToLoan = loanedItems.putIfAbsent(item.getUniqueID(), item);
		if (itemToLoan == null) {
			// borrowing successful
			// TODO create transaction
			LocalDateTime now = LocalDateTime.now();
			new Loan(user, item.getUniqueID(), now, now.plusDays(days));
			overdueItemsNotifier.schedule(() -> System.err.println("Item " + itemToLoan + " is overdue!"), days,
					TimeUnit.DAYS);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean returnItem(LibraryItemCopy item, User user) {
		check(item, user);
		LibraryItemCopy itemReturned = loanedItems.remove(item.getUniqueID());
		if (itemReturned != null) {
			// TODO
			return true;
		}
		return false;
	}

	private void check(LibraryItemCopy item, User user) {
		Objects.requireNonNull(item, "LibraryItemCopy cannot be null");
		Objects.requireNonNull(user, "User cannot be null");
	}
}
