package edu.up.cs301.pig;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    turnTotalTextView   = null;
    private TextView    messageTextView     = null;
    private ImageButton dieImageButton      = null;
    private Button      holdButton          = null;
    private TextView    oppLabel            = null;
    private TextView    playerLabel         = null;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public PigHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        Log.i("Checkpoint 2","receiveInfo(GameInfo) method called.");
        // flash if invalid
        if(!(info instanceof PigGameState)){
            flash(0xFFFF0000, 250);
        }else{
            // cast to PigGameState
            PigGameState pigInfo = (PigGameState) info;
            int thisPlayer = this.playerNum;

            // cleaning up GUI
            if(pigInfo.getId() == 0) dieImageButton.setBackgroundColor(0xFF52BFE2);
            if(pigInfo.getId() == 1) dieImageButton.setBackgroundColor(0xFFF25E59);

            // set point values
            if(thisPlayer == 0) {
                playerScoreTextView.setText("" + pigInfo.getPlayer0Score());
                oppScoreTextView.setText("" + pigInfo.getPlayer1Score());
            }else{
                playerScoreTextView.setText("" + pigInfo.getPlayer1Score());
                oppScoreTextView.setText("" + pigInfo.getPlayer0Score());
            }

            // update image on dice
            switch(pigInfo.getDiceRoll()){
                case 1: dieImageButton.setImageResource(R.drawable.face1); break;
                case 2: dieImageButton.setImageResource(R.drawable.face2); break;
                case 3: dieImageButton.setImageResource(R.drawable.face3); break;
                case 4: dieImageButton.setImageResource(R.drawable.face4); break;
                case 5: dieImageButton.setImageResource(R.drawable.face5); break;
                case 6: dieImageButton.setImageResource(R.drawable.face6); break;
            }

            // update the turn total counter
            turnTotalTextView.setText("" + pigInfo.getRunningTotal());

            // update the message TextView
            messageTextView.setText(pigInfo.getMessage());
        }

    }//receiveInfo

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        if(button instanceof ImageButton){
            PigRollAction roll = new PigRollAction(this);
            game.sendAction(roll);
        }else if(button instanceof Button){
            PigHoldAction hold = new PigHoldAction(this);
            game.sendAction(hold);
        }
    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);
        this.turnTotalTextView   = (TextView)activity.findViewById(R.id.turnTotalValue);
        this.messageTextView     = (TextView)activity.findViewById(R.id.messageTextView);
        this.dieImageButton      = (ImageButton)activity.findViewById(R.id.dieButton);
        this.holdButton          = (Button)activity.findViewById(R.id.holdButton);
        this.playerLabel         = (TextView)activity.findViewById(R.id.yourScoreText);
        this.oppLabel            = (TextView)activity.findViewById(R.id.oppScoreText);

        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);

    }//setAsGui

    @Override
    public void initAfterReady(){
        playerLabel.setText(this.name);
        oppLabel.setText(this.allPlayerNames[1]);
    }

}// class PigHumanPlayer
