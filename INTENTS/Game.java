/**
 * Michael Dorfman
 * CP430
 * 03/29/2018
 */

package com.vortex1409.cp430intentsmdorfman;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import java.util.Random;

public class Game extends AppCompatActivity {

    // Variables
    private int _rndvalue = 0;
    public final static String username = "";

    // View & Text Objects
    TextView tvuser;
    TextView tvrnd;
    EditText etresult;
    TextView cheat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Generate Random Number
        _rndvalue = generateRandomNumber(10);

        // Retrieve the Intent
        Intent GameIntent = getIntent();

        // Set Username from Intent
        tvuser = (TextView)findViewById(R.id.txtUsername);
        tvuser.setText("Welcome " + GameIntent.getStringExtra(username));

        // Set Textboxes and labels
        tvrnd = (TextView)findViewById(R.id.lblCheat);
        etresult = (EditText)findViewById(R.id.txtResult);
        cheat =  (TextView)findViewById(R.id.lblCheat);
        cheat.setText(String.valueOf(_rndvalue));

    }

    /***
     * Generate Random Number Method
     * @param max Max Integer Input
     * @return Random Number
     */
    private int generateRandomNumber(int max)
    {
        Random rdm = new Random();
        return rdm.nextInt(max + 1);
    }

    /***
     * Guess Method
     *
     * @param view View Object Input
     */
    public void Guess(View view)
    {
        EditText etNumber = (EditText)findViewById(R.id.txtNumber);
        int Guess = Integer.parseInt(etNumber.getText().toString());
        if(Guess == _rndvalue)
        {
            etresult.setText("CORRECT | " + Guess + " = " + _rndvalue);
        }
        else
        {
            etresult.setText("Incorrect");
        }
    }

    /***
     * Reset Method
     * Resets the Random Number
     * @param view View Object Input
     */
    public void Reset(View view)
    {
        EditText etNumber = (EditText)findViewById(R.id.txtNumber);
        _rndvalue = generateRandomNumber(10);
        cheat.setText(String.valueOf(_rndvalue));
        etNumber.setText("");

    }

    /***
     * Quit Method
     * Quits the current Activity
     * @param view View Object Input
     */
    public void Quit(View view)
    {
        finish();
    }
}
