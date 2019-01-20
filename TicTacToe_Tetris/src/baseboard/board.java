package baseboard;

public class board {
    private int width = 8;
    private int height = 16;
   
    private Pill currentPill = Pill.randomOne();
    private Pill nextPill = Pill.randomOne();


	public static final int PLAYING = 0;
	public static final int PAUSE = 1;
	public static final int GAMEOVER = 2;
	private int game_state;
	public int player_camp;
	public int scores_get;
	public int scores_lose;
	public Cell[][] wall = new Cell[height][width];
    
	public board(int player_camp) {
        this.player_camp = player_camp;
		this.scores_get = 0;
		this.scores_lose = 0;
    }

    //define actions up/down/left/right
    public void droppingDownAction(){
		if(canDrop()) currentPill.softDrop();
    }

    protected void moveLeftAction() {
        currentPill.moveLeft();
        if (outOfBounds() || coincide()) {
            currentPill.moveRight();
        }
    }

    protected void moveRightAction() {
        currentPill.moveRight();
        if (outOfBounds() || coincide()) {
            currentPill.moveLeft();
        }
    }

	public void rotateRightAction() {
		currentPill.rotateRight();
		if (outOfBounds() || coincide()) {
			currentPill.rotateLeft();
		}
	}
	/** can drop or not.*/
	public boolean canDrop() {
		Cell[] cells = currentPill.cells;
		for (Cell c : cells) {
			/*
			 * if one cell of current pill get to the bottom
			 * OR there is one under the cell
			 */
			int row = c.getRow();
			int col = c.getCol();

			if (row == height - 1) {
				return false;
			}
			if (wall[row + 1][col] != null) {
				return false;
			}
		}
		return true;
	}

	/** stop movement and land current pill one the wall*/
	public void landToWall() {
		Cell[] cells = currentPill.cells;
		for (Cell c : cells) {
			// get the final rows and cols number
			int row = c.getRow();
			int col = c.getCol();
			wall[row][col] = c;
		}
	}
	/** calculate the result after landing and calculate the scores */
	public void destroyConsecutive() {
		Cell[] cells = currentPill.cells;
		int row_max = Math.max(cells[0].getRow(), cells[1].getRow());
		int row_min = Math.min(cells[0].getRow(), cells[1].getRow());
		int col_max = Math.max(cells[0].getCol(), cells[1].getCol());
		int col_min = Math.min(cells[0].getCol(), cells[1].getCol());
		int[] row_consec = {0, 0};
		int[] col_consec = {0, 0};
		int move = 1;
		//min
		int type_min = wall[row_min][col_min].getType();
		int left_min = 1;
		int right_min = 1;
		int up_min = 1;
		int down_min = 1;
		//max
		int type_max = wall[row_max][col_max].getType();
		int left_max = 1;
		int right_max = 1;
		int up_max = 1;
		int down_max = 1;

		if(col_max == col_min) {
			//check min
			//up
			while(row_min - move > 0 && wall[row_min - move][col_min] != null && move <= 2) {
				if(wall[row_min - move][col_min].getType() != type_min) break;
				up_min++;
				move++;
			}
			move = 1;
			//left
			while(col_min - move > 0 && wall[row_min][col_min - move] != null && move <= 2) {
				if(wall[row_min][col_min - move].getType() != type_min) break;
				left_min++;
				move++;
			}
			move = 1;
			//right
			while(col_min + move > 0 && wall[row_min][col_min + move] != null && move <= 2) {
				if(wall[row_min][col_min + move].getType() != type_min) break;
				right_min++;
				move++;
			}
			//check max
			//down
			while(row_max + move < height && wall[row_max + move][col_max] != null && move <= 2) {
				if(wall[row_max + move][col_max].getType() != type_max) break;
				down_max++;
				move++;
			}
			move = 1;
			//left
			while(col_max - move > 0 && wall[row_max][col_max - move] != null && move <= 2) {
				if(wall[row_max][col_max - move].getType() != type_max) break;
				left_max++;
				move++;
			}
			move = 1;
			//right
			while(col_max + move < width && wall[row_max][col_max + move] != null && move <= 2) {
				if(wall[row_max][col_max + move].getType() != type_max) break;
				right_max++;
				move++;
			}
			//up and down
			if(type_max == type_min && up_min + down_max >= 3) {
				if(type_max == player_camp) {
					scores_get += up_min + down_max;
				}
				else {
					scores_lose += up_min + down_max;
				}
				for(int i = row_min - up_min + 1; i <= row_max + down_max - 1; i++) {
					wall[i][col_max] = null;
				}
			}
			else if(type_max != type_min){
				if(up_max + down_max - 1 >= 3) {
					if(type_max == player_camp) {
						scores_get += up_max + down_max - 1;
					}
					else {
						scores_lose += up_max + down_max - 1;
					}
					for(int i = row_max - up_max + 1; i <= row_max + down_max - 1; i++) {
						wall[i][col_max] = null;
					}
				}
				if(up_min + down_min - 1 >= 3) {
					if (type_min == player_camp) {
						scores_get += up_min + down_min - 1;
					} else {
						scores_lose += up_min + down_min - 1;
					}
					for (int i = row_min - up_min + 1; i <= row_min + down_min - 1; i++) {
						wall[i][col_min] = null;
					}
				}
			}
			//max l and r
			if(left_max + right_max - 1>= 3) {
				if(type_max == player_camp) {
					scores_get += left_max + right_max - 1;
				}
				else {
					scores_lose += left_max + right_max - 1;
				}
				for (int i = col_max - left_max + 1; i <= col_max + right_max - 1; i++) {
					wall[row_max][i] = null;
				}
			}

			if(left_min + right_min - 1>= 3) {
				if(type_min == player_camp) {
					scores_get += left_min + right_min - 1;
				}
				else {
					scores_lose += left_min + right_min - 1;
				}
				for (int i = col_min - left_min + 1; i <= col_min + right_min - 1; i++) {
					wall[row_min][i] = null;
				}
			}

		}
		else {
			//check min
			//left
			while(col_min - move > 0 && wall[row_min][col_min - move] != null && move <= 2) {
				if(wall[row_min][col_min - move].getType() != type_min) break;
				left_min++;
				move++;
			}
			move = 1;
			//up
			while(row_min - move > 0 && wall[row_min - move][col_min] != null && move <= 2) {
				if(wall[row_min - move][col_min].getType() != type_min) break;
				up_min++;
				move++;
			}
			move = 1;
			//down
			while(row_min + move < height && wall[row_min + move][col_min] != null && move <= 2) {
				if(wall[row_min + move][col_min].getType() != type_min) break;
				down_min++;
				move++;
			}
			//check max
			//right
			while(col_max + move < width && wall[row_max][col_max + move] != null && move <= 2) {
				if(wall[row_max][col_max + move].getType() != type_max) break;
				right_max++;
				move++;
			}
			move = 1;
			//up
			while(row_max - move > 0 && wall[row_max - move][col_max] != null && move <= 2) {
				if(wall[row_max - move][col_max].getType() != type_max) break;
				up_max++;
				move++;
			}
			move = 1;
			//down
			while(row_max + move < height && wall[row_max + move][col_max] != null && move <= 2) {
				if(wall[row_max + move][col_max].getType() != type_max) break;
				down_max++;
				move++;
			}

			//left and right
			if(type_max == type_min && left_min + right_max >= 3) {
				if(type_max == player_camp) {
					scores_get += left_min + right_max;
				}
				else {
					scores_lose += left_min + right_max;
				}
				for(int i = col_min - left_min + 1; i <= col_max + right_max - 1; i++) {
					wall[row_max][i] = null;
				}
			}
			else if(type_max != type_min){
				if(left_max + right_max - 1 >= 3) {
					if(type_max == player_camp) {
						scores_get += left_max + right_max - 1;
					}
					else {
						scores_lose += left_max + right_max - 1;
					}
					for(int i = col_max - left_max + 1; i <= col_max + right_max - 1; i++) {
						wall[row_max][i] = null;
					}
				}
				if(left_min + right_min - 1 >= 3) {
					if (type_min == player_camp) {
						scores_get += left_min + right_min - 1;
					} else {
						scores_lose += left_min + right_min - 1;
					}
					for (int i = col_min - left_min + 1; i <= col_min + right_min - 1; i++) {
						wall[row_min][i] = null;
					}
				}
			}
			//max up and down
			if(up_max + down_max - 1 >= 3) {
				if(type_max == player_camp) {
					scores_get += up_max + down_max - 1;
				}
				else {
					scores_lose += up_max + down_max - 1;
				}
				for (int i = row_max - up_max + 1; i <= row_max + down_max - 1; i++) {
					wall[i][col_max] = null;
				}
			}

			if(up_min + down_min - 1 >= 3) {
				if (type_min == player_camp) {
					scores_get += up_min + down_min - 1;
				} else {
					scores_lose += up_min + down_min - 1;
				}
				for (int i = row_min - up_min + 1; i <= row_max + down_min - 1; i++) {
					wall[i][col_min] = null;
				}
			}
		}
	}

