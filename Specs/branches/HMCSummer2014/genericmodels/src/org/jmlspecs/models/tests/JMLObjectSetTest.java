
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
import java.util.Enumeration;

/** A test case for {@link org.jmlspecs.models.JMLObjectSet}.
 *
 * @version $Revision: 1.2 $
 * @author Tim Wahls
 */
public class JMLObjectSetTest extends TestCase {

    public JMLObjectSetTest(String name) {
        super(name);
    }
    public static void main (String[] args) {
        junit.textui.TestRunner.run (suite());
    }
    public static junit.framework.Test suite() {
        return new junit.framework.TestSuite(JMLObjectSetTest.class);
    }

    protected JMLObjectSet<Integer> s;
    protected Integer i1, i2, i3;

    protected void setUp() {
      s = new JMLObjectSet<Integer>();
      i1 = new Integer(1);
      i2 = new Integer(2);
      i3 = new Integer(3);
      s = s.insert(i1).insert(i2).insert(i3);
    }

    /** test powerSet */
    public void testPowerSet() {
    	JMLObjectSet<Integer> empty = (JMLObjectSet<Integer>) JMLObjectSet.EMPTY;
        assertEquals(new JMLObjectSet<JMLObjectSet<Integer>>(empty),
                         empty.powerSet());
        JMLObjectSet<JMLObjectSet<Integer>> ps = new JMLObjectSet<JMLObjectSet<Integer>>(s);
        ps = ps.insert(new JMLObjectSet<Integer>()).insert(new JMLObjectSet<Integer>(i1)).insert(
          new JMLObjectSet<Integer>(i2)).insert(new JMLObjectSet<Integer>(i3));
        ps = ps.insert(new JMLObjectSet<Integer>(i1).insert(i2));
        ps = ps.insert(new JMLObjectSet<Integer>(i1).insert(i3));
        ps = ps.insert(new JMLObjectSet<Integer>(i2).insert(i3));
        JMLObjectSet<JMLObjectSet<Integer>> ps2 = s.powerSet();
        assertEquals(ps2.int_size(), 8);
        Enumeration<JMLObjectSet<Integer>> elems = ps2.elements();
        boolean res = false;
        while (elems.hasMoreElements()) {
           res = false;
           Enumeration<JMLObjectSet<Integer>> psEnum = ps.elements();
           JMLObjectSet<Integer> s1 = elems.nextElement();
           while (psEnum.hasMoreElements()) {
             JMLObjectSet<Integer> s2 =  psEnum.nextElement();
             res = res || s1.equals(s2);
           }
           assertTrue(res);
        }
    }
}
