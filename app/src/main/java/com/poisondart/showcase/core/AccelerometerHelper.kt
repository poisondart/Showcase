package com.poisondart.showcase.core

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AccelerometerHelper(context: Context): SensorEventListener {

    private var sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private lateinit var accelerometer: Sensor

    var xAcceleration = 0f
    var yAcceleration = 0f
    var zAcceleration = 0f

    init {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        } else {
            Toast.makeText(context, "No accelerometer, sorry!", Toast.LENGTH_SHORT).show()
            (context as AppCompatActivity).finish()
        }
    }

    override fun onSensorChanged(p0: SensorEvent) {
        xAcceleration = p0.values[0]
        yAcceleration = p0.values[1]
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun registerListener() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }
}