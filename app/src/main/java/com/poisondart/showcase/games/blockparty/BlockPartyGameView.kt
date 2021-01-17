package com.poisondart.showcase.games.blockparty

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.poisondart.showcase.core.AccelerometerHelper
import com.poisondart.showcase.core.GameView

@SuppressLint("ViewConstructor")
class BlockPartyGameView(context: Context, private val screenWidth: Int, screenHeight: Int) : GameView(context, screenWidth, screenHeight) {

    private val player = Player(screenWidth, screenHeight)
    private val barriersLine = BarriersLine(screenWidth, screenHeight, player.size)

    private val accelerometerHelper = AccelerometerHelper(context)
    private val highScoreManager = HighScoreManager(context)

    override fun update() {
        if (!paused) {
            barriersLine.update()
            val eke = barriersLine.intersect(player.body)
            /*player.move(accelerometerHelper.xAcceleration,
                    accelerometerHelper.yAcceleration,
                    barriersLine.intersect(player.hitBoxLeft),
                    barriersLine.intersect(player.hitBoxRight),
                    barriersLine.intersect(player.hitBoxTop))*/
            player.move(accelerometerHelper.xAcceleration, eke[0], eke[1])
        }
        player.update()

        if (player.isOut()) {
            highScoreManager.checkNewScore(barriersLine.wallsPassed)
            player.respawn()
            barriersLine.reset()
            paused = true
        }
    }

    override fun drawObjects() {

        canvas?.drawColor(Color.GREEN)

        paint.color = Color.BLACK
        barriersLine.barriers.forEach { b ->
            b.walls.forEach {
                canvas?.drawRect(it.hitBox, paint)
            }
        }

        paint.color = Color.WHITE
        canvas?.drawRect(player.body, paint)

        ///
        paint.color = Color.RED
        canvas?.drawRect(player.hitBoxLeft, paint)
        canvas?.drawRect(player.hitBoxRight, paint)
        canvas?.drawRect(player.hitBoxTop, paint)
        ///

        paint.color = Color.WHITE
        paint.textSize = player.size.toFloat()
        canvas?.drawText("${barriersLine.getWallsPassedString()}/${highScoreManager.getHighScore()}", (player.size / 4).toFloat(), (player.size).toFloat(), paint)

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