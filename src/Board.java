// Class untuk menghandle Papan puzzle serta solver dan output dari program

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


public class Board {
    public long executeTime;
    public int casesCount;
    public char[][] board;
    public boolean solved;

    public Board(int N, int M){
        this.board = new char[N][M];
        // Populate dengan ' '
        for (char[] row : this.board) {
            Arrays.fill(row, ' ');
        }
        this.casesCount = 0;
    }

    public void mainSolver(List<Block> puzzleBlocks) {
        long startTime = System.currentTimeMillis();
        if (recPuzzleSolver(puzzleBlocks, 0)) {
            Output.printBlock(board);
            this.solved = true;
        }
        else {
            System.out.println("No Solutions found for the given configuration of blocks");
        }
        this.executeTime = System.currentTimeMillis() - startTime;
        System.out.println("Execution time: " + executeTime + " ms");
        System.out.println("Cases evaluated: " + casesCount);
    }

    // Solve puzzle menggunakan algoritma Recursive Bcaktracking
    public boolean recPuzzleSolver(List<Block> puzzleBlocks, int blockIndex){
        if (blockIndex >= puzzleBlocks.size()) {
            return this.isSolved(); // Basis
        }

        Block currBlock = puzzleBlocks.get(blockIndex);

        for (int row = 0; row < board.length; row++){
            for (int col = 0; col < board[0].length;col++){

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
        for (int row = 0; row < block.length; row++){
            for (int col = 0; col < block[0].length; col++){
                if (block[row][col] != ' ') {
                    board[startRow + row][startCol + col] = block[row][col];

                }
                }
            }
    }

    public void removeBlock(char[][] block, int startRow, int startCol, char letter){
        for (int row = 0; row < block.length; row++){
            for (int col = 0; col < block[0].length; col++){
                if (block[row][col] == letter) {
                    board[startRow + row][startCol + col] = ' ';
                }
                }
            }
    }
    public boolean isPlaceable(char[][] block, int startRow, int startCol){
        int rows = block.length;
        int cols = block[0].length;

        // Cek index out of bounds
        if (startRow + rows > board.length || startCol + cols > board[0].length) {
            return false;
        }
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (block[row][col] != ' ' && board[startRow + row][startCol + col] != ' '){
                    return false;
                }
            }
        }
        return true;
    }

    public void clearBoard(){
        for (char[] boardRow : board) {
            Arrays.fill(boardRow, ' ');
        }
    }
    
}
