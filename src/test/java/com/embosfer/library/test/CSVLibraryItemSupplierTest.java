package com.embosfer.library.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.CSVLibraryItemSupplier;
import com.embosfer.library.LibraryItemSupplier;

import org.junit.Assert;

/**
 * @author embosfer
 *
 */
@RunWith(JUnit4.class)
public class CSVLibraryItemSupplierTest {

	private static final String INITIAL_INVENTORY_CSV = "src/main/resources/initial_inventory.csv";
	private static final String INVENTORY_TEST_CSV = "build/tmp/initial_inventory.csv";
	private static final String csvHeader = "UniqueID,BookID,Type,Title";

	@Test(expected = NullPointerException.class)
	public void nullCSVFile() {
		new CSVLibraryItemSupplier(null);
	}

	private void createCSVFile() {
		try (FileWriter fileWriter = new FileWriter(INVENTORY_TEST_CSV)) {
			fileWriter.write(csvHeader);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void initialCSVFileSize() {
		File csv = new File(INITIAL_INVENTORY_CSV);
		if (!csv.isFile()) {
			throw new IllegalArgumentException();
		}
		LibraryItemSupplier csvLibraryItemSupplier = new CSVLibraryItemSupplier(csv);
		Assert.assertEquals(12, csvLibraryItemSupplier.getCurrentInventory().size());
	}
}
