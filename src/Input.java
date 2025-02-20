import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Class untuk menghandle Input user (file)
public class Input {
    public static final char[] alphabets = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    public int N;
    public int M;
    public int P;
    public String S;
    public List<Block> puzzleBlocks;
    public boolean isCustom;
    public ArrayList<String> board;  

    public Input(int[] NMP, String S, List<Block> listOfBlocks, boolean isCustom, ArrayList<String> board){
        this.N = NMP[0];
        this.M = NMP[1];
        this.P = NMP[2];
        this.S = S;
        this.puzzleBlocks = listOfBlocks;
        this.isCustom = isCustom;
        this.board = board;
    }

    // Note: function is very long, not my proudest code :(
    public static Input handleFileInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter input filename (.txt): ");
            String filepath = "IO/input/" + scanner.nextLine();
            System.out.print("Checking " + filepath);

            File inputFile = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader((inputFile)));

            String firstLine = reader.readLine();
            
            // Pastikan baris pertama ada 3 integer
            if (!firstLine.matches("\\d+\\s+\\d+\\s+\\d+\\s*$")){
                reader.close();
                throw new IOException("Invalid line: " + firstLine);
            }

            int[] NMP = Arrays.stream(firstLine.split(" "))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();

            if (NMP[2] < 1 || NMP[2] > 26) {
                reader.close();
                throw new IllegalStateException("Puzzle blocks must be between 1 - 26");
            }

            System.out.print(".");  // Loading
            
            String secondLine = reader.readLine();
            if (!"DEFAULT".equals(secondLine) && !"CUSTOM".equals(secondLine)){
                reader.close();
                throw new IllegalStateException("Invalid mode: " + secondLine);
            }
            
              // Hanya untuk Custom
            boolean isCustom = "CUSTOM".equals(secondLine);
            ArrayList<String> board = new ArrayList<>();
            if (isCustom){board = Utils.parseCustomBoard(reader, NMP[0], NMP[1]);}


            //Simpan line2 pada file ke dalam list
            ArrayList<String> blockInputs = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (Utils.containsInvalidChars(line, false)){
                    throw new IllegalStateException("Found invalid characters when evaluating blocks!");
                }
                if (!line.trim().isEmpty()) { // Ignore empty lines (dihandle tidak dianggap error)
                    blockInputs.add(line);      
                }
            }
            reader.close();

            System.out.print(".");  // Loading
            
            if (blockInputs.size() < 1){
                throw new IllegalStateException("No blocks found!");
            }
            ArrayList<Block> listOfPuzzleBlocks = Utils.parseBlocks(blockInputs);
            
            // cek apakah jumlah sesuai dengan P, agar tidak terlalu banyak variabel, pake list accessing langsung
            if (listOfPuzzleBlocks.size() != NMP[2]){ 
                if (listOfPuzzleBlocks.size() > NMP[2]){
                    int lastIdx;
                    System.out.println("Warning! Amount of blocks found is more than declared, some blocks won't be included");
                    while ((lastIdx = listOfPuzzleBlocks.size()) > NMP[2]){
                        listOfPuzzleBlocks.remove(lastIdx-1);
                    }
                } else { 
                    throw new IllegalStateException("Amount of blocks found doesnt match what is declared!"); }
            } 

            System.out.println(".");  // Loading
            
            Input result = new Input(NMP, secondLine, listOfPuzzleBlocks, isCustom, board);
            int boardSize = isCustom ? Board.getEffectiveCells(board) : result.N * result.M;
            int blocksEffectiveCells = result.sumEffectiveCells();
            if ( boardSize != blocksEffectiveCells ) {
                throw new IllegalStateException("Size of board does not equal the sum of puzzle block's size!");
            }

            return result;
        }
         catch (FileNotFoundException e) {
            System.out.print("Error: File not found!");
        } catch (IllegalStateException e) {
            System.out.print("Error:\n" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: IO failed!");
        }
        return null;
    }

    // Mendapatkan jumlah ukuran blok puzzle (utk perbandingan dengan board)
    private int sumEffectiveCells(){
        int sum = 0;
        for(int i = 0; i < puzzleBlocks.size(); i++){
            sum += puzzleBlocks.get(i).getEffectiveCells();
        }
        return sum;
    }
}
