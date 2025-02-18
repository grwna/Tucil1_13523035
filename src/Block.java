import java.util.ArrayList;
import java.util.Arrays;

public class Block {
    public  char id;
    // public ArrayList<char[]> shape; ini yang benar, bawah untuk debug aja
    public ArrayList<String> shape;
    public char[][] shape; //ini yang benar, bawah untuk debug aja
    public int rows;
    public int cols;

    public Block(char id, ArrayList<String> shape){
        this.id = id;
        this.shape = shape;
        this.shape = filterEmptyColumns(padAndFormatShape(shape));
        this.rows = this.shape.length;
        this.cols = this.shape[0].length;
    }
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


