package edu.up.cs301.pig;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

public class PigSmartComputerPlayer extends GameComputerPlayer {

    /**
     * ctor does nothing extra
     */
    public PigSmartComputerPlayer(String name) { super(name); }

    /**
     * callback method--game's state has changed
     *
     * @param info
     *      the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // sleep value - ms the computer should sleep for
        int sleepFor = 500;

        // threshold - how many points should the smart computer stop at?
        int threshold = 15;

        // typecast info into a PigGameState object
        PigGameState received = new PigGameState((PigGameState) info);

        // if the ID of the gameState isn't equal to this player's ID, skip
        if(received.getId() != this.playerNum) return;

        /*
        External Citation:
            Date:       07 March 2022
            Problem:    Wanted to figure out best probability for deciding to roll or hold
            Resource:   https://math.stackexchange.com/questions/2469953/optimal-strategy-for-rolling-die-consecutively-without-getting-a-1
            Solution:   Used the probability calculations and rationale in this thread
         */
        if(((PigGameState) info).getRunningTotal() < threshold){
            // if the current running total is less than 20, roll
            PigRollAction roll = new PigRollAction(this);
            this.sleep(sleepFor);
            this.game.sendAction(roll);
        }else if(((PigGameState) info).getRunningTotal() > threshold){
            // if the current running total is greater than 20, stop
            PigHoldAction hold = new PigHoldAction(this);
            this.sleep(sleepFor);
            this.game.sendAction(hold);
        }else{
            // if the current running total is exactly the threshold, favor rolling but choose randomly (70-30)
            Random rand = new Random();
            int decision = rand.nextInt(10);
            if(decision < 3){
                PigHoldAction hold = new PigHoldAction(this);
                this.sleep(sleepFor);
                this.game.sendAction(hold);
            }else{
                PigRollAction roll = new PigRollAction(this);
                this.sleep(sleepFor);
                this.game.sendAction(roll);
            }
        }
    }
}
