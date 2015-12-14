package eladmizrahi.ex2home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView txtBestResult, txtRecentResult, edtBestResult, edtRecentResult;
    private Button btnSettings, btnStart;
    private boolean isOnHold;

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

        btnSettings.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        isOnHold = true;
    }

    @Override
    public void onClick(View v)
    {
        if (v == btnSettings)
        {
            if (isOnHold)
            {
                Intent intent = new Intent(v.getContext(), SettingsActivity.class);

                startActivity(intent);
            }
        }
        else if (v == btnStart)
        {
            isOnHold = false;

            txtRecentResult.setText(R.string.txtCurrentTime);
        }
    }
}
