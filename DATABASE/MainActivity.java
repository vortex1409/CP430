package com.vortex1409.cp430database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Object Declaration (Class Level)
    DB_HELPER Helper;
    SQLiteDatabase DBW;
    SQLiteDatabase DBR;
    ContentValues values;
    Cursor RC;

    /**
     * onCreate Method (Override)
     * This method creates the activity and creates an
     * instance of the SQL objects needed such as DB Write and Read
     * as well as a cursor
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Helper = new DB_HELPER(getApplicationContext());
        DBW = Helper.getWritableDatabase();
        DBR = Helper.getReadableDatabase();
        values = new ContentValues();
        RC = ReadCursor();
    }

    /**
     * Populate Method
     * This method takes arrays of strings and populates them into the database
     * using a loop
     * @param view
     */
    public void Populate(View view)
    {
        String[] OWNER = {"Michael", "Alexandria", "Aurora", "Red", "Blu", "Kuma"};
        String[] PET_NAME = {"Disgrace of the Land", "LINDA", "Derp", "Skittles", "Adra", "Poobado"};
        int[] PET_AGE = {15, 22, 44, 11, 66, 77};
        String[] PET_TYPE = {"Fern Hound", "Armored Fish", "Bird", "Sand Lion" ,"Wyvern" ,"Spider"};
        for(int i = 0; i < OWNER.length; i++) {
            values.put(DB_CONTRACT.DB_ENTRY.OWNER, OWNER[i]);
            values.put(DB_CONTRACT.DB_ENTRY.PET_NAME, PET_NAME[i]);
            values.put(DB_CONTRACT.DB_ENTRY.PET_TYPE, PET_TYPE[i]);
            long newRowId = DBW.insert(DB_CONTRACT.DB_ENTRY.TABLE_NAME, null, values);
            values.clear();
        }
        RC = ReadCursor();
    }

    /**
     * Display Method
     * This method checks to see if the cursor is not null and moved the cursor ahead one
     *
     * Note: Cursor starts at -1
     * @param view
     */
    public void Display(View view)
    {
        if(RC != null)
        {
            RC.moveToNext();
        }
        if(RC.isAfterLast())
        {
            TextView tvDATA =  findViewById(R.id.tvDATA);
            tvDATA.setText("End of Data");
        }
        else
        {
            String OWNER = RC.getString(RC.getColumnIndex(DB_CONTRACT.DB_ENTRY.OWNER));
            String PET_NAME = RC.getString(RC.getColumnIndex(DB_CONTRACT.DB_ENTRY.PET_NAME));
            int PET_AGE = RC.getInt(RC.getColumnIndex(DB_CONTRACT.DB_ENTRY.PET_NAME));
            String PET_TYPE = RC.getString(RC.getColumnIndex(DB_CONTRACT.DB_ENTRY.PET_TYPE));

            TextView record_set_field = findViewById(R.id.tvDATA);
            record_set_field.setText(
                            "Owner: " + OWNER +
                            "\nPet Name: " + PET_NAME +
                            "\nPet Type: " + PET_TYPE
            );
        }
    }

    /**
     * Reset Method
     * This method resets the cursor
     * @param view
     */
    public void Reset(View view)
    {
        RC.moveToFirst();
    }

    /**
     * ReadCursor Method
     * This method reads the cursor data
     * @return
     */
    private Cursor ReadCursor()
    {
        Cursor cursor = DBR.rawQuery("select OWNER, PET_NAME, PET_TYPE from Info",null);
        return cursor;
    }
}
