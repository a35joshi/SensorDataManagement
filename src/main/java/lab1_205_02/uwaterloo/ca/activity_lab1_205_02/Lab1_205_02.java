package lab1_205_02.uwaterloo.ca.activity_lab1_205_02;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import java.util.Arrays;
import ca.uwaterloo.sensortoy.LineGraphView;


public class Lab1_205_02 extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer, magneticfield, rotation, light;
    private double AccXMax = 0;
    private double AccYMax = 0;
    private double AccZMax = 0;
    private double deltaX = 0;
    private double deltaY = 0;
    private double deltaZ = 0;
    private double deltaMagX = 0;
    private double deltaMagY = 0;
    private double deltaMagZ = 0;
    private double deltaMagMaxX = 0;
    private double deltaMagMaxY = 0;
    private double deltaMagMaxZ = 0;
    private double rotationalX = 0;
    private double rotationalY = 0;
    private double rotationalZ = 0;
    private double maxRotX = 0;
    private double maxRotY = 0;
    private double maxRotZ = 0;
    private double lights = 0;
    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;
    private TextView RotationValueX, RotationValueY, RotationValueZ, MaxRotX, MaxRotY, MaxRotZ, LightValue;
    private TextView CurrentMagX, CurrentMagY, CurrentMagZ, MagMaxX, MagMaxY, MagMaxZ;
    public void onSensorChanged(SensorEvent event) {


        // Get the change of the x,y,z values of the accelerometer.
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // clean current values, Set it as empty.
            displayCleanValues();
            // Display the current x,y,z values for the accelerometer.
            displayCurrentValues();
            deltaX = event.values[0];
            deltaY = event.values[1];
            deltaZ = event.values[2];
            displayMaxValues();
            float[] pointstoplot={((float) deltaX),(float) deltaY, ((float) deltaZ)};
            graph.addPoint(pointstoplot);
        }
        // Get the change of the x,y,z values of the mag field.
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            displayCleanValues();
            displayCurrentValues();
            deltaMagX = event.values[0];
            deltaMagY = event.values[1];
            deltaMagZ = event.values[2];
            displayMaxValues();
        }
        // Get the change of the x,y,z values of the Rotation.
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            displayCleanValues();
            displayCurrentValues();
            rotationalX = event.values[0];
            rotationalY = event.values[1];
            rotationalZ = event.values[2];
            displayMaxValues();
        }
        // Get the change of the x,y,z values of the light.
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            displayCleanValues();
            displayCurrentValues();
            lights = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    // initialize the graphic.
    LineGraphView graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        // initialize the graph view.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Declare sensor object for
        Sensor[] sensor = new Sensor[]{sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),// Sensor- Accelerometer
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),// Sensor- Magnetic
                sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),// Sensor- Vector
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)};// Sensor- Light
        //region for 4 sensor
        if (sensor != null) {
            // success!
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            magneticfield = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, magneticfield, SensorManager.SENSOR_DELAY_NORMAL);
            rotation = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL);
            light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            return;
        }
        //region end

        //Set the graph.
        LinearLayout layout = ((LinearLayout) findViewById(R.id.layout));
        graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("X", "Y", "Z") );
        layout.addView(graph);
        graph.setVisibility(View.VISIBLE);
    }
    //Field to showing the result
    public void initializeViews() {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);
        maxX = (TextView) findViewById(R.id.maxX);
        maxY = (TextView) findViewById(R.id.maxY);
        maxZ = (TextView) findViewById(R.id.maxZ);
        CurrentMagX = (TextView) findViewById(R.id.CurrentMagX);
        CurrentMagY = (TextView) findViewById(R.id.CurrentMagY);
        CurrentMagZ = (TextView) findViewById(R.id.CurrentMagZ);
        MagMaxX = (TextView) findViewById(R.id.MagMaxX);
        MagMaxY = (TextView) findViewById(R.id.MagMaxY);
        MagMaxZ = (TextView) findViewById(R.id.MagMaxZ);
        RotationValueX = (TextView) findViewById(R.id.RotationValueX);
        RotationValueY = (TextView) findViewById(R.id.RotationValueY);
        RotationValueZ = (TextView) findViewById(R.id.RotationValueZ);
        LightValue = (TextView) findViewById(R.id.LightValue);
        MaxRotX = (TextView) findViewById(R.id.MaxRotX);
        MaxRotY = (TextView) findViewById(R.id.MaxRotY);
        MaxRotZ = (TextView) findViewById(R.id.MaxRotZ);

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magneticfield, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, rotation, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    //Field to clean the result.
    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
        CurrentMagX.setText("0.0");
        CurrentMagY.setText("0.0");
        CurrentMagZ.setText("0.0");
        RotationValueX.setText(("0.0"));
        RotationValueY.setText(("0.0"));
        RotationValueZ.setText(("0.0"));
        LightValue.setText(("0.0"));
    }

    // Field to display the current x,y,z accelerometer values, convert double value to string.
    public void displayCurrentValues() {
        currentX.setText(Double.toString(deltaX));
        currentY.setText(Double.toString(deltaY));
        currentZ.setText(Double.toString(deltaZ));
        CurrentMagX.setText(Double.toString((deltaMagX)));
        CurrentMagY.setText((Double.toString(deltaMagY)));
        CurrentMagZ.setText((Double.toString(deltaMagZ)));
        RotationValueX.setText(Double.toString(rotationalX));
        RotationValueY.setText(Double.toString(rotationalY));
        RotationValueZ.setText(Double.toString(rotationalZ));
        LightValue.setText(Double.toString(lights));
    }


    public void displayMaxValues()
    // Field to display the max x,y,z accelerometer values
    {
        if (deltaX > AccXMax) {
            AccXMax = deltaX;
            maxX.setText(Double.toString(AccXMax));
        }
        if (deltaY > AccYMax) {
            AccYMax = deltaY;
            maxY.setText(Double.toString(AccYMax));
        }
        if (deltaZ > AccZMax) {
            AccZMax = deltaZ;
            maxZ.setText(Double.toString(AccZMax));
        }
        // Field to display the max x,y,z rotational values
        if (Math.abs(rotationalX) > Math.abs(maxRotX)) {
            maxRotX = rotationalX;
            MaxRotX.setText(Double.toString(maxRotX));
        }
        if (Math.abs(rotationalY) > Math.abs(maxRotY)) {
            maxRotY = rotationalY;
            MaxRotY.setText(Double.toString(maxRotY));
        }
        if (Math.abs(rotationalZ) > Math.abs(maxRotZ)) {
            maxRotZ = rotationalZ;
            MaxRotZ.setText(Double.toString(maxRotZ));
        }
        // Field to display the max x,y,z mag field values
        if (deltaMagX > deltaMagMaxX) {
            deltaMagMaxX = deltaMagX;
            MagMaxX.setText(Double.toString(deltaMagMaxX));
        }
        if (deltaMagY > deltaMagMaxY) {
            deltaMagMaxY = deltaMagY;
            MagMaxY.setText(Double.toString(deltaMagMaxY));
        }
        if (deltaMagZ > deltaMagMaxZ) {
            deltaMagMaxZ = deltaMagZ;
            MagMaxZ.setText(Double.toString(deltaMagMaxZ));
        }
    }

}
