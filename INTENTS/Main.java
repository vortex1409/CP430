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

public class Main extends AppCompatActivity {

    public final static String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GameLauncher(View view)
    {
        // Find the Username EditText
        EditText ET = (EditText)findViewById(R.id.txtUsername);

        // Create Intent
        Intent MainIntent = new Intent(this, Game.class);

        // Shove Information into the Intent
        MainIntent.putExtra(username, ET.getText().toString());

        // Start New Activity And Pass the Intent
        startActivity(MainIntent);
    }
}
