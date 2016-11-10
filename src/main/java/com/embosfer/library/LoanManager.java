package com.embosfer.library;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.User;

public class LoanManager {

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
	public boolean loan(LibraryItem item, User user, int days) {
		Objects.requireNonNull(item, "LibraryItem cannot be null");
		Objects.requireNonNull(user, "User cannot be null");
		if (days < 1) throw new IllegalArgumentException("days must be > 0");
		
		LibraryItem itemToLoan = loanedItems.putIfAbsent(item.getUniqueID(), item);
		if (itemToLoan == null) {
			// TODO create transaction
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime returningDate = now.plusDays(days);
			return true;
		} else {
			return false;
		}
	}
}