	public void nextRound() {
		this.currentPill = this.nextPill;
		this.nextPill = Pill.randomOne();
	}

    public boolean outOfBounds() {
        Cell[] cells = currentPill.cells;
        for (Cell c : cells) {
            int col = c.getCol();
            int row = c.getRow();
            if (col < 0 || col >= width || row >= height || row < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean coincide() {
        Cell[] cells = currentPill.cells;
        for (Cell c : cells) {
            int row = c.getRow();
            int col = c.getCol();
            if (wall[row][col] != null) {
                return true;
            }
        }
        return false;
    }

	public boolean isGameOver() {
		Cell[] cells = nextPill.cells;
		for (Cell c : cells) {
			int row = c.getRow();
			int col = c.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++){
				if(wall[i][j] == null) sb.append("X");
				else sb.append(wall[i][j].getType());
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/** testing */
	/*
	public static void main(String[] args) {
		board bd = new board(0);
		int i = 0;
		while(i < 10) {
			while(bd.canDrop()) {
				bd.droppingDownAction();
			}
			System.out.println(bd.currentPill);
			bd.landToWall();
			bd.destroyConsecutive();
			bd.nextRound();
			i++;
		}

		System.out.println("score_get = " + bd.scores_get + " score_lose = " + bd.scores_lose);
		System.out.println(bd);

		while(i < 15) {
			bd.moveLeftAction();
			bd.moveLeftAction();
			while(bd.canDrop()) {
				bd.droppingDownAction();
			}
			System.out.println(bd.currentPill);
			bd.landToWall();
			bd.destroyConsecutive();
			bd.nextRound();
			i++;
		}
		System.out.println("score_get = " + bd.scores_get + " score_lose = " + bd.scores_lose);
		System.out.println(bd);

		while(!bd.isGameOver()) {
			bd.moveRightAction();
			bd.moveRightAction();
			bd.rotateRightAction();
			while(bd.canDrop()) {
				bd.droppingDownAction();
			}
			System.out.println(bd.currentPill);
			bd.landToWall();
			bd.destroyConsecutive();
			bd.nextRound();
			i++;
		}

		System.out.println("score_get = " + bd.scores_get + " score_lose = " + bd.scores_lose);
		System.out.println(bd);

	}
	*/
}

