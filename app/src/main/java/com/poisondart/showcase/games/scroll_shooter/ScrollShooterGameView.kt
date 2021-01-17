package com.poisondart.showcase.games.scroll_shooter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import com.poisondart.showcase.core.AccelerometerHelper
import com.poisondart.showcase.core.GameView

@SuppressLint("ViewConstructor")
class ScrollShooterGameView(context: Context, screenWidth: Int, screenHeight: Int): GameView(context, screenWidth, screenHeight) {

    private val player = Player(screenWidth, screenHeight)
    private val accelerometerHelper = AccelerometerHelper(context)

    override fun update() {
        if (!paused) {
            player.move(accelerometerHelper.xAcceleration)
            player.update()
        }
    }

    override fun drawObjects() {
        canvas?.drawColor(Color.BLACK)

        paint.color = Color.WHITE
        canvas?.drawRect(player.hitBox, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (paused && event.action == MotionEvent.ACTION_DOWN) {
            paused = false
        }
        return true
    }

    override fun pause() {
        paused = true
        accelerometerHelper.unregisterListener()
        super.pause()
    }

    override fun resume() {
        accelerometerHelper.registerListener()
        super.resume()
    }
}