import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static String input;
    public static void main(String[] args) {

        Utils.clearScreen();
        System.out.println("""
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
                                                                   
                                                                                                                 
                """);
        Utils.enterToContinue();
        Input fileInputData = Input.handleFileInput();
        if (fileInputData == null){
            System.exit(0);
        }
        Board puzzleBoard = new Board(fileInputData.N, fileInputData.M);
        System.out.println("\nSuccess loading file!\n");
        if (fileInputData.isCustom){
            puzzleBoard.buildCustomBoard(fileInputData.board);
        }
        System.out.println("Starting solver...\n");
        puzzleBoard.solver(fileInputData.puzzleBlocks);
        
        while (true){
                System.out.print("Save solution? (Y/N) ");
                input = scan.nextLine();
                if (!input.equals("Y") && !input.equals("N")){
                    System.out.println("\nInvalid option!");
                    Utils.enterToContinue();
                    Utils.clearScreen();
                }
                else {break;}
        }
        if (input.equals("Y"))
        while (true) { 
            
        }
        Output.handleFileOutput(puzzleBoard);
    }
}

