import java.util.ArrayList;

public class Block {
    public  char id;
    // public ArrayList<char[]> shape; ini yang benar, bawah untuk debug aja
    public ArrayList<String> shape;

    public Block(char id, ArrayList<String> shape){
        this.id = id;
        this.shape = shape;
    }
}
