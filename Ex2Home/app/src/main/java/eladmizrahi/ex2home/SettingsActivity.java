package eladmizrahi.ex2home;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements TextWatcher
{
    private EditText edtLevel, edtComplexity;
    private int level, complexity;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        edtLevel = (EditText) findViewById(R.id.edtLevel);
        edtLevel.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
        edtComplexity = (EditText) findViewById(R.id.edtComplexity);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        level = prefs.getInt("level", 1);
        complexity = prefs.getInt("complexity", 0);

        edtLevel.setText(level + "");
        edtComplexity.setText(complexity + "");

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
            SharedPreferences.Editor editor = prefs.edit();
            level = Integer.parseInt(levelText);
            editor.putInt("level", level);
            editor.apply();
        }

        String complexityText = edtComplexity.getText().toString();
        if (complexityText.length() == 0)
        {
            edtComplexity.setError("Can't be empty");
        }
        else
        {
            SharedPreferences.Editor editor = prefs.edit();
            complexity = Integer.parseInt(complexityText);
            editor.putInt("complexity", complexity);
            editor.apply();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
