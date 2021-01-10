package com.poisondart.showcase

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.SurfaceView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenWidth: Int, private val screenHeight: Int) : SurfaceView(context), Runnable, SensorEventListener {

    @Volatile
    private var playing = false
    private var gameThread: Thread? = null

    private val paint: Paint = Paint()
    private var canvas: Canvas? = null

    private val player = Player(screenWidth, screenHeight)
    private val barrierSpawner = BarriersLine(screenWidth, screenHeight, player.size)

    private var sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private lateinit var accelerometer: Sensor

    private var lastX = 0f
    private var lastY = 0f

    init {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        } else {
            Toast.makeText(context, "No accelerometer, sorry!", Toast.LENGTH_SHORT).show()
            (context as AppCompatActivity).finish()
        }
    }

    override fun run() {
        while (playing) {
            update()
            draw()
        }
    }

    override fun onSensorChanged(p0: SensorEvent) {
        lastX = p0.values[0]
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    private fun update() {
        barrierSpawner.update()
        player.move(lastX, lastY)
        player.update()
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas?.drawColor(Color.GREEN)

            paint.color = Color.BLACK
            barrierSpawner.barriers.forEach { b ->
                b.walls.forEach {
                    canvas?.drawRect(it.hitBox, paint)
                }
            }

            paint.color = Color.WHITE
            canvas?.drawRect(player.hitBox, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        playing = false
        sensorManager.unregisterListener(this)
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        gameThread = Thread(this)
        gameThread?.start()
    }
}