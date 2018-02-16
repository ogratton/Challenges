import java.lang.Math;

/**
 * Tom's Twitter Challenge
 * 
 * Given a grid, how many routes are there from the top left
 * corner to the bottom right if you can only move right, down,
 * or diagonally (i.e. right-down)?
 * @author oliver
 *
 */
public class Kraken {
	
	public static int solve(int n, int m) {
		int l = Math.max(n,m); // array length
		int k = Math.min(n,m); // number of iterations
		
		// setup an array of 1s of length l
		int[] a = new int[l];
		for (int i = 0; i < a.length; i++) {
			a[i] = 1;
		}
		
		int diag = 1;
		
		// calc each following row		
		for (int i=1; i<k; i++) {
			diag = a[i-1];
			a[i-1] = a[i];
			for(int j=i; j<l; j++) {
				int temp = diag;
				diag = a[j];
				a[j] = temp + diag + a[j-1]; // here diag is actually above		
			}
		}
		
		return a[l-1];
	}
	
	public static void main(String[] args) {
		// TODO overflow errors
		System.out.println(Kraken.solve(5, 4));
	}
}
