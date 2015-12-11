package eladmizrahi.ex6class;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View screen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View screenView = findViewById(R.id.screen);

        // Find the root view
        screen = screenView.getRootView();

        new changeBackground().execute();
    }

    private class changeBackground extends AsyncTask<Void, Integer, Integer>
    {
        private int numOfTimes;
        private Random rand;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            rand = new Random();

            numOfTimes = 5 + rand.nextInt(6);
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int color = 0;
            for (int i = 0; i < numOfTimes; ++i)
            {
                try
                {
                    Thread.sleep(5000);
                    rand = new Random();
                    color = rand.nextInt(Integer.MAX_VALUE);
                    publishProgress(color);
                }
                catch (Exception e)
                {

                }
            }
            return numOfTimes;
        }

        @Override
        protected void onProgressUpdate(Integer... progress)
        {
            //Log.d("mashu","" + Integer.toHexString(progress[0]));
            screen.setBackgroundColor(progress[0]);
    }

        @Override
        protected void onPostExecute(Integer result)
        {
            Toast.makeText(getBaseContext(), "" + numOfTimes, Toast.LENGTH_LONG).show();
        }

    }
}


