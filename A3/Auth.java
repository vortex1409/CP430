/***
 * Michael Dorfman
 * CP430 - Assignment 3
 * 04/04/2018
 */

package com.vortex1409.cp430assignment3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Auth extends AppCompatActivity {

    /***
     * OnCreate Method
     * This method creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    /***
     * OnStart Method
     * This method sets the visibility of objects depending on a saved password flag state
     */
    @Override
    protected void onStart()
    {
        super.onStart();

        // Retrieved Stored Password
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String AuthPass = pref.getString("PW", null);

        // If There is a passsword stored hide the 2nd password field
        if(AuthPass != null)
        {
            EditText PW2 = findViewById(R.id.etPW2);
            PW2.setVisibility(View.INVISIBLE);
            TextView LPW2 = findViewById(R.id.lblPW2);
            LPW2.setVisibility(View.INVISIBLE);
            Button btnReset = findViewById(R.id.btnReset);
            btnReset.setVisibility(View.VISIBLE);
        }
    }

    /***
     *  LoginClick Method
     *  This method logs the user into the application
     *  On First Run the user must pick a password
     *  On Other Runs the user enters the saved password or resets the password to a default
     * @param view
     */
    public void LoginClick(View view)
    {
        // Retrieving Password Values from Fields
        EditText PW1 = findViewById(R.id.etPW1);
        EditText PW2 = findViewById(R.id.etPW2);
        String PW_TXT =  PW1.getText().toString();
        String PWR_TXT = PW2.getText().toString();

        // If 2nd Password Field is Hidden (Password Saved/Stored)
        if(PW2.getVisibility() == View.INVISIBLE)
        {
            // Retrieved Stored Password
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            String PWS = pref.getString("PW", null);

            // If Input Matches Stored Password
            if(PW_TXT.equals(PWS))
            {
                // Retrieve the Time
                Calendar Date = Calendar.getInstance();
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                // Store the Logged In Time (LT)
                SharedPreferences.Editor SP_EDITOR = pref.edit();
                String LT = DATE_FORMAT.format(Date.getTime());
                SP_EDITOR.putString("LT", LT);
                SP_EDITOR.apply();

                // Reset Returning Flag (RFLAG)
                SP_EDITOR.putBoolean("RFLAG", true);
                SP_EDITOR.apply();

                // Start Logged In Activity (Home)
                Intent HomeIntent = new Intent(this, Home.class);
                startActivity(HomeIntent);
            }
            else
            {
                // Else Wrong Password
                TextView tvERROR = findViewById(R.id.tvERROR);
                tvERROR.setText("Wrong Password");
            }
        }
        else
        {
            // Else Password Was Never Saved

            // Check if Passwords are the Same
            if(PW_TXT.equals(PWR_TXT))
            {
                // Set Login Time (LT)
                Calendar Date = Calendar.getInstance();
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String LT = DATE_FORMAT.format(Date.getTime());

                // Store Password (PW) & Logged In Time (LT)
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor SP_EDITOR = pref.edit();
                SP_EDITOR.putString("PW", PW_TXT);
                SP_EDITOR.apply();
                SP_EDITOR.putString("LT", LT);
                SP_EDITOR.apply();

                // Start Logged In Activity (Home)
                Intent HomeIntent = new Intent(this, Home.class);
                startActivity(HomeIntent);
            }
            else
            {
                // Else Wrong Password
                TextView tvERROR = findViewById(R.id.tvERROR);
                tvERROR.setText("Wrong Password");
            }
        }
    }

    /***
     * Reset Method
     * This method resets the password to "password"
     * @param view
     */
    public void ResetClick(View view)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor SP_EDITOR = pref.edit();
        SP_EDITOR.putString("PW", "password");
        SP_EDITOR.apply();
    }
}
