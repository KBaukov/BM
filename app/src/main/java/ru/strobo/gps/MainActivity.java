package ru.strobo.gps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) this.findViewById(R.id.textView);
    }

    public void onClickStart(View v) {
        textView.setText("start");
        startService(new Intent(this, BmService.class));
    }

    public void onClickStop(View v) {
        textView.setText("stop");
        stopService(new Intent(this, BmService.class));
    }

    public void onClickExit(View v) {
        textView.setText("exit");
        this.finish();
    }
}
