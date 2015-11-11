package eladmizrahi.ex1home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private Button _btnRight;
    private Button _btnLeft;
    private Button _btnCurrentTime;
    private Button _btnBestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize buttons
        _btnRight = (Button) findViewById(R.id.btnRight);
        _btnLeft = (Button) findViewById(R.id.btnLeft);
        _btnCurrentTime = (Button) findViewById(R.id.btnCurrentTime);
        _btnBestTime = (Button) findViewById(R.id.btnBestTime);

        chooseRandomNumbers();
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
}
