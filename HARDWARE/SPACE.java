package com.vortex1409.cp430hardware;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SPACE extends AppCompatActivity implements SensorEventListener, LocationListener
{

    // Sensor Fields
    private SensorManager SensorMgr;
    private Sensor SensorAccelerometer;

    // TextView Declarations
    TextView tvLAT;
    TextView tvLON;
    TextView tvX;
    TextView tvY;
    TextView tvZ;

    /**
     * onCreate Method
     * Creates the instance of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        tvLAT = findViewById(R.id.tvLAT);
        tvLON = findViewById(R.id.tvLON);
        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);
        tvZ = findViewById(R.id.tvZ);

        SensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SensorMgr.registerListener(this, SensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        SensorAccelerometer = SensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * onPause Method
     * Unregisters the Listener when the App is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        SensorMgr.unregisterListener(this);
    }

    /**
     * onResume Method
     * Registers the listeners on app resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        SensorMgr.registerListener(this, SensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * StartGPS Method
     * This method starts the GPS using event listeners
     * @param view
     */
    public void StartGPS(View view){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101 );
        }else {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    UpdateGPS(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    /**
     * UpdateGPS Method
     * This method populates the TextViews with the GPS data
     * @param location Location Object
     */
    public void UpdateGPS(Location location)
    {
        tvLAT.setText("Latitude: " + Double.toString(location.getLatitude()));
        tvLON.setText("Longitude: " + Double.toString(location.getLongitude()));
    }

    /**
     * UpdateAccelerometer Method
     * This method updates the accelerometer TextViews
     * @param x X Value
     * @param y Y Value
     * @param z Z Value
     */
    public void UpdateAccelerometer(float x, float y, float z)
    {
        tvX.setText("X: " + String.valueOf(x));
        tvY.setText("Y: " + String.valueOf(y));
        tvZ.setText("Z: " + String.valueOf(z));
    }

    /**
     * onSensorChanged Method
     * This method takes in the sensor event data
     * and sends the X,Y and Z values to the
     * UpdateAccelerometer method for data output
     * @param event Sensor Event Object
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor Accelerometer = event.sensor;

        if (Accelerometer.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            UpdateAccelerometer(x, y, z);
        }
    }

    /**
     * Unused Override Methods
     * Unneeded for this Lab
     */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
