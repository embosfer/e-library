package com.embosfer.library.test.inventory;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.embosfer.library.inventory.CSVLibraryItemSupplier;
import com.embosfer.library.inventory.LibraryItemSupplier;

/**
 * @author embosfer
 *
 */
@RunWith(JUnit4.class)
public class CSVLibraryItemSupplierTest {

	@Test(expected = NullPointerException.class)
	public void nullCSVFile() throws IOException {
		new CSVLibraryItemSupplier(null);
	}

	@Test
	public void initialCSVFileSize() throws IOException {
		File csv = new File("src/main/resources/initial_inventory.csv");
		LibraryItemSupplier csvLibraryItemSupplier = new CSVLibraryItemSupplier(csv);
		Assert.assertEquals(12, csvLibraryItemSupplier.getCurrentInventory().size());
	}

	@Test(expected = AssertionError.class)
	public void disallowUIDDuplicates() throws IOException {
		File csv = new File("src/test/resources/duplicate_uid_inventory.csv");
		CSVLibraryItemSupplier csvLibraryItemSupplier = new CSVLibraryItemSupplier(csv);
		Assert.assertEquals(1, csvLibraryItemSupplier.getCurrentInventory().size());
	}
}
