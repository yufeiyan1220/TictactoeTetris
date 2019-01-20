package baseboard;

import java.util.Arrays;
import java.util.Random;
/**
 * Pill -- the basic element of each round of game.
 *
 * Property:
 * Cell[]
 * State[]
 * count
 * class State{}
 * Method:
 * moveLeft()
 * moveRight()
 * softDrop()
 * rotateRight()
 * rotateLeft()
 * randomOne()
 * */
public class Pill {
    protected Cell[] cells;
    /**
     * states of pills and the count of rotation state
     */
    protected State[] states;
    private int count = 0;

    public Pill() {
        this.cells = new Cell[2];
        this.states = new State[4];
        states[0] = new State(0, 0, 0, 1);
        states[1] = new State(0, 0, 1, 0);
        states[2] = new State(0, 0, 0, -1);
        states[3] = new State(0, 0, -1, 0);
    }

    /**
     * move the pill left
     */
    public void moveLeft() {
        for (Cell c : cells) {
            c.left();
        }
    }

    /**
     * move the pill left
     */
    public void moveRight() {
        for (Cell c : cells) {
            c.right();
        }
    }

    /**
     * move the pill down
     */
    public void softDrop() {
        for (Cell c : cells) {
            c.drop();
        }
    }
    /**
     * rotate Clockwise
     * */
    public void rotateRight() {
        count++;
        count = count % 4;
        State s = states[count];
        Cell c0 = cells[0];
        cells[1].setCol(c0.getCol() + s.getCol1());
        cells[1].setRow(c0.getRow() + s.getRow1());
    }
    /**
     * rotate reverse Clockwise
     * */
    public void rotateLeft() {
        count--;
        if(count < 0) count += 4;
        State s = states[count];
        Cell c0 = cells[0];
        cells[1].setCol(c0.getCol() + s.getCol1());
        cells[1].setRow(c0.getRow() + s.getRow1());
    }
    @Override
    public String toString() {
        return "[" + Arrays.toString(cells) + "]";
    }


    /**
     * need to redo!
     */
    public static Pill randomOne() {
        Pill pill = new Pill();
        Random ran = new Random();
        // generate random integer within [0,1]
        int c1_type = ran.nextInt(2);
        int c2_type = ran.nextInt(2);
        //System.out.println(c1_type + "," + c2_type);
        pill.cells[0] = new Cell(0, 3, c1_type);
        pill.cells[1] = new Cell(0, 4, c2_type);

        return pill;
    }


    public class State {
        /**
         * 2 pills 4 state parameters
         */
        int row0, col0, row1, col1;

        public State() {
        }

        public State(int row0, int col0, int row1, int col1) {
            super();
            this.row0 = row0;
            this.col0 = col0;
            this.row1 = row1;
            this.col1 = col1;

        }

        public int getRow0() {
            return row0;
        }

        public void setRow0(int row0) {
            this.row0 = row0;
        }

        public int getCol0() {
            return col0;
        }

        public void setCol0(int col0) {
            this.col0 = col0;
        }

        public int getRow1() {
            return row1;
        }

        public void setRow1(int row1) {
            this.row1 = row1;
        }

        public int getCol1() {
            return col1;
        }

        public void setCol1(int col1) {
            this.col1 = col1;
        }

        @Override
        public String toString() {
            return "State [row0=" + row0 + ", col0=" + col0 + ", row1=" + row1 + ", col1=" + col1 + "]";
        }
    }

    /*
    public static void main(String[] args) {
        Pill p = Pill.randomOne();
        System.out.println(p);
        p.rotateLeft();
        System.out.println(p);
        p.rotateLeft();
        System.out.println(p);
        p.rotateLeft();
        System.out.println(p);
        p.rotateLeft();
        System.out.println(p);
        p.rotateRight();
        System.out.println(p);
        p.rotateRight();
        System.out.println(p);
    }
    */
}
