package eladmizrahi.ex2class;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NewActivity extends AppCompatActivity
{
    private Button _btnPlay;
    private Button _btnShowSongs;
    private TextView _txtSongs;
    private MediaPlayer _player;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        _btnPlay = (Button) findViewById(R.id.btnPlay);
        _btnShowSongs = (Button) findViewById(R.id.btnShowSongs);
        _txtSongs = (TextView) findViewById(R.id.txtSongs);

        _btnShowSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                _txtSongs.setText(readTxtFileFromRaw());
            }

            private String readTxtFileFromRaw()
            {
                InputStream input = getResources().openRawResource(R.raw.soundtrack_list);
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String musicList = "";
                String line = "";

                try
                {
                    while ((line = reader.readLine()) != null)
                    {
                        musicList += line + "\n";
                    }

                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return musicList;
            }
        });

        _btnPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _player = MediaPlayer.create(v.getContext(), R.raw.soundtrack);

                _player.start();
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if (_player != null)
        {
            _player.stop();
        }
    }


}
