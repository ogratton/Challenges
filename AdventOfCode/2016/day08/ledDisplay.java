package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GitHub user Stickerpants on 07/12/2016.
 */
public class ledDisplay {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("src/day08/input.txt"));
        Pattern rect = Pattern.compile("rect (\\d+)x(\\d+)");
        Pattern rotRow = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
        Pattern rotCol = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
        
        boolean screen[][] = new boolean[6][50];
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            
            Matcher rectM = rect.matcher(line);
            Matcher rotRowM = rotRow.matcher(line);
            Matcher rotColM = rotCol.matcher(line);
            
            if(rectM.find()) {
                int col = Integer.parseInt(rectM.group(1));
                int row = Integer.parseInt(rectM.group(2));
                
                for(int r = 0; r < row; r++) {
                    for(int c = 0; c < col; c++) {
                        screen[r][c] = true;
                    }
                }
            }
            else if(rotRowM.find()) {
                int row = Integer.parseInt(rotRowM.group(1));
                int count = Integer.parseInt(rotRowM.group(2));
                
                rightRotate(screen[row], count);
            }
            else if(rotColM.find()) {
                int col = Integer.parseInt(rotColM.group(1));
                int count = Integer.parseInt(rotColM.group(2));
                
                boolean[] trans = getColumn(screen, col);
                rightRotate(trans, count);
                setColumn(screen, col, trans);
            }

            System.out.println(line);
            showScreen(screen);
        }
        
        long count = 0;
        for(int row = 0; row < screen.length; row++) {
            for(int col = 0; col < screen[row].length; col++) {
                if(screen[row][col]) {
                    count++;
                }
            }
        }
        System.out.println("Count: " + count);
        sc.close();
    }
    
    public static boolean[] getColumn(boolean[][] scr, int col) {
        boolean[] ret = new boolean[scr.length];
        for(int c = 0; c < ret.length; c++) {
            ret[c] = scr[c][col];
        }
        
        return ret;
    }

    public static void setColumn(boolean[][] scr, int col, boolean[] fill) {
        for(int c = 0; c < fill.length; c++) {
            scr[c][col] = fill[c];
        }
    }
    
    public static void rightRotate(boolean[] arr, int count) {
        count = count % arr.length;
        reverse(arr, 0, arr.length - count);
        reverse(arr, arr.length - count, arr.length);
        reverse(arr, 0, arr.length);
    }
    
    public static void reverse(boolean[] arr, int from, int to) {
        for(int i = 0; i < (to - from) / 2; i++) {
            boolean temp = arr[from + i];
            arr[from + i] = arr[to - 1 - i];
            arr[to - 1 - i] = temp;
        }
    }
    
    public static void showScreen(boolean[][] screen) {
        for(int row = 0; row < screen.length; row++) {
            for(int col = 0; col < screen[row].length; col++) {
                System.out.print(screen[row][col] ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }
}