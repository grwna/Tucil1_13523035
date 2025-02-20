import java.util.ArrayList;
import java.util.Arrays;

// Class untuk menghandle Block dan cara2 memodifikasinya
public class Block {
    public char id;
    public char[][] shape;
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
                if (shape[i][j] != ' ') effCells++;
                
            }
        }
        return effCells;
    }


    public static char getLetter(char[][] shape){
        for (int i = 0; i < shape.length; i++){
            for (int j = 0; j < shape[0].length; j++){
                if(shape[i][j] != ' '){return shape[i][j];}
            }
        }
        return 'A';  // asal aja cuy
    }

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
        int row = this.rows;
        int col = this.cols;

        // Handle perubahan dimensi jika rotasi 90/270 derajat
        int newRows = (rotation % 2 == 0) ? row : col;
        int newCols = (rotation % 2 == 0) ? col : row;
        char[][] rotatedShape = new char[newRows][newCols];
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

    // yang ini harus statik ternyata :( jadi aneh 
    public static char[][] mirrorBlock(char[][] prevShape, int mirrorMode){
            int rows = prevShape.length;
            int cols = prevShape[0].length;
            char[][] mirrored = new char[rows][cols];
            switch (mirrorMode) {
                default: // No mirror
                    return prevShape;
                case 1: // Vertical
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            mirrored[i][cols - 1 - j] = prevShape[i][j];}}
                    break;
                case 2: // Horizontal
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            mirrored[rows - 1 - i][j] = prevShape[i][j];}}
                    break;
            }
            return mirrored;
    }
}