import java.util.ArrayList;
import java.util.Arrays;

public class Block {
    public  char id;
    public char[][] shape; //ini yang benar, bawah untuk debug aja
    public int rows;
    public int cols;

    public Block(char id, ArrayList<String> shape){
        this.id = id;
        this.shape = filterEmptyColumns(padAndFormatShape(shape));
        this.rows = this.shape.length;
        this.cols = this.shape[0].length;
    }

    // Mengembalikan jumlah cell efektif, yaitu cell yang berisikan huruf (untuk keperluan menghitung papan)
    public int getEffectiveCells(){
        int effCells = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (shape[i][j] == id) effCells++;
                
            }
        }
        return effCells;
    }


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
    
    // TODO: hapus kolom dimana semua elemen pada satu kolom = ' ' 
    private static ArrayList<char[]> padAndFormatShape(ArrayList<String> shape){
        ArrayList<char[]> formattedShape = new ArrayList<>();
        
        int row = shape.size();
        int maxCols = 0;
        for (String line : shape) {
            maxCols = Math.max(maxCols, line.length());
        }

        for (int i = 0; i < row; i++) {
            char[] currRow = new char[maxCols];
            Arrays.fill(currRow, ' ');  // Pre populate
            String currentLine = shape.get(i);
            // Fill with actual characters
            for (int j = 0; j < currentLine.length(); j++) {
                currRow[j] = currentLine.charAt(j);
            }
            
            formattedShape.add(currRow);
        }
        return formattedShape;
    }

    // Menghapus kolom "kosong", yaitu kolom yang tidak berisi huruf
    private static char[][] filterEmptyColumns(ArrayList<char[]> formattedShape) {
        int rows = formattedShape.size();
        int cols = formattedShape.get(0).length;
        boolean[] keepColumn = new boolean[cols];
        int finalCols = 0;

        // Menandai kolom2 yang dikeep
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (formattedShape.get(i)[j] != ' ') {
                    keepColumn[j] = true;
                    finalCols++;
                    break;
                }
            }
        }

        char[][] filteredShape = new char[rows][finalCols];
        for (int i = 0; i < rows; i++) {
            int newCol = 0;
            for (int j = 0; j < cols; j++) {
                if (keepColumn[j]) {
                    filteredShape[i][newCol++] = formattedShape.get(i)[j];
                }
            }
        }
        
        return filteredShape;
    }


    // Merotasi block sebanyak 90 degree cw, sebanyak 'rotation' kali
    public char[][] rotateBlock(int rotation) {
        char[][] rotatedShape = new char[rows][cols];
        int rows = this.rows;
        int cols = this.cols;
        switch (rotation) {
            default:
                return this.shape;
            case 1: // 90 deg
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        rotatedShape[j][rows - 1 - i] = shape[i][j];}}
                break;
            case 2: // 180 deg
                rotatedShape = new char[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        rotatedShape[rows - 1 - i][cols - 1 - j] = shape[i][j];}}
                break;
            case 3: // 270 deg
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        rotatedShape[cols - 1 - j][i] = shape[i][j];}}
                break;
        }
        return rotatedShape;
    }

    public char[][] mirrorBlock(int mirrorMode){
            char[][] mirrored = new char[rows][cols];
            switch (mirrorMode) {
                case 0: // No mirror
                    return shape;
                case 1: // Horizontal
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            mirrored[i][cols - 1 - j] = shape[i][j];}}
                    break;
                case 2: // Vertical
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            mirrored[rows - 1 - i][j] = shape[i][j];}}
                    break;
            }
            return mirrored;
    }
}