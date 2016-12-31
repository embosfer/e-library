package com.embosfer.library.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.embosfer.library.test.inventory.CSVLibraryItemSupplierTest;
import com.embosfer.library.test.loan.BorrowedItemsCacheTest;
import com.embosfer.library.test.loan.LoanManagerTest;

@RunWith(Suite.class)
@SuiteClasses({ BorrowedItemsCacheTest.class, CSVLibraryItemSupplierTest.class, LoanManagerTest.class })
public class LibraryTestSuite {
	// holder class
}
