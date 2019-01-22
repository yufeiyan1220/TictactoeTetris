package baseboard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 1: create a window object
        JFrame frame = new JFrame("TicTacTetris");

        // create the board of game
        gameground panel = new gameground();
        // put the board into window
        frame.add(panel);

        // 2: set visible
        frame.setVisible(true);
        // 3: set the size of window 1k * 950 pixels
        frame.setSize(1000, 950);
        // 4: centered
        frame.setLocationRelativeTo(null);
        // 5: close the window when click 'x'
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.listenKeyboard();
        panel.start();
        /*
        thread player_o = new thread(panel, 0);
        thread player_x = new thread(panel, 1);
        Thread t1 = new Thread(player_o);
        Thread t2 = new Thread(player_x);
        t1.start();
        t2.start();
        */
    }
}
