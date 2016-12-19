package ru.startandroid.lw07;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sm;
    Sensor s;

    Button b1;
    TextView t1, t2;

    double x,y,z,xmax=0,ymax=0,zmax=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm=(SensorManager) getSystemService (Context.SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        b1=(Button) findViewById(R.id.button1);
        t1=(TextView) findViewById(R.id.textView1);
        t2=(TextView) findViewById(R.id.textView2);

        View.OnClickListener B1 = new View.OnClickListener() {
            public void onClick(View v) {
                xmax=0;
                ymax=0;
                zmax=0;
                t2.setText(xmax+"  "+ymax+"  "+zmax);
            }
        };
        b1.setOnClickListener(B1);
    }

    @Override
    public final void onAccuracyChanged (Sensor sensor, int accuracy){} //НЕ МЕНЯТЬ

    @Override
    public final void onSensorChanged(SensorEvent event) {
        t1=(TextView) findViewById(R.id.textView1);
        t2=(TextView) findViewById(R.id.textView2);

        x=event.values[0];
        y=event.values[1];
        z=event.values[2];
        t1.setText(x+"  "+y+"  "+z);

        if (x>xmax) xmax=x;
        if (y>ymax) ymax=y;
        if (z>zmax) zmax=z;
        if ((x>xmax) && (y>ymax)) {xmax=x; ymax=y;}
        if ((y>ymax) && (z>zmax)) {ymax=y; zmax=z;}
        if ((x>xmax) && (z>zmax)) {xmax=x; zmax=z;}
        if ((x>xmax) && (y>ymax) && (z>zmax)) {xmax=x; ymax=y; zmax=z;}

        t2.setText(xmax+"  "+ymax+"  "+zmax);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
