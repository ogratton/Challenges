# Coding Exercise:

Given a string and a substring, give the indexes of all occurrences of that substring in the string.

All solution code is in SubText.java.
All test code is in SubTextTest.java.

Two techniques are used in the solution:
* A "static" method, which simply steps through the text once per query looking for occurrences of the first character of the subtext before checking for a complete match.
* A "map" method, which makes a table with entries (k,v) where k is the character and v is a list of the indexes where they occur in the text. Once this is made, queries can be done quickly by referring to the table to jump to the first character of the subtext.

Usage of the solution is show in the main method.

The tests require JUnit 5 to be added to the buildpath.
The easiest way to do this is via an IDE like Eclipse.

