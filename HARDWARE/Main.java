package com.vortex1409.cp430hardware;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Main extends AppCompatActivity
{

    // Sensor Manager
    private SensorManager mSensorManager;

    /**
     * onCreate Method
     * Creates the Activity Instance
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * ListSensor Method
     * THis method lists all the sensors in a TextView
     * @param view
     */
    public void ListSensors(View view)
    {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        TextView SensorList = findViewById(R.id.tvLISTSENSOR);
        TextView SL = findViewById(R.id.tvSL);
        String A = "";
        String B = String.valueOf(deviceSensors.size());
        for(int i = 0; i < deviceSensors.size(); i++)
        {
            Log.d("SENSOR", "-> " + deviceSensors.get(i).getName());
            A += deviceSensors.get(i).getName() + "\n";
        }
        SensorList.setText(A);
        SL.setText("There are " + B + " sensors");
    }

    /**
     * SpaceMethod
     * This method launches a new activity
     * @param view
     */
    public void Space(View view)
    {
        Intent SpaceIntent = new Intent(this, SPACE.class);
        startActivity(SpaceIntent);
    }
}
