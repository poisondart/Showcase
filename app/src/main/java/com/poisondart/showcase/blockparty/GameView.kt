package com.poisondart.showcase.blockparty

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenWidth: Int, private val screenHeight: Int) : SurfaceView(context), Runnable {

    @Volatile
    private var playing = false
    private var paused = true
    private var gameThread: Thread? = null

    private val paint: Paint = Paint()
    private var canvas: Canvas? = null

    private val player = Player(screenWidth, screenHeight)
    private val barriersLine = BarriersLine(screenWidth, screenHeight, player.size)

    private val accelerometerHelper = AccelerometerHelper(context)

    override fun run() {
        while (playing) {
            update()
            draw()
        }
    }

    private fun update() {
        if (!paused) {
            barriersLine.update()
            player.move(accelerometerHelper.lastX, accelerometerHelper.lastY)
            if (barriersLine.intersect(player.hitBox)) {
                player.pushDownByWall()
            }
        }
        player.update()
    }

    private fun draw() {
        if (holder.surface.isValid) {

            canvas = holder.lockCanvas()

            canvas?.drawColor(Color.GREEN)

            paint.color = Color.BLACK
            barriersLine.barriers.forEach { b ->
                b.walls.forEach {
                    canvas?.drawRect(it.hitBox, paint)
                }
            }

            paint.color = Color.WHITE
            canvas?.drawRect(player.hitBox, paint)

            paint.textSize = player.size.toFloat()
            canvas?.drawText(barriersLine.getWallsPassed(), (player.size / 4).toFloat(), (player.size).toFloat(), paint)

            if (paused) {
                val textPaint = Paint()
                textPaint.color = Color.WHITE
                textPaint.textSize = (player.size / 4).toFloat()
                textPaint.textAlign = Paint.Align.CENTER
                val pauseText = "Используйте гироскоп, чтобы преодолевать преграды."
                val xPos = screenWidth / 2
                val yPos = (screenWidth / 2 - (textPaint.descent() + textPaint.ascent()) / 2)
                canvas?.drawText(pauseText, xPos.toFloat(), yPos, textPaint)
            }

            holder.unlockCanvasAndPost(canvas)
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