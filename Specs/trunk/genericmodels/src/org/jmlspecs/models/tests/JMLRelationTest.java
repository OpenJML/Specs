package org.jmlspecs.models.tests;

import junit.framework.TestCase;
import org.jmlspecs.models.*;

public class JMLRelationTest extends TestCase {
	private JMLValueToValueRelation<JMLInteger, JMLString> vvr;
	private JMLEqualsToValueRelation<Integer, JMLString> evr;
	private JMLObjectToEqualsRelation<Integer, String> oer;
	private JMLInteger three, three2;
	private Integer four;
	private JMLString foo, foo2;
	private String bar;
	
	public void setUp() {
		three = new JMLInteger(3);
		three2 = new JMLInteger(3);
		four = new Integer(4);
		foo = new JMLString("foo");
		foo2 = new JMLString("foo");
		bar = "bar";
		vvr = new JMLValueToValueRelation<JMLInteger, JMLString>();
		vvr = vvr.add(three, foo);
		evr = new JMLEqualsToValueRelation<Integer, JMLString>();
		evr = evr.add(four, foo);
		oer = new JMLObjectToEqualsRelation<Integer, String>();
		oer = oer.add(four, bar);		
	}
	
	public void testIsDefinedAt() {
		assertTrue(vvr.isDefinedAt(three2));
		assertTrue(evr.isDefinedAt(new Integer(4)));
		assertFalse(oer.isDefinedAt(new Integer(4)));
	}
	
	public void testHas() {
		assertTrue(vvr.has(three2, foo2));
		assertTrue(evr.has(new Integer(4), foo2));
		assertTrue(oer.has(four, bar));
		assertFalse(oer.has(new Integer(4), new String("bar")));
	}
	
	public void testElementImage() {
		JMLValueSet<JMLString> res = vvr.elementImage(three2);
		assertEquals(res.int_size(), 1);
		res = evr.elementImage(new Integer(4));
		assertEquals(res.int_size(), 1);
		JMLEqualsSet<String> res2 = oer.elementImage(new Integer(4));
		assertEquals(res2.int_size(), 0);
	}

	public void testRemove() {
		vvr = vvr.remove(three2, foo2);
		assertEquals(vvr.int_size(), 0);
		evr = evr.remove(new Integer(4), foo2);
		assertEquals(evr.int_size(), 0);
		oer = oer.remove(new Integer(4), new String("bar"));
		assertEquals(oer.int_size(), 1);
	}
	
	public void testCompose() {
		JMLObjectToValueRelation<Character, JMLInteger> other = new JMLObjectToValueRelation<Character, JMLInteger>();
		Character c = new Character('c');
		Character d = new Character('d');
		other = other.add(c, three);
		other = other.add(d, three2);
		other = other.add(new Character('f'), new JMLInteger(4));
		JMLObjectToValueRelation<Character, JMLString> res = vvr.compose(other);
		assertEquals(res.int_size(), 2);
		assertTrue(res.has(c, foo));
		assertTrue(res.has(d, foo));
		
		JMLValueToObjectRelation<JMLChar, Integer> other2 = new JMLValueToObjectRelation<JMLChar, Integer>();
		other2 = other2.add(new JMLChar('c'), four);
		other2 = other2.add(new JMLChar('d'), new Integer(5));
		
		JMLValueToEqualsRelation<JMLChar, String> res2 = oer.compose(other2);
		assertEquals(res2.int_size(), 1);
		assertTrue(res2.has(new JMLChar('c'), new String("bar")));
	}
}
