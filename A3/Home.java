/***
 * Michael Dorfman
 * CP430 - Assignment 3
 * 04/04/2018
 */

package com.vortex1409.cp430assignment3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Home extends AppCompatActivity {

    /***
     * OnCreate Method
     * This method creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get Stored Username & Set the EditText
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString("username", "Alice the Camel");
        EditText username_txt = findViewById(R.id.txtUsername);
        username_txt.setText(username);

        // Calculates Time Difference
        TimeDifference();

        // Toggle Switch Operation
        // Toggles TextView Visibility
        Switch ToggleSW = findViewById(R.id.ToggleSwitch);
        ToggleSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView msg = findViewById(R.id.tvToggle);
                if(isChecked){
                    msg.setVisibility(View.VISIBLE);
                }else{
                    msg.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /***
     * OnRestart Method
     * Determine if the user is going back to the login page or not
     */
    @Override
    public void onRestart(){
        super.onRestart();

        // Set Login Flag
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean LF = pref.getBoolean("LF", false);

        // Login Flag is True Then Launch Login Activity
        if(LF){
            Intent AuthIntent = new Intent(this, Auth.class);
            startActivity(AuthIntent);
        }

        // Set Flags
        SharedPreferences.Editor SP_EDITOR = pref.edit();

        // Set Login Flag
        SP_EDITOR.putBoolean("LF", false);
        SP_EDITOR.apply();

        // Set Returning Flag
        SP_EDITOR.putBoolean("RFLAG", false);
        SP_EDITOR.apply();

        /***
         * Note
         * Login Flag is needed because swapping activities calls system methods
         * that mess with the time setting and flags. The additional flag prevents the time from
         * being logged when it is not needed.
         */
    }


    /***
     * OnStop Method
     * This method records the time with the application is stopped
     */
    @Override
    public void onStop(){
        super.onStop();

        // Create Preference Editor
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor SP_EDITOR = pref.edit();

        // Retrieve Current Time
        Calendar Date = Calendar.getInstance();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String TU = DATE_FORMAT.format(Date.getTime());

        // Set Login Flag
        SP_EDITOR.putBoolean("LF", true);
        SP_EDITOR.apply();

        // Retrieve Returning Flag
        boolean RFLAG = pref.getBoolean("RFLAG", false);

        // Only Store Time if we are not returning to the application/activity
        if(RFLAG){
            SP_EDITOR.putString("TU", TU);
            SP_EDITOR.apply();
        }
    }

    /***
     * SetUsernameClick Method
     * This method retrieves and stores the username entered in a textbox for persistant use
     * @param view
     */
    public void SetUseranmeClick(View view)
    {
        // Retrieve The Username
        EditText txtUsername = findViewById(R.id.txtUsername);
        String username = txtUsername.getText().toString();

        // Store the Retrieved Username
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor SP_EDITOR = pref.edit();
        SP_EDITOR.putString("username", username);
        SP_EDITOR.apply();

        // Set the Username in EditText
        txtUsername.setText(username);
    }

    /***
     * Time Difference Method
     * This method calculates the time the user was away from the application and displays it
     */
    public void TimeDifference()
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        // Set Logged Time & Time Unfocused
        String LT = pref.getString("LT", "0:00");
        String TU = pref.getString("TU", "0:00");

        TextView tvLT = findViewById(R.id.tvLT);
        tvLT.setText(LT);
        TextView tvPT = findViewById(R.id.tvPT);
        tvPT.setText(TU);

        // Set Date Operation Variables
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date DATE_TU;
        Date DATE_LT;

        // Date Operation (Parse Errors Are Caught by Try/Catch)
        try{
            DATE_TU = DATE_FORMAT.parse(TU);
            DATE_LT = DATE_FORMAT.parse(LT);
            long TOTAL = DATE_LT.getTime() - DATE_TU.getTime();
            long SEC = TOTAL / 1000 % 60;
            long MIN = TOTAL / (60 * 1000) % 60;
            long HRS = TOTAL / (60 * 60 * 1000) % 24;
            long DAY = TOTAL / (24 * 60 * 60 * 1000);
            TextView tvDIFF = findViewById(R.id.tvDIFF);
            tvDIFF.setText(DAY + " days \n" + HRS + " hours \n" + MIN + " minutes \n" + SEC + " seconds");
        }
        catch(Exception e)
        {
            // On First Login or Parse Error
            TextView tvDIFF = findViewById(R.id.tvDIFF);
            tvDIFF.setText("0:00");
        }
    }
}
