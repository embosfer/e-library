package com.embosfer.library;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
	private final List<LibraryItem> inventoryList = new ArrayList<>();

	public CSVLibraryItemSupplier(String csvFile) {
		Objects.requireNonNull(csvFile, "CSV file name must not be null");
		URL systemResource = ClassLoader.getSystemResource(csvFile);
		if (systemResource == null) throw new IllegalArgumentException("CSV file " + csvFile + " could not be found");

		try (Stream<String> stream = Files.lines(Paths.get(systemResource.toURI()))) {


			stream.skip(1).forEach(line -> {
				String[] fields = line.split(COMMA);
				int i = 0;
				Long uniqueID = Long.valueOf(fields[i++]);
				Long bookID = Long.valueOf(fields[i++]);
				LibraryItemType type = LibraryItemType.valueOf(fields[i++]);
				String title = fields[i++];
				LibraryItem item = new LibraryItem(uniqueID, bookID, type, title);
				
				// populate caches
				addNewItem(item);
			});

		} catch (IOException | URISyntaxException e) {
			throw new AssertionError(e); // TODO review: maybe create a custom exception
		}
	}
	
	private void addNewItem(LibraryItem item) {
		System.out.println(item.toString());
		itemsByUniqueID.put(item.getBookID(), item);
		inventoryList.add(item);
	}

	@Override
	public Collection<LibraryItem> getItemsByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LibraryItem> getCurrentInventory() {
		return inventoryList; // TODO think about whether we want this to escape or not
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
