package com.chopsy.roadfighter.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.chopsy.roadfighter.R;
import com.chopsy.roadfighter.view.PlayerCarView;

public class CarController implements SensorEventListener {

    private PlayerCarView mPlayerCarView;
    private SensorManager mSensorManager;

    public CarController(RoadFighterMain gameController) {
        mPlayerCarView = (PlayerCarView) gameController.findViewById(R.id.car);
        mSensorManager = (SensorManager) gameController.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (Math.abs(event.values[0]) < 1) {
                return;
            }
            boolean turnLeft = event.values[0] > 0;
            mPlayerCarView.updateView(turnLeft);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSensorManager() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopSensorManager() {
        mSensorManager.unregisterListener(this);
    }
}
