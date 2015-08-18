package com.example.android.magicgamecounter;

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

    // Instance variables
    int player_1_total = 20;
    int player_2_total = 20;


    /**
     * Fires when app starts up.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
     * Displays the given score for a given player.
     * @param player
     * @param score
     */
    private void displayForPlayer(String player, int score) {
        // get id for given player
        int resID = getResources().getIdentifier(player, "id", getPackageName());
        TextView scoreView = (TextView) findViewById(resID);
        scoreView.setText(String.valueOf(score));
    }
}
