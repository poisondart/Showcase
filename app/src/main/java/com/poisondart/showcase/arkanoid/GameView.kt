package com.poisondart.showcase.arkanoid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenWidth: Int, screenHeight: Int) : SurfaceView(context), Runnable  {

    @Volatile
    private var playing = false
    private var paused = true

    private var gameThread: Thread? = null

    private val paint: Paint = Paint()
    private var canvas: Canvas? = null

    private val bat = Bat(screenWidth, screenHeight)

    private val accelerometerHelper = AccelerometerHelper(context)

    override fun run() {
        while (playing) {
            draw()
            update()
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas?.drawColor(Color.BLUE)

            paint.color = Color.YELLOW
            canvas?.drawRect(bat.hitBox, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun update() {
        if (!paused) {
            bat.move(accelerometerHelper.xAcceleration)
            bat.update()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (paused && event.action == MotionEvent.ACTION_DOWN) {
            paused = false
        }
        return true
    }

    fun pause() {
        playing = false
        paused = true
        accelerometerHelper.unregisterListener()
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        accelerometerHelper.registerListener()
        gameThread = Thread(this)
        gameThread?.start()
    }
}