// @(#)$Id: JMLObjectSequenceTest.java,v 1.13 2004/07/30 19:10:35 kui_dai Exp $

// Copyright (C) 2001 Iowa State University

// This file is part of JML

// JML is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.

// JML is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with JML; see the file COPYING.  If not, write to
// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.


package org.jmlspecs.models.tests;

import junit.framework.*;
import org.jmlspecs.models.*;

/** A test case for {@link org.jmlspecs.models.JMLObjectSequence}.
 * Most of this is derived from the equational theory of sequences.
 *
 * @version $Revision: 1.13 $
 * @author Gary T. Leavens
 */
public class JMLObjectSequenceTest extends TestCase {

    public JMLObjectSequenceTest(String name) {
        super(name);
    }
    public static void main (String[] args) {
        junit.textui.TestRunner.run (suite());
    }
    public static Test suite() {
        return new junit.framework.TestSuite(JMLObjectSequenceTest.class);
    }

    protected JMLObjectSequence<Integer>[] s;
    protected JMLObjectSequence<Integer>[] old_s;
    protected Integer[] e;
    protected Character ca, cb, cc, cd, ce, cx;
    protected JMLObjectSequence<Character> ab, abc, abcd, abcde, abdc, bcd, dabc, axcde;
    protected JMLObjectSequence<Character> abcdc, bcde, bc, cde, cba, acde;
    protected JMLObjectSequence<Character> emptySeq;

    protected void setUp() {
        if (s == null) {

            final int s_size = 5;
            s = new JMLObjectSequence[s_size];
            old_s = new JMLObjectSequence[s_size];
            s[0] = new JMLObjectSequence<Integer>(new Integer(0));
            s[1] = s[0].insertBack(new Integer(2)).insertBack(new Integer(1));
            s[2] = s[1].concat(s[1]).insertBack(new Integer(0));
            s[3] = s[2].insertBack(new Integer(9)).concat(s[2]);
            s[4] = new JMLObjectSequence<Integer>();
            for (int i = 0; i < s_size; i++) {
                old_s[i] = (JMLObjectSequence<Integer>)s[i].clone();
            }
            final int e_size = 5;
            e = new Integer[e_size];
            for (int j = 0; j < e_size - 1; j++) {
                e[j] = new Integer(j);
            }
            e[e_size - 1] = null;

            ca = new Character('a');
            cb = new Character('b');
            cc = new Character('c');
            cd = new Character('d');
            ce = new Character('e');
            cx = new Character('x');
            ab = new JMLObjectSequence<Character>(ca).insertBack(cb);
            abc = ab.insertBack(cc);
            abcd = abc.insertBack(cd);
            abcde = abcd.insertBack(ce);
            abdc = ab.insertBack(cd).insertBack(cc);
            acde = new JMLObjectSequence<Character>(ca).insertBack(cc).insertBack(cd)
                .insertBack(ce);
            bc = new JMLObjectSequence<Character>(cb).insertBack(cc);
            bcd = bc.insertBack(cd);
            bcde = bcd.insertBack(ce);
            cde = new JMLObjectSequence<Character>(cc).insertBack(cd).insertBack(ce);
            cba = new JMLObjectSequence<Character>(cc).insertBack(cb).insertBack(ca);
            dabc = new JMLObjectSequence<Character>(cd).insertBack(ca).insertBack(cb)
                .insertBack(cc);
            axcde = new JMLObjectSequence<Character>(ca).insertBack(cx).insertBack(cc)
                .insertBack(cd).insertBack(ce);
            abcdc = abcd.insertBack(cc);

            emptySeq = new JMLObjectSequence<Character>();

        }
    }

    /** Test of subsequence, thanks to Roy Tan, that was related to a
     * bug in subsequence (now fixed). */
    public void testSubsequenceRoys() {
        try {
            assertEquals(abcde.subsequence(abcde.int_size(), abcde.int_size()), 
                         new JMLObjectSequence<Character>());
        } catch (JMLSequenceException e) {
            fail("subsequence here should not raise a JMLSequenceException");
        }
    }

