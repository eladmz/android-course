package eladmizrahi.ex2home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener
{
    public static boolean isOnHold;

    private TextView txtBestResult, txtRecentResult, edtBestResult, edtRecentResult;
    private Button btnSettings, btnStart;
    private int level, complexity;
    private AppEntryBestScoresDAL dal;
    private long startTime, currentTime, bestTime;
    RedButtonView redButtonView;

    private SharedPreferences prefs;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        txtBestResult = (TextView) findViewById(R.id.txtBestResult);
        txtRecentResult = (TextView) findViewById(R.id.txtRecentResult);
        edtBestResult = (TextView) findViewById(R.id.edtBestResult);
        edtRecentResult = (TextView) findViewById(R.id.edtRecentResult);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnStart = (Button) findViewById(R.id.btnStart);
        redButtonView = (RedButtonView) findViewById(R.id.redButtonView);

        btnSettings.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        redButtonView.setOnTouchListener(this);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        level = prefs.getInt("level", 1);
        complexity = prefs.getInt("complexity", 0);
        dal = new AppEntryBestScoresDAL(getApplicationContext());

        bestTime = dal.getBestScore(level, complexity);
        if (bestTime != 0)
            displayBestTime();

        isOnHold = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
        isOnHold = true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        txtRecentResult.setText(R.string.txtRecentResult);
        edtRecentResult.setText("");
        level = prefs.getInt("level", 1);
        complexity = prefs.getInt("complexity", 0);
        bestTime = dal.getBestScore(level, complexity);
        if (bestTime != 0)
            displayBestTime();
        redButtonView.invalidate();
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnSettings && isOnHold)
        {
            Intent intent = new Intent(v.getContext(), SettingsActivity.class);
            startActivity(intent);
        }
        else if (v == btnStart && isOnHold)
        {
            isOnHold = false;
            startGame();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!isOnHold && v.onTouchEvent(event))
            level--;
        checkGameOver();
        return true;
    }

    private void checkGameOver()
    {
        if (level == 0 && timer != null)
        {
            timer.cancel();
            timer = null;
            isOnHold = true;
            redButtonView.invalidate();
            if (bestTime != 0 && currentTime < bestTime)
            {
                bestTime = currentTime;
                dal.addBestScoreEntry(bestTime, level, complexity);
                displayBestTime();
            }
        }
    }

    private void startGame()
    {
        level = prefs.getInt("level", 1);
        complexity = prefs.getInt("complexity", 0);
        redButtonView.generateObjects();
        txtRecentResult.setText(R.string.txtCurrentTime);
        startTime = System.currentTimeMillis();
        timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 0, 100);
        redButtonView.invalidate();
    }

    private class MyTimerTask extends TimerTask
    {
        private SimpleDateFormat fmt = new SimpleDateFormat("ss:SSS");
        @Override
        public void run()
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    currentTime = System.currentTimeMillis() - startTime;
                    Date date = new Date(currentTime);
                    edtRecentResult.setText(fmt.format(date));
                }
            });
        }
    }

    private void displayBestTime()
    {
        SimpleDateFormat fmt = new SimpleDateFormat("ss:SSS");
        Date date = new Date(bestTime);
        edtBestResult.setText(fmt.format(date));
    }
}
