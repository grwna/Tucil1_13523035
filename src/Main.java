public class Main {
    /*
     TODO:
     - Rename Variables
     */
    public static void main(String[] args) {
        Input fileInputData = Input.handleFileInput();
        Board puzzleBoard = new Board(fileInputData.N, fileInputData.M);
        puzzleBoard.mainSolver(fileInputData.puzzleBlocks);

    }
}

