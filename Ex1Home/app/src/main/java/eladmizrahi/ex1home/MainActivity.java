package eladmizrahi.ex1home;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // UI buttons
    private Button _btnRight;
    private Button _btnLeft;
    private Button _btnMeasuredTime;
    private Button _btnBestTime;

    // times
    private long _currentTime, _measuerdTime, _bestTime;

    // Utilities
    private SharedPreferences prefs;
    private static final SimpleDateFormat _fmt = new SimpleDateFormat("mm:ss:SS");;
    private static final long NOT_USED = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize buttons
        _btnRight = (Button) findViewById(R.id.btnRight);
        _btnLeft = (Button) findViewById(R.id.btnLeft);
        _btnMeasuredTime = (Button) findViewById(R.id.btnCurrentTime);
        _btnBestTime = (Button) findViewById(R.id.btnBestTime);

        // set listeners
        _btnLeft.setOnClickListener(this);
        _btnRight.setOnClickListener(this);
        _btnBestTime.setOnClickListener(this);

        chooseRandomNumbers();

        _currentTime = NOT_USED;
        _bestTime = NOT_USED;

        // get the name from preferences file
        prefs = getSharedPreferences("scores", MODE_PRIVATE);
        String bestTime = prefs.getString("best_time","");
        if (!bestTime.equals(""))
        {
            _bestTime = Long.parseLong(bestTime);
            displayBestTime();
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v == _btnLeft)
        {
            if (_btnLeft.getText().equals("1"))
                pressedOne();
            else
                pressedTwo();
        }
        else if (v == _btnRight)
        {
            if (_btnRight.getText().equals("1"))
                pressedOne();
            else
                pressedTwo();
        }
        else if (v == _btnBestTime)
        {
            pressedBestTime();
        }
    }

    private void chooseRandomNumbers()
    {
        // get random number (0 or 1)
        Random rand = new Random();
        int randNum = rand.nextInt(2);

        if (randNum == 0)
        {
            _btnRight.setText("1");
            _btnLeft.setText("2");
        }
        else
        {
            _btnLeft.setText("1");
            _btnRight.setText("2");
        }
    }

    private void pressedOne()
    {
        if (_currentTime == NOT_USED)
            _currentTime = System.currentTimeMillis();
    }

    private void pressedTwo()
    {
        if (_currentTime != NOT_USED)
        {
            _measuerdTime = System.currentTimeMillis() - _currentTime;
            Date date = new Date(_measuerdTime);
            _btnMeasuredTime.setText(_fmt.format(date));
            checkBestTime();
            _currentTime = NOT_USED;
            chooseRandomNumbers();
        }
    }

    private void pressedBestTime()
    {
        _currentTime = NOT_USED;
        _bestTime = NOT_USED;
        _btnMeasuredTime.setText("");
        _btnBestTime.setText("");
    }

    private void checkBestTime()
    {
        if (_bestTime == NOT_USED)
        {
            _bestTime = _measuerdTime;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("best_time", "" + _bestTime);
            editor.apply();
            displayBestTime();
        }
        else
        {
            if (_measuerdTime < _bestTime)
            {
                _bestTime = _measuerdTime;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("best_time", "" + _bestTime);
                editor.apply();
                displayBestTime();
            }
        }
    }

    private void displayBestTime()
    {
        Date date = new Date(_bestTime);
        _btnBestTime.setText(_fmt.format(date));
    }
}
