This project encapsulate the basic logic of TicTacToeTetris Game.

The API of this is:

1. class Cell: The smallest block of this game.

property:
	row : index of row (y-coordinate location)
	col : index of coloum (x-coordinate location)
	image : the image of cell
	type (0 stand for 'O' and 1 stand for 'X')
method:
	set/get row/col/type/image() :  get or set the property
	left() : col--
	right() : col++
	drop() : row++

2. class Pill: the basic element of our game, contains 2 Cells.

Property:
	Cell[] cells: the array with size 2, the two Cell of this Pill
	
Method:
	moveLeft() : the Pill move left by 1
	moveRight() : the Pill move right by 1
	softDrop() : the Pill drop down by 1
	rotateRight() : rotate clockwise
	rotateLeft() : rotate reverse clockwise
	(static)randomOne() : randomly generate one pill with 2 random-type Cell
	
3. class Board: the Main class of this game, contains all the functions related all rules of the game.

Property:
	width = 8
    height = 16
    currentPill : the current dropping pill
    nextPill : next ramdom Pill
	Cell[][] wall : the 16*8 sized board of this game
    player_camp : the player's camp, 'O' or 'X'
	scores_get : the score get from destroying the cells with same type with player_camp
	scores_lose : the score get for opponent from destroying the cells with different type with player_camp
	game_state : game over, pause or playing.(not used yet)
	
Method:
	droppingDownAction() : drop down the current by 1 if it could drop
	moveLeftAction() : move left the current pill by 1 if it could be moved
	moveRightAction() : move right the current pill by 1 if it could be moved
	rotateRightAction() : rotate the current Pill if it could rotate clockwise
	landToWall() : after the currentPill could not move, set it to wall
	destroyConsecutive() : destroy consecutive > 3 cells with same types and calculate the scores.
	nextRound() : after the fininshing all the calculation in this round, make the next random Pill dropping 