    /** Test the clone method. */
    public void testClone() {
        assertTrue(s.length == old_s.length);
        for (int i = 0; i < s.length; i++) {
            assertEquals(s[i], old_s[i]);
        }
    }

    /** Test the constructor. */
    public void testNew() {
        assertTrue((new JMLObjectSequence<Integer>()).has(e[1]) == false);
        assertTrue((new JMLObjectSequence<Integer>()).int_size() == 0); 
        try {
            (new JMLObjectSequence<Integer>()).itemAt(0);
            fail("itemAt() should throw an exception on a empty sequence");
        } catch (JMLSequenceException e) {
            // expect to get here
        }
        assertTrue((new JMLObjectSequence<Integer>(e[1])).int_size() == 1); 
        assertTrue((new JMLObjectSequence<Integer>(e[1])).has(e[1]));
        try {
            assertTrue((e[1] == null) ||
                       (new JMLObjectSequence<Integer>(e[1])).itemAt(0) == e[1]);
        } catch (JMLSequenceException e) {
            fail("itemAt should not raise a JMLSequenceException");
        }
    }

    /** Test the insertFront and insertBack methods. */
    public void testInsertFrontBack() {
        try {
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < e.length; j++) {
                    assertTrue((e[j] == null) ||
                               s[i].insertFront(e[j]).first() == e[j]);
                    assertEquals(s[i],old_s[i]);
                    
                    assertTrue((e[j] == null) ||
                               s[i].insertBack(e[j]).last() == e[j]);
                    assertEquals(s[i],old_s[i]);

                    assertTrue((e[j] == null) ||
                               s[i].insertFront(e[j]).itemAt(0) == e[j]);
                    assertEquals(s[i],old_s[i]);
                    
                    assertTrue(s[i].insertFront(e[j]).int_size() 
                               == s[i].int_size() + 1); 
                    assertTrue(s[i].insertFront(null).int_size() 
                               == s[i].int_size() + 1);

                    assertTrue((e[j] == null) ||
                               s[i].insertBack(e[j]).itemAt(s[i].int_size())
                               == e[j]);

                    assertTrue(s[i].insertBack(e[j]).int_size()
                               == s[i].int_size() + 1);
                    assertTrue(s[i].insertBack(null).int_size()
                               == s[i].int_size() + 1);
                }
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the insertAfterIndex method. */
    public void testInsertAfterIndex() {
        int i = 0, j = 0, n = -1;
        try {
            for (i = 0; i < s.length; i++) {
                for (j = 0; j < e.length; j++) {
                    for (n = -1; -1 <= n && n < s[i].int_size(); n++) {
                        if (e[j] != null) {
                            assertTrue("first insertAfterIndex with i = "
                                       + i + "  j = " + j + "  n = " + n,
                                       s[i].insertAfterIndex(n, e[j])
                                       .itemAt(n+1) == e[j]);
                        }
                        assertTrue(s[i].insertAfterIndex(n, e[j]).int_size()
                                   == s[i].int_size() + 1);
                        assertTrue(s[i].insertAfterIndex(n, null).int_size()
                                   == s[i].int_size() + 1);
                    }
                }
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException, i = "
                 + i + "  j = " + j + "  n = " + n + "\n" + e);
        }
    }
        
    /** Test the insertBeforeIndex method. */
    public void testInsertBeforeIndex() {
        try {
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < e.length; j++) {
                    for (int n = 0; -1 <= n && n <= s[i].int_size(); n++) {
                        if (e[j] != null) {
                            assertTrue("first insertBeforeIndex with i = "
                                       + i + "  j = " + j + "  n = " + n,
                                       s[i].insertBeforeIndex(n, e[j])
                                       .itemAt(n) == e[j]);
                        }
                        assertTrue(s[i].insertBeforeIndex(n, e[j]).int_size()
                                   == s[i].int_size() + 1);
                        assertTrue(s[i].insertBeforeIndex(n, null).int_size()
                                   == s[i].int_size() + 1);
                    }
                }
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the isPrefix method. */
    public void testIsPrefix() {
        try {
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[i].isPrefix(s[j])) {
                        for (int n = 0; 0 <= n && n < s[i].int_length(); n++) {
                            assertTrue((s[j].itemAt(n) != null 
                                        && s[j].itemAt(n) == (s[i].itemAt(n)))
                                       || (s[j].itemAt(n) == null
                                           && s[i].itemAt(n) == null));
                        }
                    }
                }
            }

        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }

