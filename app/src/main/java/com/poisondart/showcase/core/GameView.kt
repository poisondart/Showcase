package com.poisondart.showcase.core

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceView

abstract class GameView(context: Context, private val screenWidth: Int, screenHeight: Int) : SurfaceView(context), Runnable  {

    @Volatile
    private var playing = false
    protected var paused = true

    protected val paint: Paint = Paint()
    protected var canvas: Canvas? = null

    private var gameThread: Thread? = null

    override fun run() {
        while (playing) {
            update()
            draw()
        }
    }

    abstract fun update()
    abstract fun drawObjects()

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            drawObjects()
            holder.unlockCanvasAndPost(canvas)
        }
    }

    open fun pause() {
        playing = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    open fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread?.start()
    }
}