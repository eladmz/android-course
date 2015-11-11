package eladmizrahi.ex1class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Button btnShow;
    private EditText editText;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        txtView = (TextView) findViewById(R.id.textView);
        txtView.setText("");

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtView.setText(editText.getText());
                editText.setText("");
            }
        });

        btnShow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                txtView.setText("");
                editText.setText("");
                return true;
            }
        });
    }
}
