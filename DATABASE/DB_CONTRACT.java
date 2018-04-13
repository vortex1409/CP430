package com.vortex1409.cp430database;

import android.provider.BaseColumns;

/**
 * Created by mdorfman on 4/5/2018.
 */

public final class DB_CONTRACT
{
    /***
     * Constructor
     * Unused
     */
    private DB_CONTRACT()
    {

    }

    /**
     * DB_ENTRY METHOD
     * Sets the Table Column Names & Types
     */
    public static class DB_ENTRY implements BaseColumns
    {
        public static final String TABLE_NAME = "Info";
        public static final String OWNER = "OWNER";
        public static final String PET_NAME = "PET_NAME";
        public static final String PET_AGE = "PET_AGE";
        public static final String PET_TYPE = "PET_TYPE";
    }

    /**
     * Creates Table Names (Query)
     */
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DB_ENTRY.TABLE_NAME + " (" +
                    DB_ENTRY._ID + " INTEGER PRIMARY KEY," +
                    DB_ENTRY.OWNER + " TEXT," +
                    DB_ENTRY.PET_NAME + " TEXT," +
                    DB_ENTRY.PET_AGE + " INTEGER," +
                    DB_ENTRY.PET_TYPE + " TEXT)";

    /**
     * Drops Table (Query)
     */
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DB_ENTRY.TABLE_NAME;
}