        assertTrue(abc.isPrefix(abcd));
        assertTrue(abc.isPrefix(abc));
        assertTrue(emptySeq.isPrefix(abcd));
        assertTrue(!abcd.isPrefix(abc));
        assertTrue(!bcd.isPrefix(abcde));

        assertTrue(abc.isProperPrefix(abcd));
        assertTrue(!abc.isProperPrefix(abc));
        assertTrue(!bcd.isProperPrefix(abcde));
    }

    /** Test the isSuffix method. */
    public void testIsSuffix() {
        assertTrue(bcd.isSuffix(abcd));
        assertTrue(abcd.isSuffix(abcd));
        assertTrue(!abc.isSuffix(abcd));
        assertTrue(emptySeq.isSuffix(abcd));
        assertTrue(!bcd.isSuffix(abcde));

        assertTrue(bcd.isProperSuffix(abcd));
        assertTrue(!abcd.isProperSuffix(abcd));
        assertTrue(!abc.isProperSuffix(abcd));
        assertTrue(emptySeq.isProperSuffix(abcd));
        assertTrue(!bcd.isProperSuffix(abcde));
    }

    /** Test the isSubsequence method. */
    public void testIsSubsequence() {
        try {
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    assertTrue(s[i].isSubsequence(s[j])
                               == (s[i].int_length() <= s[j].int_length()
                                   && (s[i].isPrefix(s[j])
                                       || s[i].isSubsequence(s[j].trailer())
                                       )));
                }
            }
        } catch (JMLSequenceException e) {
            fail("trailer here should not raise a JMLSequenceException");
        }
    }

    /** Test the isEmpty method. */
    public void testIsEmpty() {
        assertTrue(emptySeq.isEmpty());
        assertTrue((new JMLObjectSequence<Integer>()).isEmpty());
        assertTrue(!(new JMLObjectSequence<Integer>(e[1])).isEmpty());

        for (int i = 0; i < s.length; i++) {
            assertTrue(s[i].isEmpty() == (s[i].int_size() == 0));
            assertTrue(s[i].isEmpty() == (s[i].int_length() == 0));
        }
    }

    /** Test the insertX methods. */
    public void testInsertDerived() {
        try {
            for (int j = 0; j < e.length; j++) {
                assertEquals(new JMLObjectSequence<Integer>(e[j]),
                             new JMLObjectSequence<Integer>().insertFront(e[j]));
                assertEquals(new JMLObjectSequence<Integer>(e[j]),
                             new JMLObjectSequence<Integer>().insertBack(e[j]));
                assertEquals(new JMLObjectSequence<Integer>(e[j]),
                             new JMLObjectSequence<Integer>().insertAfterIndex(-1,e[j]));
                assertEquals(new JMLObjectSequence<Integer>(e[j]),
                             new JMLObjectSequence<Integer>().insertBeforeIndex(0,e[j]));
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the trailer and header methods. */
    public void testTrailerHeader() {
        try {
            for (int i = 0; i < s.length; i++) {
                if (s[i].int_size() >= 1) {
                    assertEquals(s[i],
                                 s[i].trailer().insertFront(s[i].first()));
                    assertEquals(s[i],
                                 s[i].header().insertBack(s[i].last()));
                }
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the comparison methods. */
    public void testComparisonsDerived() {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s.length; j++) {
                assertTrue(s[i].isProperSubsequence(s[j])
                           == (   s[i].isSubsequence(s[j])
                               && !s[i].equals(s[j])));
                assertTrue(s[i].isSupersequence(s[j])
                           == s[j].isSubsequence(s[i]));
                assertTrue(s[i].isProperSupersequence(s[j])
                           == s[j].isProperSubsequence(s[i]));
            }
        }
    }

    /** Test the examples from the itemAt method. */
    public void testItemAtExamples() {
        try {
            assertTrue(abcd.itemAt(0) == ca);
            assertTrue(abcd.itemAt(3) == cd);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the size method. */
    public void testSizeExamples() {
        assertTrue(abcd.int_size() == 4);
        assertTrue(emptySeq.int_size() == 0);
    }

    /** Test the examples from the count method. */
    public void testCountExamples() {
        assertTrue(abcdc.count(cc) == 2);
        assertTrue(abcdc.count(new Character('q')) == 0);
    }

    /** Test the examples from the has method. */
    public void testHasExamples() {
        assertTrue(abcdc.has(ca));
        assertTrue(abcdc.has(cc));
        assertTrue(abcdc.has(cd));
        for (int j = 0; j < e.length; j++) {
            assertTrue(!emptySeq.has(e[j]));
        }
    }

    /** Test the examples from the indexOf method. */
    public void testIndexOfExamples() {
        try {
            assertTrue(axcde.indexOf(cx) == 1);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }

        try {
            abcd.indexOf(cx);
            fail("indexOf should raise an exception in this case");
        } catch (JMLSequenceException e) {
            // ok
        }
    }

    /** Test the examples from the first method. */
    public void testFirstExamples() {
        try {
            assertTrue(abcd.first() == ca);
            assertTrue(bcd.first() == cb);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the last method. */
    public void testLastExamples() {
        try {
            assertTrue(abcd.last() == cd);
            assertTrue(bcd.last() == cd);
            assertTrue(abc.last() == cc);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the prefix method. */
    public void testPrefixExamples() {
        try {
            assertEquals(abcde.prefix(0), emptySeq);
            assertEquals(abcde.prefix(3), abc);
            assertEquals(abcde.prefix(5), abcde);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the removePrefix method. */
    public void testRemovePrefixExamples() {
        try {
            assertEquals(abcde.removePrefix(1), bcde);
            assertEquals(abcde.removePrefix(5), emptySeq);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the concat method. */
    public void testConcatExamples() {
        assertEquals(ab.concat(cde), abcde);
    }

    /** Test the examples from the reverse method. */
    public void testReverseExamples() {
        assertEquals(abc.reverse(), cba);
        assertEquals(emptySeq.reverse(), emptySeq);
    }

    /** Test the examples from the removeItemAt method. */
    public void testRemoveItemAtExamples() {
        try {
            assertEquals(abcde.removeItemAt(1), acde);
            assertEquals(abcde.removeItemAt(4), abcd);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the header method. */
    public void testHeaderExamples() {
        try {
            assertEquals(abcd.header(), abc);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the trailer method. */
    public void testTrailerExamples() {
        try {
            assertEquals(abcd.trailer(), bcd);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the insertAfterIndex method. */
    public void testInsertAfterIndexExamples() {
        try {
            assertEquals(abc.insertAfterIndex(1,cd), abdc);
            assertEquals(abc.insertAfterIndex(-1,cd), dabc);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the insertBack method. */
    public void testInsertBackExamples() {
        assertEquals(abc.insertBack(cd), abcd);
    }

    /** Test the examples from the insertFront method. */
    public void testInsertFrontExamples() {
        assertEquals(abc.insertFront(cd), dabc);
    }

    /** Test the examples from the replaceItemAt method. */
    public void testReplaceItemAtExamples() {
        try {
            assertEquals(abcde.replaceItemAt(1, cx).int_size(), axcde.int_size());
            assertEquals(abcde.replaceItemAt(1, cx), axcde);
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

    /** Test the examples from the subsequence method. */
    public void testSubsequenceExamples() {
        try {
            assertEquals(abcde.subsequence(1, 3), bc);
            assertEquals(abcde.subsequence(0, 3), abc);
            for (int i = 0; i < 5; i++) {
                assertEquals(abcde.subsequence(i, i), emptySeq);
            }
        } catch (JMLSequenceException e) {
            fail("indexing here should not raise a JMLSequenceException");
        }
    }

}
