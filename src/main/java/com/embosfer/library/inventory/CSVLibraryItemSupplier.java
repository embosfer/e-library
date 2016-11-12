package com.embosfer.library.inventory;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

import com.embosfer.library.model.LibraryItem;
import com.embosfer.library.model.LibraryItem.LibraryItemType;
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

	private final ConcurrentMap<Long, LibraryItem> itemsByUniqueID = new ConcurrentHashMap<>();
	private final List<LibraryItem> inventoryList;

	public CSVLibraryItemSupplier(File csvFile) {
		Objects.requireNonNull(csvFile, "CSV File must not be null");

		try (Stream<String> stream = Files.lines(Paths.get(csvFile.toURI()))) {

			List<LibraryItem> libraryItems = stream.skip(1).map(toLibraryItem).collect(toList());
			inventoryList = new CopyOnWriteArrayList<>(libraryItems);

		} catch (IOException e) {
			throw new AssertionError(e); // TODO review: maybe create a custom
											// exception
		}
	}

	private Function<String, LibraryItem> toLibraryItem = line -> {
		String[] fields = line.split(COMMA);
		int i = 0;
		LibraryItem libraryItem = new LibraryItem(Long.valueOf(fields[i++]), Long.valueOf(fields[i++]),
				LibraryItemType.valueOf(fields[i++]), fields[i++]);
		itemsByUniqueID.put(libraryItem.getBookID(), libraryItem);
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
