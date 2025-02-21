// Class untuk menghandle Papan puzzle serta solver dan output dari program

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {
    public long executeTime = 0;
    public int casesCount = 0;
    public char[][] board;
    public boolean solved = false;
    int rows;
    int cols;

    public Board(int N, int M){
        this.board = new char[N][M];
        // Populate dengan ' '
        for (char[] row : this.board) {
            Arrays.fill(row, ' ');
        }
        this.rows = N;
        this.cols = M;
    }

    public void solver(List<Block> puzzleBlocks) {
        long startTime = System.currentTimeMillis();
        if (recPuzzleSolver(puzzleBlocks, 0)) {
            System.out.println("Found a solution!\n");
            Output.printBlock(board);
            solved = true;
        }
        else {
            System.out.println("No Solutions found for the given configuration of blocks");
        }
        this.executeTime = System.currentTimeMillis() - startTime;
        System.out.print("\nExecution time: ");
        Utils.printColorStr(Output.fm.format(executeTime) + " ms", 10, true);
        System.out.print("Cases evaluated: ");
        Utils.printColorStr("" + Output.fm.format(casesCount) + "\n", 10, true);
    }

    // Solve puzzle menggunakan Recursive Bcaktracking
    public boolean recPuzzleSolver(List<Block> puzzleBlocks, int blockIndex){
        if (blockIndex >= puzzleBlocks.size()) {
            return this.isSolved(); // Basis
        }

        Block currBlock = puzzleBlocks.get(blockIndex);

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols;col++){

                // Cek setiap rotasi dan pencerminan
                for (int rot = 0; rot < 4; rot++){
                    char[][] rotatedBlock = currBlock.rotateBlock(rot);
                    for(int mirror = 0; mirror < 3; mirror++){
                        char[][] mirroredBlock = Block.mirrorBlock(rotatedBlock, mirror);
                        
                        casesCount++;
                        if (isPlaceable(mirroredBlock, row, col)){
                            placeBlock(mirroredBlock, row, col);
                            
                            // Rekursi
                            if (recPuzzleSolver(puzzleBlocks, blockIndex+1)){
                                return true;
                            } else {
                                casesCount++;
                                removeBlock(mirroredBlock, row, col, puzzleBlocks.get(blockIndex).id);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isSolved(){
        for (char[] boardRow : board){
            for (char element : boardRow){
                if (element == ' ') return false;
            }
        }
        return true;
    }

    public void placeBlock(char[][] block, int startRow, int startCol){
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[0].length; col++) {
                if (block[row][col] != ' ') {
                    board[startRow + row][startCol + col] = block[row][col];

                }
                }
            }
    }

    public void removeBlock(char[][] block, int startRow, int startCol, char letter){
        for (int row = 0; row < block.length; row++) {
            for (int col = 0; col < block[0].length; col++) {
                if (block[row][col] == letter) {
                    board[startRow + row][startCol + col] = ' ';
                }
                }
            }
    }
    public boolean isPlaceable(char[][] block, int startRow, int startCol){
        int rowus = block.length;
        int colus = block[0].length;
        // Cek index out of bounds
        if (startRow + rowus > rows || startCol + colus > cols) {
            return false;
        }
        for (int row = 0; row < block.length; row++){
            for (int col = 0; col < block[0].length; col++){
                if (block[row][col] != ' ' && board[startRow + row][startCol + col] != ' '){
                    return false;
                }
            }
        }
        return true;
    }

    /* ================== BONUS CUSTOM ========================== */
    // Ingat! ini dipanggil sesudah N dan M di set
    public void buildCustomBoard(ArrayList<String> parsedBoard){
        char curr;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                curr = parsedBoard.get(i).charAt(j);
                this.board[i][j] = curr == 'X' ? ' ' : curr;
            }
        }
    }

    public static int getEffectiveCells(ArrayList<String> board){
        int effCells = 0;
        for (int i = 0; i < board.size(); i++ ){
            for (int j = 0; j < board.get(i).length(); j++){
                if (board.get(i).charAt(j) == 'X') {effCells++;}
            }
        }
        return effCells;
    }
}
