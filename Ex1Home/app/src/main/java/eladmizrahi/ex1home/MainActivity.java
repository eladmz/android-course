package eladmizrahi.ex1home;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
    Author: Elad Mizrahi
    Date:   15/11/2015
    Desc:   Game of speed - hit the "1" button and then the "2" button as fast as you can while they change randomly.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // UI buttons
    private Button _btnRight;
    private Button _btnLeft;
    private Button _btnMeasuredTime;
    private Button _btnBestTime;

    // times
    private long _currentTime, _measuerdTime, _bestTime;

    // utilities
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

        // if it was screen rotation - take the information from the saved bundle
        if (savedInstanceState != null)
        {
            _btnLeft.setText(savedInstanceState.getCharSequence("left_button"));
            _btnRight.setText(savedInstanceState.getCharSequence("right_button"));
            _measuerdTime = savedInstanceState.getLong("measured_time");
            if (_measuerdTime != NOT_USED)
                displayMeasuredTime();
        }
        else
        {
            chooseRandomNumbers();
            _measuerdTime = NOT_USED;
        }

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

    // save the button location when activity is no longer visible
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("left_button", _btnLeft.getText());
        outState.putCharSequence("right_button", _btnRight.getText());
        outState.putLong("measured_time", _measuerdTime);
    }

    // taking the saved button location (if there is any)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
        {
            _btnLeft.setText(savedInstanceState.getCharSequence("left_button"));
            _btnRight.setText(savedInstanceState.getCharSequence("right_button"));
            _measuerdTime = savedInstanceState.getLong("measured_time");
            if (_measuerdTime != NOT_USED)
                displayMeasuredTime();
        }

    }

    // on click listener - check what key is pressed and according to it checks whether the left or right button is 1 or 2
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

    // a function to choose random numbers for the buttons
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

    // a function that is being called when the user pressed 1 button
    private void pressedOne()
    {
        if (_currentTime == NOT_USED)
            _currentTime = System.currentTimeMillis();
    }

    // a function that is being called when the user pressed 2 button
    private void pressedTwo()
    {
        if (_currentTime != NOT_USED)
        {
            _measuerdTime = System.currentTimeMillis() - _currentTime;
            displayMeasuredTime();
            checkBestTime();
            _currentTime = NOT_USED;
            chooseRandomNumbers();
        }
    }

    // a function that is being called when the user pressed on the best time
    private void pressedBestTime()
    {
        _currentTime = NOT_USED;
        _bestTime = NOT_USED;
        _measuerdTime = NOT_USED;
        _btnMeasuredTime.setText("");
        _btnBestTime.setText("");
        saveBestTime(true);
        chooseRandomNumbers();
    }

    // checks the best time and if it's better update it and show a message
    private void checkBestTime()
    {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.popup ,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (_bestTime == NOT_USED)
        {
            _bestTime = _measuerdTime;
            saveBestTime(false);
            displayBestTime();
            toast.show();
        }
        else
        {
            if (_measuerdTime < _bestTime)
            {
                _bestTime = _measuerdTime;
                saveBestTime(false);
                displayBestTime();
                toast.show();
            }
        }
    }

    // display the best time in the right format
    private void displayBestTime()
    {
        Date date = new Date(_bestTime);
        _btnBestTime.setText(_fmt.format(date));
    }

    // display the measured time in the right format
    private void displayMeasuredTime()
    {
        Date date = new Date(_measuerdTime);
        _btnMeasuredTime.setText(_fmt.format(date));
    }

    // save or deletes the best time (according to delete value)
    private void saveBestTime(boolean delete)
    {
        SharedPreferences.Editor editor = prefs.edit();
        if (delete)
            editor.putString("best_time", "");
        else
            editor.putString("best_time", "" + _bestTime);
        editor.apply();
    }
}
