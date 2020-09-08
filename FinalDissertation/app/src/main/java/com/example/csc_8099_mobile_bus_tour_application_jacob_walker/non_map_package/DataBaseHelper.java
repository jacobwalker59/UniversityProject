package com.example.csc_8099_mobile_bus_tour_application_jacob_walker.non_map_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    //database for time table
    //taken from the following youtube video
    //https://www.youtube.com/watch?v=cp2rL3sAFmI

    // creates database and table

    private static final String DATA_BASE_NAME = "CUMBRIAN_CLASSIC_COACHES";
    private static final String DB_TABLE = "TIME_TABLE";
    private static final String ID ="ID";
    private static final String BUS_STOP_NAME = "BUS_STOP_NAME";
    private static final String APPROACH = "APPROACH";
    private static final String RETURNING = "RETURNING";

    //sql string necessary in order to pass to mySQLite

    private static final String CREATE_TABLE = "CREATE TABLE "+DB_TABLE+" ("+
            ID + " PRIMARY KEY AUTOINCREMENT, " +
            BUS_STOP_NAME + " TEXT, "+
            APPROACH + " TEXT, "+
            RETURNING + " TEXT "+ ")";


    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
        onCreate(sqLiteDatabase);

    }

    //adds data to the database,
    //seems to be the generliased method of inserting for mySQLIte
    //https://www.youtube.com/watch?v=T0ClYrJukPA&t=515s

    public boolean insertData(String name,String approach, String returning){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BUS_STOP_NAME, name);
        cv.put(APPROACH, approach);
        cv.put(RETURNING,returning);
        long result = db.insert(DB_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //used to get all data which was then iterated through and passed into a temporary Array List
    //ideally used for JSON info or path constraints in the future

    public Cursor viewData() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    // database check database to see if database exists
    // validation purposes
    // code taken from https://www.youtube.com/watch?v=SK98ayjhk1E

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATA_BASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    //deletes all from database table, necessary for testing components and for erasing mistales

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);
        db.close();
    }

}