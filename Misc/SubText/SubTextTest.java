import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SubTextTest {

	/**
	 * This test is to test my claim that using the map improves efficiency
	 * when the same text is queried multiple times
	 */
	@Test
	void speed()
	{
		String subtext = "sample";
		List<Integer> pos = Arrays.asList(0, 100, 268, 584, 2288, 7987, 50432);
		String text = generateTestString(100000, subtext, pos);
		
		// first test the static way 1000 times
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			SubText.findSubTextIndexes(text, subtext);
		}
		long firstLength = System.currentTimeMillis() - time;
		System.out.println("Method 1: " +firstLength + "ms");
		
		// now test the map method 1000 times
		time = System.currentTimeMillis();
		SubText st = new SubText(text);
		for (int i = 0; i < 1000; i++) {
			st.checkSubText(subtext);
		}
		long secondLength = System.currentTimeMillis() - time;
		System.out.println("Method 2: " +secondLength + "ms");
		
		// second method should be faster if multiple queries are made
		assertTrue(secondLength < firstLength);
	}
	
	// The remaining tests test each function of SubText in turn
	
	@Test
	void testIndexText()
	{
		String subtext = "test";
		List<Integer> pos = Arrays.asList(0, 11, 40, 61, 96);
		String text = generateTestString(100, subtext, pos);
		
		HashMap<Character, ArrayList<Integer>> map = SubText.indexText(text);
		
//		System.out.println(map);
		
		// confirm the location of all occurrences of a chosen letter:
		ArrayList<Integer> ss = map.get('s');
		for (Integer i : ss) {
			if (text.charAt(i) != 's') {
				fail("Not an s at index" + i);
			}
		}
	}
	
	@Test
	void testCheckSubText() {
		String subtext = "test";
		List<Integer> pos = Arrays.asList(0, 12, 36, 57, 96);
		String text = generateTestString(100, subtext, pos);
		
//		System.out.println(text);
		
		SubText st = new SubText(text);
		
		// test both case sensitive and not
		ArrayList<Integer> test1 = st.checkSubText(subtext, true);
		ArrayList<Integer> test2 = st.checkSubText(subtext.toUpperCase(), false);
		ArrayList<Integer> test3 = st.checkSubText("won't be here", false);
		assertEquals(pos, test1);
		assertEquals(pos, test2);
		assertEquals(new ArrayList<Integer>(), test3);
	}
	
	@Test 
	void testCheckFrom() {
		// make test data
		// (as the word "test" ends in a 't', it also checks that 
		//  the function doesn't overrun the bounds of text)
		String subtext = "test";
		List<Integer> pos = Arrays.asList(0, 10, 20, 45, 75, 89);
		String text = generateTestString(100, "test", pos);
		// test case sensitive
		assertTrue(SubText.checkFrom(0, text, subtext, true));
		// test not
		assertTrue(SubText.checkFrom(0, text, subtext.toUpperCase(), false));
	}
	
	@Test
	void testFindSubTextIndexes() {	
		String subtext = "test";
		List<Integer> pos = Arrays.asList(0, 14, 18, 44, 96);
		String text = generateTestString(100, subtext, pos);
		
//		System.out.println(text);
		
		// test both case sensitive and not
		ArrayList<Integer> test1 = SubText.findSubTextIndexes(text, subtext, true);
		ArrayList<Integer> test2 = SubText.findSubTextIndexes(text, subtext.toUpperCase(), false);
		ArrayList<Integer> test3 = SubText.findSubTextIndexes(text, "won't be here", true);
		assertEquals(pos, test1);
		assertEquals(pos, test2);
		assertEquals(new ArrayList<Integer>(), test3);
	}
	
	@Test
	void testCheckChar() {
		Random r = new Random();
		// first, random characters, not case-sensitive
		for (int i = 0; i < 100; i++) {
			int n1 = r.nextInt(94);
			int n2 = r.nextInt(94);
			char rand_c_1 = (char)(n1 + ' ');
			char rand_c_2 = (char)(n2 + ' ');
			boolean test = SubText.checkChar(rand_c_1, rand_c_2, true);
			assertEquals(test, n1==n2);
		}
		// second, identical characters
		assertTrue(SubText.checkChar('a', 'a', true));
		assertTrue(SubText.checkChar('a', 'A', false));
		assertTrue(SubText.checkChar(',', ',', true));
		assertTrue(SubText.checkChar(',', ',', false));
	}
	
	/**
	 * Generate a string with subtext inserted into it at indexes in pos
	 * @return
	 */
	private String generateTestString(int length, String subtext, List<Integer> pos) {
		Random r = new Random();
		// make a random string with subtext inserted in set places
		char[] textArray = new char[length];
		for (int i = 0; i < textArray.length; i++) {
			if (pos.contains(i)) {
				for (int j = 0; j < subtext.length(); j++) {
					textArray[i+j] = subtext.charAt(j);
				}
				i += subtext.length()-1;
			}
			else {
				textArray[i] = (char)(r.nextInt(94) + ' ');
			}
		}
		return new String(textArray);
	}
}
