package baseboard;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class boardSingle extends JPanel{
    private int width = 8;
    private int height = 16;

    private Pill currentOne = Pill.randomOne();
    /** current dropping pill */
    private Pill nextOne = Pill.randomOne();
    /** property: board(8 * 16) */
    private Cell[][] wall = new Cell[height][width];
    private int player_camp = 0; //0 -> O; 1 -> X;
    /** 3 state Playing Pause and Gameover*/
    public static final int PLAYING = 0;
    public static final int PAUSE = 1;
    public static final int GAMEOVER = 2;

    String[] showState = { "P[pause]", "C[continue]", "Enter[replay]" };

    private static final int CELL_SIZE = 26;

    public void dropping(){
        currentOne.softDrop();
    }

    public boolean canDrop() {
        Cell[] cur_cells = currentOne.cells;
        for(Cell c : cur_cells) {
            if(c.getRow() == height - 1) return false;
            else if(wall[c.getRow() + 1][c.getCol()] != null) return false;
        }
        return true;
    }
    /** when can't drop. save in wall[][]*/
    public void landToWall() {
        Cell[] cur_cells = currentOne.cells;
        for (Cell c : cur_cells) {
            // get the final col and row of each cell of current pill
            int row = c.getRow();
            int col = c.getCol();
            wall[row][col] = c;
        }
    }
    /*
    public void checkAndDestroyPill() {
        Cell[] cur_cells = currentOne.cells;
        for(Cell c : cur_cells) {
            Find(c.getRow(), c.getCol());
        }
    }
    */
    /*
    private int Find(int row, int col) {
        int up_count = 0;
        int down_count = 0;
        int left_count = 0;
        int right_count = 0;
        int type
        //up
        for(int i = 0; i < 3; i++) {
            if(wall[row - i][col])
        }
    }
    */



    /**
    public static BufferedImage O;
    public static BufferedImage X;

    public static BufferedImage background;
    public static BufferedImage game_over;
    static {
        try {

            O = ImageIO.read(boardSingle.class.getResource("O.png"));
            X = ImageIO.read(boardSingle.class.getResource("X.png"));

            background = ImageIO.read(boardSingle.class.getResource("TicTacToeTetris.png"));
            game_over = ImageIO.read(boardSingle.class.getResource("game-over.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /*
    public boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            Image bi = ImageIO.read(in);
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "newX.png", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    */
}

