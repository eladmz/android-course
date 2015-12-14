package eladmizrahi.ex2home;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements TextWatcher
{
    private EditText edtLevel, edtComplexity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        edtLevel = (EditText) findViewById(R.id.edtLevel);
        edtLevel.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10")});
        edtComplexity = (EditText) findViewById(R.id.edtComplexity);

        edtLevel.addTextChangedListener(this);
        edtComplexity.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        String levelText = edtLevel.getText().toString();
        if (levelText.length() == 0)
        {
            edtLevel.setError("Can't be empty");
        }
        else
        {
            // save it
        }

        String ComplexityText = edtComplexity.getText().toString();
        if (ComplexityText.length() == 0)
        {
            edtComplexity.setError("Can't be empty");
        }
        else
        {
            // save it
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
