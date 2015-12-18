package eladmizrahi.ex2home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static boolean isOnHold;

    private TextView txtBestResult, txtRecentResult, edtBestResult, edtRecentResult;
    private Button btnSettings, btnStart;
    private int level, complexity;
    private long startTime;
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

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        level = prefs.getInt("level", 1);
        complexity = prefs.getInt("complexity", 0);

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
        // save what i to DB
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
//        redButtonView.generateCircles();
        redButtonView.invalidate();
        // get from DB
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

    private void startGame()
    {
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
                    long millis = System.currentTimeMillis() - startTime;
                    Date date = new Date(millis);
                    edtRecentResult.setText(fmt.format(date));
                }
            });
        }
    }
}
