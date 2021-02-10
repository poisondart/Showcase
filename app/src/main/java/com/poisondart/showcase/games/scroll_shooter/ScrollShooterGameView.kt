package com.poisondart.showcase.games.scroll_shooter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import com.poisondart.showcase.core.AccelerometerHelper
import com.poisondart.showcase.core.GameView

@SuppressLint("ViewConstructor")
class ScrollShooterGameView(context: Context, screenWidth: Int, screenHeight: Int) :
    GameView(context, screenWidth, screenHeight) {

    private val player = Player(screenWidth, screenHeight)
    private val chainEnemy = ChainEnemy(player.hitBox.width(), screenWidth, screenHeight)
    private val background = Background(screenWidth, screenHeight, player.hitBox.width())
    private val accelerometerHelper = AccelerometerHelper(context)

    override fun update() {
        if (!paused) {
            player.move(
                accelerometerHelper.xAcceleration,
                accelerometerHelper.yAcceleration.toInt()
            )
            player.update()
            player.cannon.update()

            background.update()

            chainEnemy.move()
            chainEnemy.update()
        }
    }

    override fun drawObjects() {
        canvas?.drawColor(Color.BLACK)

        paint.color = Color.WHITE
        canvas?.drawRect(player.hitBox, paint)

        paint.color = Color.YELLOW
        player.cannon.projectiles.forEach {
            if (it.isShootOut) {
                canvas?.drawRect(it.hitBox, paint)
            }
        }
        paint.color = Color.WHITE
        background.stars.forEach {
            canvas?.drawRect(
                it.x.toFloat(),
                it.y.toFloat(),
                (it.x + 4).toFloat(),
                (it.y + 4).toFloat(),
                paint
            )
        }
        paint.color = Color.RED
        chainEnemy.enemies.forEach {
            canvas?.drawRect(it.hitBox, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (paused && event.action == MotionEvent.ACTION_UP) {
            paused = false
        }
        if (!paused && event.action == MotionEvent.ACTION_DOWN) {
            player.shot()
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