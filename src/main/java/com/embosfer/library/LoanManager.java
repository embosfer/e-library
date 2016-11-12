package com.embosfer.library;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.Loan;
import com.embosfer.library.model.User;

public class LoanManager implements LibraryController {

	// TODO: tune it to size
	private final ConcurrentMap<Long, LibraryItem> loanedItems = new ConcurrentHashMap<>();
	private final ScheduledExecutorService overdueItemsNotifier = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactory() {

				int incr = 1;

				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "overdueItemsNotifier-" + incr++);
					t.setDaemon(true);
					return t;
				}
			});

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
		if (days < 1) throw new IllegalArgumentException("days must be > 0");
		
		LibraryItem itemToLoan = loanedItems.putIfAbsent(item.getUniqueID(), item);
		if (itemToLoan == null) {
			// borrowing successful
			// TODO create transaction
			LocalDateTime now = LocalDateTime.now();
			new Loan(user, item.getUniqueID(), now, now.plusDays(days));
			overdueItemsNotifier.schedule(() -> System.err.println("Item " + itemToLoan + " is overdue!"), days, TimeUnit.DAYS);
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
			
		}
		return false;
	}
	
	private void check(LibraryItem item, User user) {
		Objects.requireNonNull(item, "LibraryItem cannot be null");
		Objects.requireNonNull(user, "User cannot be null");
	}
}
