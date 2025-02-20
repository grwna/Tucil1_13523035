
// Menyimpan fungsi2 utility seperti debug ato fungsi lainnya agar class tidak terlalu panjang

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static void printCharMatrix(char[][] charMatrix){
        int rows = charMatrix.length;
        int cols = charMatrix[0].length;
        System.out.print("[\n  ");
        for (int i = 0; i < rows; i++) {
            System.out.print("[");
            for (int j = 0; j < cols; j++) {
                System.out.print("'" + charMatrix[i][j] + "'");
                if (j < cols - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print(" ]");
            if (i < rows - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n]\n");
    }
    
    public static char getLetterString(String string){
        char letter = ' ';
        for (int i = 0; i < string.length(); i++){
            char currChar = string.charAt(i);
            if ( currChar >= 'A' && currChar <= 'Z'){     
                if (letter >= 'A' && letter <= 'Z'){ // hanya di evaluate kalo letter sudah di assign sebelumnya
                    if (letter != currChar){throw new IllegalStateException("One line can only contain unique letter of the alphabet!");}
                } 
                letter = currChar;
            }
        }
        return letter;
    }

        /* 
    *  Simpan line di list, jika curr alphabet != last alphabet (maka blok di instansiasi dan simpan ke list of objek)\
    *  kemudian list nya di clear, dan ulang lagi untuk next block
    */
    public static ArrayList<Block> parseBlocks(ArrayList<String> rawBlocks) {
        ArrayList<Block> output = new ArrayList<>();
        ArrayList<String> currentBlock = new ArrayList<>();
        char currAlphabet = ' ';
        char lastAlphabet = getLetterString(rawBlocks.get(0));
        // Cek line by line apakah valid untuk block
        for (int i = 0; i < rawBlocks.size(); i++){
            String currLine = rawBlocks.get(i);
            currAlphabet = getLetterString(currLine);
            if (currAlphabet != lastAlphabet){  // Jika currAlphabet tidak sama dengan last, maka currLine bagian dari blok baru
                try {
                    // Pake new ArrayList untuk ngecopy last current block
                    output.add(new Block(lastAlphabet , new ArrayList<>(currentBlock)));
                    lastAlphabet = currAlphabet;
                    currentBlock.clear();
                } catch (IllegalStateException e) {
                    throw new IllegalStateException("Failed to create block!");
                }
            }

            currentBlock.add(currLine); 
        }
        
        
        // Handle block terakhir
        try {
            output.add(new Block(currAlphabet, currentBlock));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Failed to create block!");
        }
        return output;
    }

    public static ArrayList<String> parseCustomBoard(BufferedReader reader, int N, int M) throws IOException{
        ArrayList<String> parsedBoard = new ArrayList<>();
        String line;
        for (int i = 0; i < N; i++){
            line = reader.readLine();
            if (Utils.containsInvalidChars(line, true)){
                throw new IllegalStateException("Custom board can only contain \'.\' or \'X\', also ensure the dimensions of the board are correct");
            }  
            if (line.length() != M){
                throw new IllegalStateException("Dimensions of board inconsistent with declared dimension");
            }
            parsedBoard.add(line);
        }
        return parsedBoard;
    }

    // Bonus Custom
    // Cek di line ada invalid karakter atau tidak
    public static boolean containsInvalidChars(String str, boolean trueIfBoardfalseIfBlock) {   // W parameter
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (trueIfBoardfalseIfBlock) {
                if (c != '.' && c != 'X') {
                    return true;
                }
            } else {
                if (c < 'A' || c > 'Z') {
                    if (c != ' '){return true;}
                }
            }
            
        }
        return false;
    }

    public static void printColorChar(char whattoprint, int colourCode, boolean newline){
        System.out.print("\u001B[38;5;" + colourCode + "m" + whattoprint + "\u001B[0m");
        if (newline) {
            System.out.println();
        }
    }
    public static void printColorStr(String whattoprint, int colourCode, boolean newline){
        System.out.print("\u001B[38;5;" + colourCode + "m" + whattoprint + "\u001B[0m");
        if (newline) {
            System.out.println();
        }
    }

    public static void border() {
        System.out.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void enterToContinue() {
        System.out.print("\nPress enter to continue...");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        System.out.println();
    }
}