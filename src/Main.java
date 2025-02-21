import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static String input;
    public static void main(String[] args) {

        Utils.clearScreen();
    String title ="""
  _____ ____    _____               _             _____            
 |_   _/ __ \\  |  __ \\             | |           |  __ \\           
   | || |  | | | |__) |   _ _______| | ___ _ __  | |__) | __ ___   
   | || |  | | |  ___/ | | |_  /_  / |/ _ \\ '__| |  ___/ '__/ _ \\  
  _| || |__| | | |   | |_| |/ / / /| |  __/ |    | |   | | | (_) | 
 |_____\\___\\_\\ |_|    \\__,_/___/___|_|\\___|_|    |_|   |_|  \\___/ 
                     _____       _                                 
                    / ____|     | |                                
                   | (___   ___ | |_   _____ _ __                  
                    \\___ \\ / _ \\| \\ \\ / / _ \\ '__|                
                    ____) | (_) | |\\ V /  __/ |                    
                   |_____/ \\___/|_| \\_/ \\___|_|                    
                                                                   
                                                                                                                 
                """;

    String goodbye = """
                                                                                                        
  ,ad8888ba,                                      88  88                                    
 d8\"'    `\"8b                                     88  88                                    
d8'                                               88  88                                    
88              ,adPPYba,    ,adPPYba,    ,adPPYb,88  88,dPPYba,   8b       d8   ,adPPYba,  
88      88888  a8"     "8a  a8"     "8a  a8"    `Y88  88P'    "8a  `8b     d8'  a8P_____88  
Y8,        88  8b       d8  8b       d8  8b       88  88       d8   `8b   d8'   8PP\"\"\"\"\"\"\"  
 Y8a.    .a88  \"8a,   ,a8\"  \"8a,   ,a8"  \"8a,   ,d88  88b,   ,a8"    `8b,d8'    "8b,   ,aa  
  `\"Y88888P\"    `\"YbbdP\"\'    `\"YbbdP\"\'    `\"8bbdP\"Y8  8Y\"Ybbd8\"\'       Y88\'      `\"Ybbd8\"\'  
                                                                       d8'                  
                                                                      d8'                   
            """;

        Utils.printColorStr(title, 14, true);
        Utils.enterToContinue();
        while (true) {
            while (true) { 
                Utils.clearScreen();
                Utils.printColorStr("====== ", 190, false);
                Utils.printColorStr("IQ PUZZLE PRO SOLVER", 14, false);
                Utils.printColorStr(" ======", 190, true);
                Utils.printColorStr("\n1. Solve a puzzle", 190, true);
                Utils.printColorStr("2. Exit", 190, true);
                Utils.printColorStr("\nSelect an option: ", 190, false);
                input = scan.nextLine();
                if (input.equals("2")){
                    Utils.clearScreen();
                    Utils.printColorStr(goodbye, 9, true);
                    System.exit(0);
                } else if (input.equals("1")){
                    Utils.clearScreen();
                    break;
                }
                Utils.clearScreen();
                Utils.printColorStr("Invalid option!", 9, true);
                Utils.enterToContinue();
                Utils.clearScreen();
            }

            Input fileInputData = Input.handleFileInput();
            if (fileInputData == null){
                Utils.enterToContinue();
                continue;
            }
            Utils.clearScreen();
            Board puzzleBoard = new Board(fileInputData.N, fileInputData.M);
            System.out.println("Success loading file!\n");
            if (fileInputData.isCustom){
                puzzleBoard.buildCustomBoard(fileInputData.board);
            }
            System.out.println("Starting solver...\n");
            puzzleBoard.solver(fileInputData.puzzleBlocks);
            
            while (true){
                    Utils.printColorStr("Save solution? (Y/N) ", 190, false);
                    input = scan.nextLine().toLowerCase();
                    if (input.equals("y") || input.equals("n")){
                        break;
                    }
                    Utils.clearScreen();
                    Utils.printColorStr("Invalid option!", 9, true);
                    Utils.enterToContinue();
                    Utils.clearScreen();

            }
            if (input.equals("y"))
            while (true) {
                Utils.clearScreen();
                Utils.printColorStr("1. Output to text file (.txt)", 190, true);
                Utils.printColorStr("2. Output to image file (.png)", 190, true);
                Utils.printColorStr("\nSelect an option: ", 190, false);
                input = scan.nextLine();
                System.out.println();
                if (input.equals("1") || input.equals("2")){
                    if (input.equals("1")) {
                        Utils.clearScreen();
                        Output.handleFileOutput(puzzleBoard);
                    } else {
                        Utils.clearScreen();
                        Output.handleImageOutput(puzzleBoard);
                    }
                    break;
                }
                Utils.clearScreen();
                Utils.printColorStr("Invalid option!", 9, true);
                Utils.enterToContinue();
                Utils.clearScreen();
                }
            
        Utils.enterToContinue();
        }
    }
}

