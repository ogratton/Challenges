import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.*;

/**
 * Class with methods for finding occurrences of "subtext" string within "text"
 * string Matches the subtext against the text, outputting the character
 * positions of the beginning of each match for the subtext within the text.
 * Allows multiple matches. Allows case-insensitive matching.
 * 
 * For one-off searches of a text, the static findSubTextIndexes() will suffice.
 * 
 * If a text is searched multiple times, it is faster to make an instance of
 * this class and use the checkSubText() method.
 * 
 * See SubTextTest for a benchmark as well as testing of every method.
 * 
 * @author Oliver Gratton
 *
 */
public class SubText {

    private HashMap<Character, ArrayList<Integer>> indexTable;
    private String text;
    
    /**
     * Load in a text ready for quicker querying
     * 
     * @param text The body of text to be indexed
     */
	public SubText(String text) {
		this.indexTable = indexText(text);
		this.text = text;
	}
    
    /**
     * Make a map of characters to their occurrences
     * Useful for large texts which are queried often
     *
     * @param text The body of text to be searched
     */
	protected static HashMap<Character, ArrayList<Integer>> indexText(String text) {

		HashMap<Character, ArrayList<Integer>> iTable = new HashMap<Character, ArrayList<Integer>>();

		for (int i = 0; i < text.length(); i++) {
			Character c = new Character(text.charAt(i));
			if (iTable.containsKey(c)) {
				iTable.get(c).add(i);
			} else {
				ArrayList<Integer> occs = new ArrayList<Integer>();
				occs.add(i);
				iTable.put(c, occs);
			}
		}
		return iTable;
	}
    
    /**
     * Using the map made by indexText, quickly find all the occurrences
     * without having to traverse the whole text
     * 
     * @param subtext The string to be searched for
     * @param caseSens True if matches are to be case sensitive
     */
    public ArrayList<Integer> checkSubText(String subtext, boolean caseSens) {
        
    	// look up the first character in the pre-built map
        char c = caseSens ? subtext.charAt(0) : Character.toLowerCase(subtext.charAt(0));
        Character firstChar = new Character(c);
        ArrayList<Integer> firstCharOccs = new ArrayList<Integer>();
        if (indexTable.containsKey(firstChar)) {
            firstCharOccs.addAll(indexTable.get(firstChar));
        }
        // if case insensitive, look up the upper case letters too
        if (!caseSens && Character.isAlphabetic(firstChar)) {
        	Character upperC = Character.toUpperCase(firstChar.charValue());
        	if(indexTable.containsKey(upperC)) {
        		firstCharOccs.addAll(indexTable.get(upperC));
        	}
        }

        // keep only occurrences which satisfy the subtext check
        firstCharOccs = firstCharOccs.stream()
        		            .filter(x -> checkFrom(x, text, subtext, caseSens))
                            .collect(Collectors.toCollection(ArrayList::new));
        // sort list (as uppercase are done after lower)
        if (!caseSens) firstCharOccs.sort((p1, p2) -> p1.compareTo(p2));
        
        return firstCharOccs;
        // (The same thing but for Java < 1.8:)
        /*
        ArrayList<Integer> occs = new ArrayList<Integer>();
        for (Integer occ : firstCharOccs) {
            if (checkFrom(occ, text, subtext, caseSens)) {
                occs.add(occ);
            }
        } 
        // won't be sorted
        return occs;
        */
    }    
    
    /**
     * Using the map made by indexText, quickly find all the occurrences
     * without having to traverse the whole text.
     * Case insensitive.
     * 
     * @param subtext The string to be searched for
     */
    public ArrayList<Integer> checkSubText(String subtext) {
        return checkSubText(subtext, false);
    }

    /**
     * Check if "text" contains "subtext" starting from "index"
     *
     * @param index    Index of text to start from
     * @param text     The body of text to be searched
     * @param subtext  The string to be searched for within text
     * @param caseSens True if the match is to be case sensitive
     */
	protected static boolean checkFrom(int index, String text, String subtext, boolean caseSens) {
		int len = text.length();
		for (int i = 0; i < subtext.length(); i++) {
			// if the end of the text has been reached
			if (index + i >= len)
				return false;
			if (!checkChar(text.charAt(index + i), subtext.charAt(i), caseSens)) {
				return false;
			}
		}
		return true;
	}

    /**
     * Find all occurrences of subtext in text and return a list of the indexes.
     * 
     * @param text     The body of text to be searched
     * @param subtext  The string to be searched for within text
     * @param caseSens True if matches are to be case sensitive
     */
	public static ArrayList<Integer> findSubTextIndexes(String text, String subtext, boolean caseSens) {

		ArrayList<Integer> occurrences = new ArrayList<Integer>();
		char firstChar = subtext.charAt(0);

		for (int i = 0; i < text.length(); i++) {
			if (checkChar(text.charAt(i), firstChar, caseSens)) {
				if (checkFrom(i, text, subtext, caseSens)) {
					occurrences.add(i);
				}
			}
		}

		return occurrences;
	}
    
    /**
     * Find all occurrences of subtext in text and return a list of the indexes.
     * Case insensitive.
     * 
     * @param text     The body of text to be searched
     * @param subtext  The string to be searched for within text
     */
    public static ArrayList<Integer> findSubTextIndexes(String text, String subtext) {
        return findSubTextIndexes(text, subtext, false);
    }
    
    /**
     * Compare two characters
     *
     * @param c
     * @param d
     * @param caseSens true if case sensitive
     */
    protected static boolean checkChar(Character c, Character d, boolean caseSens) {
        if(caseSens) {
            return c.equals(d);
        }
        else {
            return Character.toLowerCase(c) == Character.toLowerCase(d);
        }
    }

    public static void main(String[] args) {   
    
    	//String text = "How much wood would a woodchuck chuck, if a Woodchuck could chuck wood?";
    	//String sub = "wood";
//    	String text = "testy`%hu0Z#:Mtesttest!W(P\"*o|PzK*g2O6I;5,Q(test]fcoE\"j${=[QP }O;u'M1+N?w2ba#xMf[Fi7Tp-$IO>:GiObtest";
//        String sub = "test";
        String text = "woodWOOD46u4u4eedfwOod88j8jw";
        String sub = "wood";
        
        // static way
        System.out.println("Static method: " + findSubTextIndexes(text, sub));
        System.out.println("Static method: (case sensitive) " + findSubTextIndexes(text, sub, true));
        
        // map way
        SubText st = new SubText(text);
        System.out.println("Map method: " + st.checkSubText(sub));
        System.out.println("Map method (case sensitive) " + st.checkSubText(sub, true));
        
    }
}
