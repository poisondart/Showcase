package com.example.gyrotest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, private val screenWidth: Int, private val screenHeight: Int) : SurfaceView(context), Runnable {

    @Volatile
    private var playing = false
    private var gameThread: Thread? = null

    private val paint: Paint = Paint()
    private var canvas: Canvas? = null

    private val player = Player(screenWidth, screenHeight)
    private val barrierSpawner = BarriersLine(screenWidth, screenHeight, player.size)

    override fun run() {
        while (playing) {
            update()
            draw()
        }
    }

    private fun update() {
        barrierSpawner.update()
        player.update()
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas?.drawColor(Color.argb(255, 196, 240, 194))

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
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }
}