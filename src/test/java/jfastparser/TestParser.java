// Copyright Hugh Perkins 2012, hughperkins -at- gmail
//
// This Source Code Form is subject to the terms of the Mozilla Public License, 
// v. 2.0. If a copy of the MPL was not distributed with this file, You can 
// obtain one at http://mozilla.org/MPL/2.0/.

package jfastparser;

import junit.framework.TestCase;
import org.junit.Test;

import static jfastparser.ExceptionAssertions.*;
import static junit.framework.TestCase.assertEquals;

public class TestParser {
	@Test
	public void test() {
		String input = "123 56.4 12:3";
		final Parser parser = new Parser(input);
		assertEquals(123, parser.eatInt());
		assertException(new BlastContainer() {
			@Override
			public void test() throws Exception {
				parser.eatInt();
			}
		});
		assertException(new BlastContainer() {
			@Override
			public void test() throws Exception {
				parser.eatDouble();
			}
		});
		parser.eatWhitespace();
		assertEquals(56.4, parser.eatDouble());
		parser.eatWhitespace();
		assertEquals(12, parser.eatInt());
		assertException(new BlastContainer() {
			@Override
			public void test() throws Exception {
				parser.eatChar('.');
			}
		});
		parser.eatChar(':');
		assertEquals(3, parser.eatInt());
	}
}
