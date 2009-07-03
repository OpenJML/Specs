// @(#)$Id: JMLObjectBagTest.java,v 1.12 2005/03/27 14:50:59 leavens Exp $

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

/**
 * A test case for <code>JMLObjectBag</code>.
 *
 */
public class JMLObjectBagTest extends TestCase {

    public JMLObjectBagTest(String name) {
        super(name);
    }
    public static void main (String[] args) {
        junit.textui.TestRunner.run (suite());
    }
    public static Test suite() {
        return new junit.framework.TestSuite(JMLObjectBagTest.class);
    }

    protected JMLObjectBag<Integer>[] b;
    protected JMLObjectBag<Integer>[] old_b;
    protected JMLObjectBag<String> b5;
    protected Integer [] e;

    protected void setUp() {
        if (b == null) {

            final int e_size = 5;
            e = new Integer[e_size];
            for (int j = 0; j < e_size - 1; j++) {
                e[j] = new Integer(j);
            }
            e[e_size - 1] = null;

            final int b_size = 5;
            b = new JMLObjectBag[b_size];
            old_b = new JMLObjectBag[b_size];
            b[0] = new JMLObjectBag<Integer>();
            b[1] = new JMLObjectBag<Integer>(e[1]);
            b[2] = new JMLObjectBag<Integer>(e[1]).insert(e[2]);
            b[3] = new JMLObjectBag<Integer>(e[3]).insert(e[3]).insert(e[3]);
            b[4] = new JMLObjectBag<Integer>(e[2]).insert(e[1]).insert(e[2]).insert(e[4]);
            b5 = JMLObjectBag
                .convertFrom(java.util.Arrays.asList(new String[] {
                    "n", "k", "i", "n"}));
            for (int i = 0; i < b_size; i++) {
                old_b[i] = (JMLObjectBag<Integer>)b[i].clone();
            }

        }
    }

    public void testJmlObjectBag() {
	assertTrue(b[0].isEmpty());
    }

    public void testJmlObjectBag_Object() {
	assertTrue(b[1].int_size() == 1);
	assertTrue(b[1].count(e[1]) == 1);
    }

    public void testSize() {
        for (int i = 0; i < 5; i++) {
            assertTrue(b[i].int_size() == i);
        }
    }

    public void testClone() {
        assertTrue(b.length == old_b.length);
        for (int i = 0; i < b.length; i++) {
            assertEquals(b[i], old_b[i]);
        }
        assertEquals(b5, b5.clone());
    }

    public void testHas() {
        for (int i = 0; i < b.length; i++) {
	    for (int j = 0; j < e.length; j++) {
		assertTrue(b[i].has(e[j]) == (b[i].count(e[j]) > 0));
	    }
        }
    }

    public void testCount() {
	for (int j = 0; j < e.length; j++) {
	    assertTrue(b[0].count(e[j]) == 0);
	    assertTrue(b[1].count(e[j]) == ((j == 1) ? 1 : 0));
	    assertTrue(b[2].count(e[j]) == ((j == 1 || j == 2) ? 1 : 0));
	    assertTrue(b[3].count(e[j]) == ((j == 3) ? 3 : 0));
        }
    }

    public void testIsEmpty() {
        for (int i = 0; i < b.length; i++) {
	    assertTrue(b[i].isEmpty() == (i == 0));
        }
    }

    public void testInsert() {
        for (int i = 0; i < b.length; i++) {
	    JMLObjectBag<Integer> nb = b[i].insert(e[3],3).insert(e[4]); // e[4] == null
	    for (int j = 0; j < e.length; j++) {
		if (j == 3)
		    assertEquals(nb.count(e[j]), b[i].count(e[j]) + 3);
		else if (j == 4)
		    assertEquals(nb.count(e[j]), b[i].count(e[j]) + 1);
		else
		    assertTrue(nb.count(e[j]) == b[i].count(e[j]));
	    }
        }
    }    

    public void testRemove() {
        for (int i = 0; i < b.length; i++) {
	    JMLObjectBag<Integer> nb = b[i].removeAll(e[2]);
	    nb = nb.remove(e[3],3).remove(e[4]); // e[4] == null
	    for (int j = 0; j < e.length; j++) {
		int cnt = b[i].count(e[j]);
		if (j == 2) 
		    cnt = 0;
		else if (j == 4 && b[i].has(e[j]))
		    cnt--;
		else if (j == 3 && b[i].has(e[j]))
		    cnt = Math.max(cnt - 3, 0);
		assertTrue(nb.count(e[j]) == cnt);
	    }
        }
    }

    public void testChoose() {
        for (int i = 0; i < b.length; i++) {
	    try {
		Object e = b[i].choose();
		assertTrue(b[i].has(e));
	    }
	    catch (JMLNoSuchElementException ex) {
		assertTrue(b[i].isEmpty());
	    }
        }
    }

    public void testElements() {
        for (int i = 0; i < b.length; i++) {
	    JMLObjectBagEnumerator<Integer> obenum = b[i].elements();
	    assertEquals(b[i].isEmpty(), !obenum.hasMoreElements());
	    JMLObjectBag<Integer> ob = new JMLObjectBag<Integer>();
	    while (obenum.hasMoreElements())
		ob = ob.insert(obenum.nextElement());
	    assertEquals(b[i], ob);
        }
    }

    public void testIsSubbag() {
        for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		boolean is_subbag = true;
		for (int k = 0; k < e.length; k++) 
		    if (!(b[i].count(e[k]) <= b[j].count(e[k]))) {
			is_subbag = false;
			break;
		    }
		assertEquals(b[i].isSubbag(b[j]), is_subbag);
	    }
        }
    }

    public void testIsProperSubbag() {
        for (int i = 0; i < b.length; i++) {
	    for (int j = 0; j < b.length; j++) {
		assertEquals(b[i].isProperSubbag(b[j]),
			     b[i].isSubbag(b[j]) && !b[i].equals(b[j]));
	    }
        }
    }

    public void testIsSuperbag() {
        for (int i = 0; i < b.length; i++) {
	    for (int j = 0; j < b.length; j++) {
		assertEquals(b[i].isSuperbag(b[j]), b[j].isSubbag(b[i]));
	    }
        }
    }

    public void testIsProperSuperbag() {
        for (int i = 0; i < b.length; i++) {
	    for (int j = 0; j < b.length; j++) {
		assertEquals(b[i].isProperSuperbag(b[j]), 
			     b[j].isProperSubbag(b[i]));
	    }
        }
    }

    public void testDifference() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                assertTrue(b[i].difference(b[j]) != null);
                for (int k = 0; k < e.length; k++) {
                    assertEquals(b[i].difference(b[j]).count(e[k]),
                       Math.max(0, b[i].count(e[k]) - b[j].count(e[k])));
                }
            }
        }
    }

    public void testInterSection() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                assertEquals(b[i].intersection(b[j]), 
			     b[j].intersection(b[i]));
                assertTrue(b[i].intersection(b[j]) != null);
                for (int k = 0; k < e.length; k++) {
                    assertEquals(b[i].intersection(b[j]).count(e[k]),
                                 Math.min(b[i].count(e[k]), b[j].count(e[k])));
                }
            }
        }
    }

    public void testUnion() {
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                assertEquals(b[i].union(b[j]), b[j].union(b[i]));
                assertTrue(b[i].union(b[j]) != null);
                for (int k = 0; k < e.length; k++) {
                    assertEquals(b[i].union(b[j]).count(e[k]),
                                 b[i].count(e[k]) + b[j].count(e[k]));
                }
            }
        }
    }
}
