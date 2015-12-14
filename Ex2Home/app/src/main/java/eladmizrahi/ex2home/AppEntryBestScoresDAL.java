package eladmizrahi.ex2home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by il731158 on 13/12/2015.
 */
public class AppEntryBestScoresDAL
{
    private AppEntryBestScoresDBHelper helper;

    public AppEntryBestScoresDAL(Context context)
    {
        helper = new AppEntryBestScoresDBHelper(context);
    }

    public void addBestScoreEntry(String score, int level, int complexity){
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();

        //values to save
        ContentValues values = new ContentValues();
        values.put(AppEntryBestScoresContract.AppEntryBestScores.BEST_SCORE, score);
        values.put(AppEntryBestScoresContract.AppEntryBestScores.LEVEL, level);
        values.put(AppEntryBestScoresContract.AppEntryBestScores.COMPLEXITY, complexity);

        //save the values
        db.insert(AppEntryBestScoresContract.AppEntryBestScores.TABLE_NAME, null, values);
        db.close();
    }

    public String getBestScore(int level, int complexity)
    {
        String bestScore = "";

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] selectionArgs = { level + "", complexity + ""};
        Cursor cursor = db.rawQuery("SELECT " + AppEntryBestScoresContract.AppEntryBestScores.BEST_SCORE + " FROM " +
                                    AppEntryBestScoresContract.AppEntryBestScores.TABLE_NAME + " WHERE " +
                                    AppEntryBestScoresContract.AppEntryBestScores.LEVEL + " =? AND " +
                                    AppEntryBestScoresContract.AppEntryBestScores.COMPLEXITY + " =? ", selectionArgs);

        while (cursor.moveToNext())
        {
            int bestScoreIndex = cursor.getColumnIndex(AppEntryBestScoresContract.AppEntryBestScores.BEST_SCORE);
            bestScore = cursor.getString(bestScoreIndex);
        }

        db.close();

        return bestScore;
    }

    public void updateBestScore(String score, int level, int complexity)
    {
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppEntryBestScoresContract.AppEntryBestScores.BEST_SCORE, score);

        String where = AppEntryBestScoresContract.AppEntryBestScores.LEVEL + " =? AND " +
                       AppEntryBestScoresContract.AppEntryBestScores.COMPLEXITY + " =? ";
        String[] whereArgs = { level + "", complexity + "" };

        db.update(AppEntryBestScoresContract.AppEntryBestScores.TABLE_NAME, values, where, whereArgs);

        db.close();
    }
}
