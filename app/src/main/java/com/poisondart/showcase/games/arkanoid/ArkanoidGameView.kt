package com.poisondart.showcase.games.arkanoid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import com.poisondart.showcase.core.AccelerometerHelper
import com.poisondart.showcase.core.GameView

@SuppressLint("ViewConstructor")
class ArkanoidGameView(context: Context, screenWidth: Int, screenHeight: Int) : GameView(context, screenWidth, screenHeight) {

    private val bat = Bat(screenWidth, screenHeight)
    private val projectile = Projectile(screenWidth, screenHeight, screenWidth / 8)

    private val accelerometerHelper = AccelerometerHelper(context)

    private val blockWall = BlockWall(screenWidth)

    override fun drawObjects() {
        canvas?.drawColor(Color.BLACK)

        paint.color = Color.YELLOW
        canvas?.drawRect(bat.hitBox, paint)

        paint.color = Color.RED
        canvas?.drawRect(projectile.hitBox, paint)

        paint.color = Color.BLUE
        blockWall.blocks.forEach {
            canvas?.drawRect(it.hitBox, paint)
        }
    }

    override fun update() {
        if (!paused) {
            bat.move(accelerometerHelper.xAcceleration)
            bat.update()
            if (blockWall.intersect(projectile.hitBox)) {
                if (blockWall.blocks.isEmpty()) {
                    resetGame()
                    return
                }
                projectile.reflect()
            }
            projectile.move(bat.hitBox, accelerometerHelper.xAcceleration)
            projectile.update()

            if (projectile.isOut()) {
                resetGame()
            }
        }
    }

    private fun resetGame() {
        paused = true
        projectile.reset()
        bat.reset()
        blockWall.reset()
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