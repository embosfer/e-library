package com.embosfer.library.inventory;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.LibraryItem.LibraryItemType;
import com.embosfer.library.model.LibraryItemCopy;
import com.embosfer.library.model.User;

/**
 * A supplier that retrieves the library items from a CSV
 * 
 * @author embosfer
 *
 */
public class CSVLibraryItemSupplier implements LibraryItemSupplier {

	// TODO maybe rename to inventorySupplier
	// https://commons.apache.org/proper/commons-csv/user-guide.html#Example:_Parsing_an_Excel_CSV_File
	private static final String COMMA = ",";

	private final ConcurrentMap<LibraryItem, Set<LibraryItemCopy>> copiesByItem = new ConcurrentHashMap<>();
	private final List<LibraryItem> inventoryList;

	/**
	 * @param csvFile
	 *            The CSV {@link File}
	 * @throws IOException
	 *             If an {@link IOException} occurs
	 */
	public CSVLibraryItemSupplier(File csvFile) throws IOException {
		Objects.requireNonNull(csvFile, "CSV File must not be null");

		try (Stream<String> stream = Files.lines(Paths.get(csvFile.toURI()))) {

			List<LibraryItem> libraryItems = stream.skip(1).map(toLibraryItem).collect(toList());
			inventoryList = new CopyOnWriteArrayList<>(libraryItems);

		}
	}

	private Function<String, LibraryItem> toLibraryItem = line -> {
		String[] fields = line.split(COMMA);
		int i = 0;
		Long uniqueID = Long.valueOf(fields[i++]);
		LibraryItem libraryItem = new LibraryItem(Long.valueOf(fields[i++]), LibraryItemType.valueOf(fields[i++]),
				fields[i++]);
		LibraryItemCopy itemCopy = new LibraryItemCopy(uniqueID, libraryItem);
		copiesByItem.computeIfAbsent(libraryItem, k -> new HashSet<>()).add(itemCopy);
		return libraryItem;
	};

	@Override
	public Collection<LibraryItem> getItemsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LibraryItem> getCurrentInventory() {
		return inventoryList;
	}

	@Override
	public Collection<LibraryItem> getOverdueItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LibraryItem> getBorrowedItemsFor() {
		// TODO Auto-generated method stub
		return null;
	}

}
