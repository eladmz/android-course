package eladmizrahi.ex2class;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private TextView _txtName;
    private EditText _edtName;
    private Button _btnSave;
    private Button _btnNext;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _btnSave = (Button) findViewById(R.id.btnSave);
        _btnNext = (Button) findViewById(R.id.btnNext);
        _edtName = (EditText) findViewById(R.id.edtName);
        _txtName = (TextView) findViewById(R.id.txtName);

        _btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = prefs.edit();

                // save to shared preferences
                String name = _edtName.getText().toString();
                editor.putString("saved_name", name);
                editor.apply();

                // display the name
                _txtName.setText(name);
            }
        });

        _btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), NewActivity.class);

                startActivity(intent);
            }
        });

        // get the name from preferences file
        prefs = getSharedPreferences("names", MODE_PRIVATE);
        String name = prefs.getString("saved_name","");

        _txtName.setText(name);
    }
}
