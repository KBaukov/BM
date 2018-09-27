package ru.strobo.gps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ru.strobo.gps.data.BmDbHelper;
import ru.strobo.gps.data.ent.Config;
import ru.strobo.gps.services.LocationService;

public class MainActivity extends AppCompatActivity {

    private EditText devFld, hostFld, portFld;
    private TextView info;
    private BmDbHelper db = new BmDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devFld = (EditText) this.findViewById(R.id.devField);
        hostFld = (EditText) this.findViewById(R.id.hostField);
        portFld = (EditText) this.findViewById(R.id.portField);
        info = (TextView) this.findViewById(R.id.infoLabel);

        Config cfg = db.getConfig();

        devFld.setText(cfg.getDevId());
        hostFld.setText(cfg.getHost());
        portFld.setText(cfg.getPort());
    }

    public void onClickStart(View v) {
        //textView.setText("start");
        startService(new Intent(this, LocationService.class));

        info.setText( info.getText().toString() + "\n" + "Служба активна");

        int count = db.getUnSendedPoinstCount();

        info.setText( info.getText().toString() + "\n" + "Не отправлено :" + count );
    }

    public void onClickStop(View v) {
        //textView.setText("stop");
        stopService(new Intent(this, LocationService.class));

        info.setText( info.getText().toString() + "\n" + "Служба не активна");
    }

    public void onClickExit(View v) {

        String devId = devFld.getText().toString();
        String host = hostFld.getText().toString();
        String port = "8080";

        db.seveConfig(devId,host,port);

        db.close();

        this.finish();
    }
}
