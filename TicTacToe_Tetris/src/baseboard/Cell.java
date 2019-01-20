package baseboard;

import java.awt.image.BufferedImage;

/**
 * Cell -- The minimum element in this game
 * property：
 *  row
 *  col
 *  image
 *  type O/X
 * method:
 *   left()
 *   right()
 *   drop();
 */
public class Cell {
    private int row;
    private int col;
    //type 0/1 = O/X
    private int type;
    private BufferedImage image;

    public Cell(int row, int col, int type, BufferedImage image) {
        super();
        this.row = row;
        this.col = col;
        this.type = type;
        this.image = image;
    }

    public Cell(int row, int col, int type) {
        super();
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + "," + type + ")";
    }

    /**
     * moves left/right/drop
     */
    public void left() {
        col--;
    }

    public void right() {
        col++;
    }

    public void drop() {
        row++;
    }
    /*
    public static void main(String[] args) {
        Cell c = new Cell(0, 0, 1);
        c.left();
        c.drop();
        c.drop();
        c.right();
        c.right();
        System.out.println(c);
    }
    */
}
