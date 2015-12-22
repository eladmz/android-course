package eladmizrahi.ex2home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by il731158 on 13/12/2015.
 */
public class AppEntryBestScoresDBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AppTimeBestScores.db";

    public AppEntryBestScoresDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + AppEntryBestScoresContract.AppEntryBestScores.TABLE_NAME + "(" +
                        AppEntryBestScoresContract.AppEntryBestScores._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        AppEntryBestScoresContract.AppEntryBestScores.BEST_SCORE + " INTEGER, " +
                        AppEntryBestScoresContract.AppEntryBestScores.LEVEL + " INTEGER, " +
                        AppEntryBestScoresContract.AppEntryBestScores.COMPLEXITY + " INTEGER " +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + AppEntryBestScoresContract.AppEntryBestScores.TABLE_NAME);
        onCreate(db);
    }
}
