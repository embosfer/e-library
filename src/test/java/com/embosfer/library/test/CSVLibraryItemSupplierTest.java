package com.embosfer.library.test;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.CSVLibraryItemSupplier;
import com.embosfer.library.LibraryItemSupplier;

/**
 * @author embosfer
 *
 */
@RunWith(JUnit4.class)
public class CSVLibraryItemSupplierTest {

	private static final String INVENTORY_TEST_CSV = "inventory_test.csv";
	private static final String csvHeader = "UniqueID,BookID,Type,Title";

	@Test(expected = NullPointerException.class)
	public void testNullCSVFile() {
		new CSVLibraryItemSupplier(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonExistentCSVFile() {
		new CSVLibraryItemSupplier("foo.txt");
	}

	private void createCSVFile() {
		try (FileWriter fileWriter = new FileWriter(INVENTORY_TEST_CSV)) {
			fileWriter.write(csvHeader);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

	// TODO think about path issues
	@Test
	public void testCSVFile() {
		createCSVFile();
		LibraryItemSupplier csvLibraryItemSupplier = new CSVLibraryItemSupplier(INVENTORY_TEST_CSV);

	}
}
