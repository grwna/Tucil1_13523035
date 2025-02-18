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
