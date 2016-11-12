package com.embosfer.library.test.inventory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.embosfer.library.test.loan.LoanManagerTest;

@RunWith(Suite.class)
@SuiteClasses({ CSVLibraryItemSupplierTest.class, LoanManagerTest.class })
public class LibraryTestSuite {
	// holder class
}
