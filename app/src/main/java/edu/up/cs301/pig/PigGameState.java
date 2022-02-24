package edu.up.cs301.pig;

import edu.up.cs301.game.infoMsg.GameState;

public class PigGameState extends GameState {

    // instance variables
    private int id; // id (0 or 1) of the current player
    private int player0Score; // player 0's score
    private int player1Score; // player 1's score
    private int runningTotal; // running total before HOLD is selected
    private int diceRoll; // the result of the most recent dice roll

    public PigGameState(){
        // set everything to 0
        this.id = 0; // first player - player 0
        this.player0Score = 0; // initial score 0
        this.player1Score = 0; // initial score 0
        this.runningTotal = 0; // running total should be 0
        this.diceRoll = 0; // the dice roll should not have a value yet
    }

    public PigGameState(PigGameState orig){
        // use getter methods to retrieve values from the original GameState
        this.id = orig.getId();
        this.player0Score = orig.getPlayer0Score();
        this.player1Score = orig.getPlayer1Score();
        this.runningTotal = orig.getRunningTotal();
        this.diceRoll = orig.getDiceRoll();
    }

    // getter methods
    public int getId() { return this.id; }
    public int getPlayer0Score() { return this.player0Score; }
    public int getPlayer1Score() { return this.player1Score; }
    public int getRunningTotal() { return this.runningTotal; }
    public int getDiceRoll() { return this.diceRoll; }

    // setter methods
    public void setId(int id) { this.id = id; }
    public void setPlayer0Score(int score) { this.player0Score = score; }
    public void setPlayer1Score(int score) { this.player1Score = score; }
    public void setRunningTotal(int total) { this.runningTotal = total; }
    public void setDiceRoll(int diceVal) { this.diceRoll = diceVal; }
}
