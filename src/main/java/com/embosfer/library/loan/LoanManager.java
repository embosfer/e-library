package com.embosfer.library.loan;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.Loan;
import com.embosfer.library.model.User;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class LoanManager implements LoanController {

	// TODO: tune it to size
	private final ConcurrentMap<Long, LibraryItem> loanedItems = new ConcurrentHashMap<>();
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
	public boolean borrow(LibraryItem item, User user, int days) {
		check(item, user);
		if (days < 1)
			throw new IllegalArgumentException("days must be > 0");

		LibraryItem itemToLoan = loanedItems.putIfAbsent(item.getUniqueID(), item);
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
	public boolean returnItem(LibraryItem item, User user) {
		check(item, user);
		LibraryItem itemReturned = loanedItems.remove(item.getUniqueID());
		if (itemReturned != null) {
			//TODO
			return true;
		}
		return false;
	}

	private void check(LibraryItem item, User user) {
		Objects.requireNonNull(item, "LibraryItem cannot be null");
		Objects.requireNonNull(user, "User cannot be null");
	}
}
