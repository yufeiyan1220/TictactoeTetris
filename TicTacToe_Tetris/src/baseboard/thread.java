package baseboard;

public class thread implements Runnable {
    public gameground game;
    public int type;
    public thread(gameground game, int type) {
        this.type = type;
        this.game = game;
    }
    @Override
    public void run(){
        if(this.type == 0) game.startO();
        else game.startX();
    }

}
