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
    /* TODO: Perbaiki Error Message */
    private static final char[] alphabets = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    public int N;
    public int M;
    public int P;
    public String S;
    public List<Block> puzzleBlocks;

    public Input(int[] NMP, String S, List<Block> listOfBlocks){
        this.N = NMP[0];
        this.M = NMP[1];
        this.P = NMP[2];
        this.S = S;
        this.puzzleBlocks = listOfBlocks;
    }

    public static Input handleFileInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Masukkan nama file input (.txt): ");
            String filepath = "test/" + scanner.nextLine();
            scanner.close();
            System.out.println(filepath);

            File inputFile = new File(filepath);
            BufferedReader reader = new BufferedReader(new FileReader((inputFile)));
            // FileReader reader = (new FileReader((inputFile)));
            System.out.println("File contents:");

            String firstLine = reader.readLine();
            
            // Pastikan baris pertama ada 3 integer
            if (!firstLine.matches("\\d+\\s+\\d+\\s+\\d+\\s*$")){
                reader.close();
                throw new IOException("Invalid line: " + firstLine);
            }

            int[] firstLineList = Arrays.stream(firstLine.split(" "))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();

            if (firstLineList[2] < 0 || firstLineList[2] > 26) {
                reader.close();
                throw new IllegalStateException("Invalid amount for puzzle blocks: " + firstLineList[2]);
            }

            String secondLine = reader.readLine();
            if (!"DEFAULT".equals(secondLine)){
                reader.close();
                throw new IllegalStateException("Invalid mode: " + secondLine);
            }

            ArrayList<String> blockInputs = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    blockInputs.add(line);      // Ignore empty lines (dihandle tidak dianggap error)
                }
            }
            reader.close();
            
            ArrayList<Block> listOfPuzzleBlocks = parseBlocks(blockInputs);

            // cek apakah jumlah sesuai dengan P, agar tidak terlalu banyak variabel, pake list accessing langsung
            if (listOfPuzzleBlocks.size() != firstLineList[2]){ 
                if (listOfPuzzleBlocks.size() > firstLineList[2]){
                    int lastIdx;
                    while ((lastIdx = listOfPuzzleBlocks.size()) > firstLineList[2]){
                        listOfPuzzleBlocks.remove(lastIdx-1);
                    }
                } else { 
                    throw new IllegalStateException("Amount of blocks doesnt match what is declared!"); }
            }

            
            // DEBUG
            // for (int i = 0; i < listOfPuzzleBlocks.size(); i++){
            //     System.out.println(listOfPuzzleBlocks.get(i).id);
            // }
            
            Input result = new Input(firstLineList, secondLine, listOfPuzzleBlocks);
            int boardSize = result.N * result.M;
            int blocksEffectiveCells = result.sumEffectiveCells();
            if ( boardSize != blocksEffectiveCells ) {
                throw new IllegalStateException("Ukuran papan dan jumlah blok puzzle tidak sesuai!");
            }

            return result;
        }
         catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        } catch (IllegalStateException e) {
            System.out.println("Error:\n" + e.getMessage() + "\n\nMake sure the file used contains valid information, check doc for more details");
        } catch (IOException e) {
            System.out.println("Error: IO failed!");
        }
        return null;
    }

    private static ArrayList<Block> parseBlocks(ArrayList<String> rawBlocks) {
        /* Strategi: 
        *  Simpan line di list, jika curr alphabet != last alphabet (yang ada dilist di instantiasi)\
        *  kemudian list nya di clear
        */
        int index = 0;  // Alphabet index (cek apakah sesuai urutan)
        ArrayList<Block> output = new ArrayList<>();
        
        ArrayList<String> currentBlock = new ArrayList<>();
        
        // Cek line by line apakah valid untuk block
        for (int i = 0; i < rawBlocks.size(); i++){
            String currLine = rawBlocks.get(i);
            char currChar = alphabets[index];
            boolean isLineValid = currLine.indexOf(currChar) != -1;
            if (!isLineValid) {
                if (currLine.indexOf(alphabets[(index + 1) % 26]) != -1 ){
                    try {
                            index++;
                            // Pake new ArrayList untuk ngecopy last current block
                            output.add(new Block(currChar, new ArrayList<>(currentBlock)));
                            currentBlock.clear();
                        } catch (IllegalStateException e) {
                            throw new IllegalStateException("Failed to create block!");
                        }
                } else { throw new IllegalStateException("Parsing blocks failed due to invalid input!");}
            }

            currentBlock.add(currLine);
        }
        
        // Handle last block
        try {
            output.add(new Block(alphabets[index], currentBlock));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Failed to create block!");
        }

        return output;
    }

    private int sumEffectiveCells(){
        int sum = 0;
        for(int i = 0; i < puzzleBlocks.size(); i++){
            sum += puzzleBlocks.get(i).getEffectiveCells();
        }
        return sum;
    }
}
