package com.poisondart.showcase.blockparty

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceView

@SuppressLint("ViewConstructor")
class GameView(context: Context, screenWidth: Int, screenHeight: Int) : SurfaceView(context), Runnable {

    @Volatile
    private var playing = false
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
        barriersLine.update()
        player.move(accelerometerHelper.lastX, accelerometerHelper.lastY)
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

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        playing = false
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