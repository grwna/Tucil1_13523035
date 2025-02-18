import java.util.HashMap;
import java.util.Map;

public class Output {
    private static final Map<Character, String> COLOR_MAP = new HashMap<>();
    static {
        COLOR_MAP.put('A', "\u001B[31m");  // Red
        COLOR_MAP.put('B', "\u001B[32m");  // Green
        COLOR_MAP.put('C', "\u001B[33m");  // Yellow
        COLOR_MAP.put('D', "\u001B[34m");  // Blue
        COLOR_MAP.put('E', "\u001B[35m");  // Magenta
        COLOR_MAP.put('F', "\u001B[36m");  // Cyan
        COLOR_MAP.put('G', "\u001B[91m");  // Bright Red
        COLOR_MAP.put('H', "\u001B[92m");  // Bright Green
        COLOR_MAP.put('I', "\u001B[93m");  // Bright Yellow
        COLOR_MAP.put('J', "\u001B[94m");  // Bright Blue
        COLOR_MAP.put('K', "\u001B[95m");  // Bright Magenta
        COLOR_MAP.put('L', "\u001B[96m");  // Bright Cyan
        COLOR_MAP.put('M', "\u001B[97m");  // White
        COLOR_MAP.put('N', "\u001B[90m");  // Gray
        COLOR_MAP.put('O', "\u001B[30m");  // Black
        COLOR_MAP.put('P', "\u001B[41m");  // Red Background
        COLOR_MAP.put('Q', "\u001B[42m");  // Green Background
        COLOR_MAP.put('R', "\u001B[43m");  // Yellow Background
        COLOR_MAP.put('S', "\u001B[44m");  // Blue Background
        COLOR_MAP.put('T', "\u001B[45m");  // Magenta Background
        COLOR_MAP.put('U', "\u001B[46m");  // Cyan Background
        COLOR_MAP.put('V', "\u001B[100m"); // Bright Gray
        COLOR_MAP.put('W', "\u001B[101m"); // Bright Red Background
        COLOR_MAP.put('X', "\u001B[102m"); // Bright Green Background
        COLOR_MAP.put('Y', "\u001B[103m"); // Bright Yellow Background
        COLOR_MAP.put('Z', "\u001B[104m"); // Bright Blue Background
    }
    private static final String RESET_COLORING = "\u001B[0m";

    public static void printBlock(char[][] block){
        for (int i = 0; i < block.length; i++){
            for (int j = 0; j < block[0].length; j++){
                char currLetter = block[i][j];
                String color = COLOR_MAP.getOrDefault(currLetter, RESET_COLORING);
                System.out.print(color + currLetter + RESET_COLORING);
            }
            System.out.println();
        }
    }

    // public static void fileOutput(char[][] block){
        
    // }
}
