//Copyright Hugh Perkins 2012, hughperkins -at- gmail
//
//This Source Code Form is subject to the terms of the Mozilla Public License, 
//v. 2.0. If a copy of the MPL was not distributed with this file, You can 
//obtain one at http://mozilla.org/MPL/2.0/.

package jfastparser;

import junit.framework.AssertionFailedError;

public class ExceptionAssertions {
	public static void assertException(BlastContainer blastContainer ) {
		boolean caughtException = false;
		try {
			blastContainer.test();
		} catch( Exception e ) {
			caughtException = true;
		}
		if( !caughtException ) {
			throw new AssertionFailedError("exception expected to be thrown, but was not");
		}
	}
	public static interface BlastContainer {
		public void test() throws Exception;
	}
}
