package baseboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class gameground extends JPanel{
    /** 3 states */
    public static final int PLAYING = 0;
    public static final int PAUSE = 1;
    public static final int GAMEOVER = 2;
    /** The state display on the window */
    String[] showState = { "P[pause]", "C[continue]", "Enter[replay]" };
    private static final int offset = 150;
    private static final int offset_x = 600;
    private static final int offset_o = 0;
    private static final int CELL_SIZE = 46;
    protected int game_state;
    protected int score_o = 0;
    protected int score_x = 0;

    public board playerO;
    public board playerX;

    public static BufferedImage O;
    public static BufferedImage X;
    public static BufferedImage background;
    public static BufferedImage game_over;
    static {
        try {
            /*
             * getResource(String url) url: path of the img.png
             */
            O = ImageIO.read(gameground.class.getResource("O.png"));
            X = ImageIO.read(gameground.class.getResource("X.png"));
            background = ImageIO.read(gameground.class.getResource("background.png"));
            //backgroundX = ImageIO.read(gameground.class.getResource("background.png"));
            game_over = ImageIO.read(gameground.class.getResource("gameover.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public gameground() {
        playerO = new board(0);
        playerX = new board(1);
    }


    public void paint(Graphics g) {
        // paint background
        /*
         * g：graph class g.drawImage(image,x,y,null) image: picture we draw x: start x, y:start y
         */
        g.drawImage(background, 0, 0, null);
        // move the coordinator
        g.translate(15, 15);
        // draw the wall
        paintWall(g, 0);
        paintWall(g, 1);
        // draw the current Pill
        paintCurrentOne(g, 0);
        paintCurrentOne(g, 1);
        // draw the next Pill
        paintNextOne(g, 0, 25, 0);
        paintNextOne(g, 600, 25,1);
        //draw score and state
        paintScore(g);
        paintState(g);
    }

    private void paintState(Graphics g) {
        if (game_state == GAMEOVER) {
            g.drawImage(game_over, 0, 0, null);
            g.drawString(showState[GAMEOVER], 415, 600);
        }
        if (game_state == PLAYING) {
            g.drawString(showState[PLAYING], 415, 600);
        }
        if (game_state == PAUSE) {
            g.drawString(showState[PAUSE], 415, 600);
        }

    }

    public void paintScore(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        g.drawString("O:" + score_o, 415, 500);
        g.drawString("X:" + score_x, 415, 550);
    }
    public void paintNextOne(Graphics g, int offset_x, int offset_y, int type) {
        Cell[] cells = null;
        if(type == 0) cells = playerO.nextPill.cells;
        else cells = playerX.nextPill.cells;
        for (Cell c : cells) {
            // get row and col
            int row = c.getRow();
            int col = c.getCol();
            // calculate (x, y) in our window
            int x = col * CELL_SIZE + offset_x;
            int y = row * CELL_SIZE + offset_y;
            g.drawImage(c.getImage(), x, y, null);
        }
    }

    /**
     * Drawing the dropping(current) pill
     *
     */
    public void paintCurrentOne(Graphics g, int type) {
        int offset_game = 0;
        Cell[] cells = null;
        if(type == 1) {
            offset_game = offset_x;
            cells = playerX.currentPill.cells;
        }
        else {
            offset_game = offset_o;
            cells = playerO.currentPill.cells;
        }

        for (Cell c : cells) {
            int x = c.getCol() * CELL_SIZE + offset_game;
            int y = c.getRow() * CELL_SIZE + offset;
            g.drawImage(c.getImage(), x, y, null);
        }
    }

    /**
     * 16 * 8 Squares
     *
     * @param a
     */
    public void paintWall(Graphics a, int type) {
        int offset_game = 0;
        Cell cell = null;
        if(type == 1) {
            offset_game = offset_x;
        }
        else offset_game = offset_o;
        // rows
        for (int i = 0; i < 16; i++) {
            // column
            for (int j = 0; j < 8; j++) {
                int x = j * CELL_SIZE + offset_game;
                int y = i * CELL_SIZE + offset;
                if(type == 0)
                    cell = playerO.wall[i][j];
                else cell = playerX.wall[i][j];
                if (cell == null) {
                    a.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                } else {
                    a.drawImage(cell.getImage(), x, y, null);
                }
            }
        }
    }

    /**
     * encapsulate the basic logic of game
     */
    public void listenKeyboard() {
        game_state = PLAYING;
        // Listen to the keyboard on
        KeyListener l = new KeyAdapter() {
            /*
             * KeyPressed() 是键盘按钮 按下去所调用的方法
             */
            public void keyPressed(KeyEvent e) {
                // 获取一下键子的代号
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_P) {
                    if (game_state == PLAYING) {
                        game_state = PAUSE;
                    }

                }
                if (code == KeyEvent.VK_C) {
                    if (game_state == PAUSE) {
                        game_state = PLAYING;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    game_state = PLAYING;
                    playerO.wall = new Cell[20][10];
                    playerO.currentPill = Pill.randomOne();
                    playerO.nextPill = Pill.randomOne();
                    playerO.scores_lose = 0;
                    playerO.scores_get = 0;
                    playerX.wall = new Cell[20][10];
                    playerX.currentPill = Pill.randomOne();
                    playerX.nextPill = Pill.randomOne();
                    playerX.scores_lose = 0;
                    playerX.scores_get = 0;
                }

                switch (code) {
                    case KeyEvent.VK_DOWN:
                        playerX.droppingDownAction();
                        break;
                    case KeyEvent.VK_LEFT:
                        playerX.moveLeftAction();
                        break;
                    case KeyEvent.VK_RIGHT:
                        playerX.moveRightAction();
                        break;
                    case KeyEvent.VK_UP:
                        playerX.rotateRightAction();
                        break;
                    case KeyEvent.VK_S:
                        playerO.droppingDownAction();
                        break;
                    case KeyEvent.VK_A:
                        playerO.moveLeftAction();
                        break;
                    case KeyEvent.VK_D:
                        playerO.moveRightAction();
                        break;
                    case KeyEvent.VK_W:
                        playerO.rotateRightAction();
                        break;
                }
                repaint();
            }
        };
        // Add listener
        this.addKeyListener(l);
        // setting as focus
        this.requestFocus();
    }

    public synchronized void startO() {
        while (true) {

            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (game_state == PLAYING) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (playerO.canDrop()) {
                    playerO.currentPill.softDrop();
                } else {
                    playerO.landToWall();
                    playerO.destroyConsecutive();
                    this.score_o += playerO.scores_get;
                    this.score_x += playerO.scores_lose;

                    if (!playerO.isGameOver()) {
                        playerO.nextRound();
                    } else {
                        game_state = GAMEOVER;
                    }
                }
                repaint();

                /*
                 * 下落之后，要重新进行绘制，才会看到下落后的 位置 repaint方法 也是JPanel类中提供的 此方法中调用了paint方法
                 */
            }
            Thread.currentThread().yield();
        }
    }
    public synchronized void startX() {
        while (true) {
            if (game_state == PLAYING) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (playerX.canDrop()) {
                    playerX.currentPill.softDrop();
                } else {
                    playerX.landToWall();
                    playerX.destroyConsecutive();
                    this.score_x += playerX.scores_get;
                    this.score_o += playerX.scores_lose;
                    if (!playerX.isGameOver()) {
                        playerX.nextRound();
                    } else {
                        game_state = GAMEOVER;
                    }
                }
                repaint();
            }
        }
    }
    public void start() {
        while (true) {
            if (game_state == PLAYING) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (playerO.canDrop()) {
                    playerO.currentPill.softDrop();
                } else {
                    playerO.landToWall();
                    playerO.destroyConsecutive();
                    this.score_o += playerO.scores_get;
                    this.score_x += playerO.scores_lose;

                    if (!playerO.isGameOver()) {
                        playerO.nextRound();
                    } else {
                        game_state = GAMEOVER;
                    }
                }
                repaint();
                if (playerX.canDrop()) {
                    playerX.currentPill.softDrop();
                } else {
                    playerX.landToWall();
                    playerX.destroyConsecutive();
                    this.score_x += playerX.scores_get;
                    this.score_o += playerX.scores_lose;

                    if (!playerX.isGameOver()) {
                        playerX.nextRound();
                    } else {
                        game_state = GAMEOVER;
                    }
                }
                repaint();
            }
        }
    }
}





