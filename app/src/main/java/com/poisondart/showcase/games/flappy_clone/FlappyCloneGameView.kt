package com.poisondart.showcase.games.flappy_clone

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import com.poisondart.showcase.core.GameView

@SuppressLint("ViewConstructor")
class FlappyCloneGameView(context: Context, screenWidth: Int, screenHeight: Int): GameView(context, screenWidth, screenHeight) {

    private val bird = Bird(screenWidth, screenHeight)

    override fun update() {
        if (!paused) {
            bird.move()
            bird.update()
            if (bird.isOut()) {
                reset()
            }
        }
    }

    private fun reset() {
        paused = true
        bird.reset()
    }

    override fun drawObjects() {
        canvas?.drawColor(Color.BLUE)

        paint.color = Color.YELLOW
        canvas?.drawRect(bird.hitBox, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (paused && event.action == MotionEvent.ACTION_UP) {
            paused = false
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            bird.jump()
        }

        return true
    }

    override fun pause() {
        paused = true
        super.pause()
    }
}