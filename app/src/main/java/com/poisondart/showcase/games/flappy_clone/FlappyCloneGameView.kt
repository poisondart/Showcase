package com.poisondart.showcase.games.flappy_clone

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.poisondart.showcase.core.GameView
import com.poisondart.showcase.core.HighScoreManager

@SuppressLint("ViewConstructor")
class FlappyCloneGameView(context: Context, screenWidth: Int, screenHeight: Int) : GameView(context, screenWidth, screenHeight) {

    companion object {
        private const val MAX_SCORE = "flappy_max_score"
    }

    private val bird = Bird(screenWidth, screenHeight)
    private val column = Column(screenWidth, screenHeight, bird.size)

    private val highScoreManager = HighScoreManager(context)

    override fun update() {
        if (!paused) {
            bird.move()
            bird.update()
            column.move()
            column.update()

            if (column.intersect(bird.hitBox)) {
                reset()
            }

            if (bird.isOut()) {
                reset()
            }
        }
    }

    private fun reset() {
        paused = true
        highScoreManager.updateHighScore(MAX_SCORE, column.passedColumnCount)
        column.passedColumnCount = 0
        bird.reset()
        column.reset()
    }

    override fun drawObjects() {
        canvas?.drawColor(Color.BLUE)

        paint.color = Color.YELLOW
        canvas?.drawRect(bird.hitBox, paint)

        paint.color = Color.GREEN
        column.parts.forEach {
            canvas?.drawRect(it.hitBox, paint)
        }

        val textScore = "${column.passedColumnCount}/${highScoreManager.getHighScore(MAX_SCORE)}"
        val textPaint = Paint()
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.WHITE
        textPaint.textSize = 100.0f

        val xPos = canvas!!.width / 2
        //val yPos = (canvas!!.height / 2 - (textPaint.descent() + textPaint.ascent()) / 2).toInt()
        val yPos = canvas!!.height - bird.size
        canvas?.drawText(textScore, xPos.toFloat(), yPos.toFloat(), textPaint)
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