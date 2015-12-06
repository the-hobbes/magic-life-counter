package com.example.android.magicgamecounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    // Constants
    public static final int FIVE = 5;
    public static final int ONE = 1;
    public static final String PLAYER1 = "player_1_total";
    public static final String PLAYER2 = "player_2_total";
    public static final String RED = "#F44336";
    public static final String YELLOW = "#FB8C00";
    public static final String BLACK = "#000000";

    // Instance variables
    int player_1_total = 20;
    int player_2_total = 20;

    // State variables
    public static final String STATE_NAME = "saved_state";
    public SharedPreferences savedState;

    /**
     * Fires when app starts up.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedState = getSharedPreferences(STATE_NAME, Context.MODE_PRIVATE);
        if (savedState.getInt(PLAYER1, -1) == -1 || savedState.getInt(PLAYER2, -1) == -1) {
            SharedPreferences.Editor editor = getSharedPreferences(STATE_NAME, MODE_PRIVATE).edit();
            editor.putInt(PLAYER1, player_1_total);
            editor.putInt(PLAYER2, player_2_total);
            editor.commit();
        }
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false; // don't show the menu, in this case the 3-dot icon
    }

    /**
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void lifeChangeHandler(View view) {
        switch (view.getId()) {
            case R.id.decrementButtonPlayer1ByFive:
                decrementLifeTotal(PLAYER1, FIVE);
                break;
            case R.id.decrementButtonPlayer1ByOne:
                decrementLifeTotal(PLAYER1, ONE);
                break;
            case R.id.incrementButtonPlayer1ByOne:
                incrementLifeTotal(PLAYER1, ONE);
                break;
            case R.id.decrementButtonPlayer2ByFive:
                decrementLifeTotal(PLAYER2, FIVE);
                break;
            case R.id.decrementButtonPlayer2ByOne:
                decrementLifeTotal(PLAYER2, ONE);
                break;
            case R.id.incrementButtonPlayer2ByOne:
                incrementLifeTotal(PLAYER2, ONE);
                break;
        }
    }

    public void player1EditTextHandler(View view) {
        // TODO(pheven): make this method generic for both players
        int textViewID = view.getId();
        // TODO(pheven): make the player_1_edit_text string a constant
        int editViewID = getResources().getIdentifier("player_1_edit_text", "id", getPackageName());
        findViewById(textViewID).setVisibility(View.GONE);
        findViewById(editViewID).setVisibility(View.VISIBLE);

        // TODO(pheven): handle user pressing enter or clicking done checkbox in keyboard.
        // http://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
    }



    /**
     * Reset the life totals of all players and update the view.
     * @param view
     */
    public void resetHandler(View view) {
        player_1_total = 20;
        player_2_total = 20;
        displayForPlayer(PLAYER1, player_1_total);
        displayForPlayer(PLAYER2, player_2_total);
    }

    /**
     * Increases the life total of a given player by a given amount.
     * @param player
     * @param increment
     */
    private void incrementLifeTotal(String player, int increment) {
        switch (player) {
            case PLAYER1: player_1_total += increment;
                          displayForPlayer(PLAYER1, player_1_total);
                          break;
            case PLAYER2: player_2_total += increment;
                          displayForPlayer(PLAYER2, player_2_total);
                          break;
            default:      throw new RuntimeException("No Player Specified.");

        }
    }

    /**
     * Decreases the life total of a given player by a given amount.
     * @param player
     * @param decrement
     */
    private void decrementLifeTotal(String player, int decrement) {
        switch (player) {
            case PLAYER1: player_1_total -= decrement;
                          displayForPlayer(PLAYER1, player_1_total);
                          break;
            case PLAYER2: player_2_total -= decrement;
                          displayForPlayer(PLAYER2, player_2_total);
                          break;
            default:      throw new RuntimeException("No Player Specified.");

        }
    }

    /**
     * Overload the test method so it is called with a default parameter.
     */
    private void displayForPlayer(String player, int score) {
        displayForPlayer(player, score, true);
    }

    /**
     * Displays the given score for a given player.
     * @param player
     * @param score
     */
    private void displayForPlayer(String player, int score, boolean shouldVibrate) {
        // get id for given player
        int resID = getResources().getIdentifier(player, "id", getPackageName());
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        TextView scoreView = (TextView) findViewById(resID);
        // change color and vibration based on life total
        String setColor;
        if (score <= 5 && score > 0) {
            setColor = YELLOW;
            if (shouldVibrate){
                v.vibrate(250);
            }
        } else if (score <= 0) {
            setColor = RED;
            if (shouldVibrate){
                v.vibrate(250);
            }
        } else {
            setColor = BLACK;
        }
        scoreView.setText(String.valueOf(score));
        scoreView.setTextColor(Color.parseColor(setColor));
    }

    @Override
    public void onPause() {
        // save score
        super.onPause();

        SharedPreferences.Editor editor = getSharedPreferences(STATE_NAME, MODE_PRIVATE).edit();
        editor.putInt(PLAYER1, player_1_total);
        editor.putInt(PLAYER2, player_2_total);
        editor.commit();
    }

    @Override
    public void onResume() {
        // retrieve score
        super.onResume();

        SharedPreferences savedState = getSharedPreferences(STATE_NAME, MODE_PRIVATE);
        player_1_total = savedState.getInt(PLAYER1, -1);
        player_2_total = savedState.getInt(PLAYER2, -1);

        displayForPlayer(PLAYER1, player_1_total, false);
        displayForPlayer(PLAYER2, player_2_total, false);
    }
}
