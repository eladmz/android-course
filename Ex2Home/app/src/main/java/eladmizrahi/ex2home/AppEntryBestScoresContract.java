package eladmizrahi.ex2home;

import android.provider.BaseColumns;

/**
 * Created by il731158 on 13/12/2015.
 */
public class AppEntryBestScoresContract
{
    public AppEntryBestScoresContract()
    {

    }

    public static abstract class AppEntryBestScores implements BaseColumns
    {
        public static final String TABLE_NAME = "scores";
        public static final String BEST_SCORE = "best_score";
        public static final String LEVEL = "level";
        public static final String COMPLEXITY = "complexity";
    }
}
