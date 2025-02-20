
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;


public class Output {
    public static final DecimalFormat fm = new DecimalFormat("#,###");
    static {
        DecimalFormatSymbols symbols = fm.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        fm.setDecimalFormatSymbols(symbols);
    }

    public static final int[] colourCodes = {
        196, 34, 220, 21, 201, 51, 208, 46, 202, 27, 200, 129, 118, 
        172, 15, 87, 214, 160, 82, 226, 33, 201, 141, 88, 230, 244
    };

    public static void printBlock(char[][] block){
        for (int i = 0; i < block.length; i++){
            for (int j = 0; j < block[0].length; j++){
                char currLetter = block[i][j];
                if (currLetter == '.') {
                    System.out.print(" ");
                    continue;
                }
                int colourCode = colourCodes[currLetter - 65];
                Utils.printColorChar(currLetter, colourCode, false);
            }
            System.out.println();
        }
    }

    // public static void fileOutput(char[][] block){
        
    // }
}
