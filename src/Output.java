import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Output {
    public static final DecimalFormat fm = new DecimalFormat("#,###");
    static {
        DecimalFormatSymbols symbols = fm.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        fm.setDecimalFormatSymbols(symbols);
    }

    // Urutan warna sesuai alphabet
    public static final int[] colourCodes = {
        196, 34, 220, 21, 201, 51, 208, 46, 202, 27, 200, 129, 118, 
        172, 15, 87, 214, 160, 82, 226, 33, 219, 141, 88, 230, 244
    };

    public static final int[][] ansiToRGB = {
        {255, 0, 0}, {0,175,0}, {255,215,0}, {0,0,255}, {255,0,255}, {0,255,255}, {255,135,0}, {0,255,0},
        {255,95,0}, {0,95,255}, {255,0,215}, {175,0,255}, {135,255,0}, {215,135,0}, {255,255,255}, {95,255,255},
        {255,175,0}, {215,0,0}, {95,255,0}, {255,255,0}, {0,135,255}, {255,175,255}, {175,135,255}, {135,0,0}, 
        {255,255,215}, {128,128,128} 
    };

    public static void printBlock(char[][] block){
        for (int i = 0; i < block.length; i++){
            for (int j = 0; j < block[0].length; j++){
                char currLetter = block[i][j];
                if (currLetter == '.') {
                    System.out.print(" ");
                    continue;
                }
                int colourCode = colourCodes[currLetter - 65];
                Utils.printColorChar(currLetter, colourCode, false);
            }
            System.out.println();
        }
    }

    public static void handleFileOutput(Board boardResult){
        char[][] board = boardResult.board;
        Scanner scanner = new Scanner(System.in);
        String filepath = "IO/results/";
        while (true) { 
            Utils.printColorStr("Enter output filename (.txt): ", 190, false);
            filepath = filepath + scanner.nextLine();
            if (filepath.equals("IO/results/")){
                Utils.clearScreen();
                Utils.printColorStr("Filename can't be empty", 9, true);
                Utils.enterToContinue();
                Utils.clearScreen();
            } else {break;}
        }
        System.out.print("\nWriting to " + filepath + "...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (int i = 0; i < board.length; i++){
                for (int j = 0; j < board[0].length; j++){
                    char currLetter = board[i][j];
                    if (currLetter == '.') {
                        writer.write(" ");
                        continue;
                    }
                    writer.write(currLetter);
                }
                writer.newLine();
                
            }
            writer.newLine();
            writer.write("Execution time: " + fm.format(boardResult.executeTime));
            writer.newLine();
            writer.write("Cases evaluated: " + fm.format(boardResult.casesCount));

            Utils.clearScreen();
            System.out.println("Successfully written to " + filepath);
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }

    public static void handleImageOutput(Board board){

        Scanner scanner = new Scanner(System.in);
        String filepath = "IO/results/";
        while (true) { 
            Utils.printColorStr("Enter image filename (.png): ", 190, false);
            filepath = filepath + scanner.nextLine();
            if (filepath.equals("IO/results/") || !filepath.endsWith(".png")){
                Utils.clearScreen();
                Utils.printColorStr("Filename can't be empty, and must be of type png!", 9, true);
                Utils.enterToContinue();
                Utils.clearScreen();
            } else {break;}
        }
        
        try {
            System.out.print("\nWriting to " + filepath + "...");
            BufferedImage image = createImage(board);
            ImageIO.write(image, "png", new File(filepath));
            Utils.clearScreen();
            System.out.println("Image written to " + filepath);
        } catch (IOException e) {
            Utils.printColorStr("Error creating image!",9,true);
        }
    }

    public static BufferedImage createImage(Board board) {
        char[][] bord = board.board;
        int rows = board.rows;
        int cols = board.cols;

        int cellWidth = 100;
        int cellHeight = 100;
        int width = cols * cellWidth;
        int height = rows * cellHeight;
        
        
        BufferedImage image = new BufferedImage(width, height + 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("Arial", Font.BOLD, 38);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        g2d.setFont(font);
        
        // Background
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, width, height + 400);

        // Setiap cell pada board digambarkan sebagai lingkaran berwarna dengan huruf2 blok
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char currLetter = bord[i][j];
                if (currLetter == '.') {
                    continue;
                }

                int colorIndex = currLetter - 'A'; 
                // Ini warna lingkaran
                g2d.setColor(new Color(
                    ansiToRGB[colorIndex][0],
                    ansiToRGB[colorIndex][1],
                    ansiToRGB[colorIndex][2]
                    ));

                int circleDiameter = 80;
                int xCircle = j * cellWidth + (cellWidth - circleDiameter) / 2;
                int yCircle = i * cellHeight + (cellHeight - circleDiameter) / 2;
                g2d.fillOval(xCircle, yCircle, circleDiameter, circleDiameter);

                g2d.setColor(Color.WHITE); // warna huruf
                String text = String.valueOf(currLetter);
                int textWidth = fontMetrics.stringWidth(text);
                int textHeight = fontMetrics.getAscent(); 
                // Gambar huruf
                int xText = xCircle + (circleDiameter - textWidth) / 2 - 6;
                int yText = yCircle + (circleDiameter + textHeight) / 2 + 8;
                g2d.drawString(text, xText, yText);
            }
        }
        Font result = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(result);
        g2d.drawString("Execute time: " + fm.format(board.executeTime) + " ms", 20, height + 30);
        g2d.drawString("Cases evaluated: " + fm.format(board.casesCount), 20, height + 70);

        g2d.dispose();
        return image;
    }    
}

