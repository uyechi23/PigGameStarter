package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

import java.util.Random;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {

    // a GameState object for this LocalGame
    private PigGameState game;

    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        // initialize the GameState
        game = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == this.game.getId();
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        // if it's a hold action
        if(action instanceof PigHoldAction){
            // Log action
            Log.d("Player " + game.getId(), "HOLD");
            // if the ID of the current player is 0, add running total to player 0
            // same for player 1
            if(this.game.getId() == 0){
                this.game.setPlayer0Score(this.game.getPlayer0Score() + this.game.getRunningTotal());
                this.game.setMessage(this.playerNames[0] + " has added " + this.game.getRunningTotal() + " points to their score!");
            }else{
                this.game.setPlayer1Score(this.game.getPlayer1Score() + this.game.getRunningTotal());
                this.game.setMessage(this.playerNames[1] + " has added " + this.game.getRunningTotal() + " points to their score!");
            }
            // reset the running total
            this.game.setRunningTotal(0);
            // switch to next player if there's more than 1
            if(this.players.length > 1) { this.game.setId((this.game.getId() + 1) % 2); }
            // return true (valid move)
            return true;
        }else if(action instanceof PigRollAction){
            // Log action
            Log.d("Player " + game.getId(), "ROLL");
            // create a random object
            Random random = new Random();
            // reset the game's dice roll to 0
            game.setDiceRoll(0);
            // make a roll
            int diceRoll = (random.nextInt(6) + 1);
            // set the game state's dice roll value to the dice roll
            game.setDiceRoll(diceRoll);
            // if the dice roll is not 1, then add to running total
            // if the dice roll is 1, then reset the running total and end player's turn
            if(diceRoll != 1){
                this.game.setRunningTotal(this.game.getRunningTotal() + diceRoll);
                this.game.setMessage(this.playerNames[this.game.getId()] + " rolls successfully, adding " + diceRoll + " points to the running total!");
            }else{
                this.game.setMessage("Oh no! " + this.playerNames[this.game.getId()] + " has landed a 1! They are forced to pass without earning any points.");
                this.game.setRunningTotal(0);
                if(this.players.length > 1) { this.game.setId((this.game.getId() + 1) % 2); }
            }
            // return true (valid move)
            return true;
        }else{
            // return false (invalid move)
            return false;
        }
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // copy the game state
        PigGameState currState = new PigGameState(this.game);
        // send info to player
        p.sendInfo(currState);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(this.game.getPlayer1Score() >= 50){
            return "Winner: " + this.playerNames[1] + " with a score of " + this.game.getPlayer1Score();
        }
        if(this.game.getPlayer0Score() >= 50){
            return "Winner: " + this.playerNames[0] + " with a score of " + this.game.getPlayer0Score();
        }
        return null;
    }

}// class PigLocalGame
