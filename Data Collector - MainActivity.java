This code handles real-time data collection, UI updates, sensor integration, and data storage with proper permission checks and theme adjustments.


// imports
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // variable declarations
    private SensorManager sensorManagers;
    private SensorManager sensorManagerG;
    private Sensor senAccelerometor;
    private Sensor senGyroscope;
    private TextView GPSx;
    private TextView GPSy;
    private TextView GPS_loc;
    private FusedLocationProviderClient MyFusedLocationClient;
    private long lastUpdate = 0;
    private long lastUpdate_gyro = 0;
    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    public int i = 0;
    public int c = 0;
    public String fileString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // location updates
        MyFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sensorManagers = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManagerG = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManagers != null;
        
        // Set up aclmtr and gyroscope sensors
        senAccelerometor = sensorManagers.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyroscope = sensorManagerG.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // asking permissions for location and storage access
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, locationRequestCode);
        } else {
            Toast T = Toast.makeText(getApplicationContext(), "Location & file access Permission Granted", Toast.LENGTH_SHORT);
            T.show();
        }

        ChangeTheme();
        
        // Theme toggle switch handler
        Switch theme = (Switch) findViewById(R.id.sw_theme);
        theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                c = 1;
                ChangeTheme();
            } else {
                c = 0;
                ChangeTheme();
            }
        });

        // Data collection toggle switch handler
        Switch toggle = (Switch) findViewById(R.id.sw);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                i = 1;
                onResume();
            } else {
                i = 0;
                onPause();
            }
        });
    }

    // writing sensor data and location data to a CSV file
    public void FileWriters(String str){
        SimpleDateFormat dateObj = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateObj.format(calendar.getTime());

        // Set file path and check for existence, creating if needed
        File path = new File(Objects.requireNonNull(this.getExternalFilesDir(null)).getAbsolutePath() + "/Collected_data");

        if (!path.exists()){
            path.mkdirs();
            TextView txt = (TextView) findViewById(R.id.city);
            txt.setText(date);
        }

        final File file = new File(path, "Collected_data.csv");

        try {
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file, true);
                fOut.write(" AX, AY, AZ, GPS_Lat, GPS_Long, GX, GY, GZ\n".getBytes());
                fOut.close();
            }
            
            // Append sensor data to the file
            FileOutputStream fOut = new FileOutputStream(file, true);
            fOut.write(str.getBytes());
            fOut.flush();
            fOut.close();

        } catch (IOException e){
            Log.e("Exception", "File write failed");
        }

        fileString = "";
    }

    // Unregister sensors when the activity is paused
    protected void onPause(){
        super.onPause();
        sensorManagers.unregisterListener(this);
    }

    // Register sensors when activity is resumed
    protected void onResume(){
        super.onResume();
        if(i == 1) {
            sensorManagers.registerListener(this, senAccelerometor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManagers.registerListener(this, senGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // gets last known location and update UI with location data
    public void GetNewLocation(){
        MyFusedLocationClient.flushLocations();
        MyFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if(location != null){
                wayLatitude = location.getLatitude();
                wayLongitude = location.getLongitude();

                // Update UI with GPS coordinates
                GPSx = (TextView) findViewById(R.id.gpsx);
                GPSx.setText("" + wayLatitude);

                GPSy = (TextView) findViewById(R.id.gpsy);
                GPSy.setText("" + wayLongitude);

                fileString += wayLatitude + ", " + wayLongitude + ", ";

                GPS_loc = (TextView) findViewById(R.id.city);
                GPS_loc.setText(String.format(Locale.US, "%s -- %s", wayLatitude, wayLongitude));
            }
        });
    }

    // Callback for sensor events to handle aclmtr and gyroscope data
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;
        Sensor GSensor = sensorEvent.sensor;

        // Gyroscope sensor data handling
        if (GSensor.getType() == Sensor.TYPE_GYROSCOPE){
            float gx = sensorEvent.values[0];
            float gy = sensorEvent.values[1];
            float gz = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUpdate_gyro > 300)) {
                lastUpdate_gyro = currentTime;
                GetNewLocation();  // Update location for each gyroscope event

                // Update UI with gyroscope data
                String sX = Float.toString(gx);
                TextView text = (TextView) findViewById(R.id.gx);
                text.setText(sX);

                String sY = Float.toString(gy);
                text = (TextView) findViewById(R.id.gy);
                text.setText(sY);

                String sZ = Float.toString(gz);
                text = (TextView) findViewById(R.id.gz);
                text.setText(sZ);

                fileString += sX + ", " + sY + ", " + sZ + ", ";
            }
        }

        // Aclmtr sensor data handling
        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastUpdate) > 300){
                lastUpdate = currentTime;

                // Update UI and progress bar with aclmtr data
                String sX = Float.toString(x);
                TextView text = (TextView) findViewById(R.id.ax);
                text.setText(sX);

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                int progress = (int) (((-1 * x) + 10) * 10000);
                progressBar.setProgress(progress);

                String sY = Float.toString(y);
                text = (TextView) findViewById(R.id.ay);
                text.setText(sY);

                String sZ = Float.toString(z);
                text = (TextView) findViewById(R.id.az);
                text.setText(sZ);

                // Append data to fileString and write to file
                fileString += sX + ", " + sY + ", " + sZ + "\n";
                FileWriters(fileString);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    // Function to toggle the application's theme
    private void ChangeTheme(){
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.consl);
        CardView cardView = (CardView) findViewById(R.id.cardView);

        int backgroundColor;
        if(c == 1){
            backgroundColor = ContextCompat.getColor(this, R.color.black);
        } else {
            backgroundColor = ContextCompat.getColor(this, R.color.white);
        }

        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(constraintLayout, "backgroundColor", backgroundColor);
        colorAnimator.setDuration(2000);
        colorAnimator.setInterpolator(new LinearInterpolator());
        colorAnimator.start();
    }
}
