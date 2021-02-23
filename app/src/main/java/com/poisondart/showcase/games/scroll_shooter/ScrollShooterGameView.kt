package com.poisondart.showcase.games.scroll_shooter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.poisondart.showcase.core.AccelerometerHelper
import com.poisondart.showcase.core.GameView
import com.poisondart.showcase.core.HighScoreManager
import com.poisondart.showcase.core.SpriteSet

@SuppressLint("ViewConstructor")
class ScrollShooterGameView(context: Context, screenWidth: Int, screenHeight: Int) :
    GameView(context, screenWidth, screenHeight) {

    companion object {
        private const val MAX_SCORE = "scroll_shooter_max_score"
    }

    private val player = Player(screenWidth, screenHeight)
    private val chainEnemy = ChainEnemy(player.size, screenWidth, screenHeight)
    private val singleEnemy = SingleEnemy(player.size, screenWidth, screenHeight)
    private val archEnemy = ArchEnemy(screenWidth, screenHeight)
    private val background = Background(screenWidth, screenHeight, player.size)
    private val accelerometerHelper = AccelerometerHelper(context)
    private val highScoreManager = HighScoreManager(context)
    private val spriteSet = SpriteSet(context, "spriteset.png")
    private var reward = 0

    override fun update() {
        if (!paused) {
            player.move(
                accelerometerHelper.xAcceleration,
                accelerometerHelper.yAcceleration.toInt()
            )
            player.update()
            player.cannon.update()

            background.update()

            if (chainEnemy.intersectProjectile(player.cannon.projectiles)) {
                reward += 50
                if (chainEnemy.enemies.isEmpty()) chainEnemy.respawn()
            }

            if (chainEnemy.intersectPlayer(player.hitBox)) {
                restart()
            }

            if (singleEnemy.intersectPlayer(player.hitBox)) {
                restart()
            }

            if (archEnemy.intersectPlayer(player.hitBox)) {
                restart()
            }

            if (archEnemy.intersectProjectile(player.cannon.projectiles)) {
                reward += 75
                if (archEnemy.enemies.isEmpty()) archEnemy.respawn()
            }

            if (singleEnemy.intersectProjectiles(player.cannon.projectiles)) {
                if (singleEnemy.isDead()) {
                    reward += 100
                    singleEnemy.respawn()
                }
            }

            chainEnemy.move()
            chainEnemy.update()

            singleEnemy.move()
            singleEnemy.update()

            archEnemy.move()
            archEnemy.update()
        }
    }

    private fun restart() {
        highScoreManager.updateHighScore(MAX_SCORE, reward)
        chainEnemy.respawn()
        singleEnemy.respawn()
        player.respawn()
        archEnemy.respawn()
        paused = true
        reward = 0
    }

    override fun drawObjects() {
        canvas?.drawColor(Color.BLACK)


        canvas?.drawBitmap(spriteSet.test(0,0,16,16, player.hitBox), player.x.toFloat(), player.y.toFloat(), paint)

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

        paint.color = Color.BLUE
        canvas?.drawRect(singleEnemy.hitBox, paint)

        paint.color = Color.CYAN
        archEnemy.enemies.forEach {
            canvas?.drawRect(it.hitBox, paint)
        }

        val textScore = "Score: $reward Max Score: ${highScoreManager.getHighScore(MAX_SCORE)}"
        val textPaint = Paint()
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.color = Color.GREEN
        textPaint.textSize = 25.0f

        val xPos = player.size / 2
        val yPos = canvas!!.height - player.size
        canvas?.drawText(textScore, xPos.toFloat(), yPos.toFloat(), textPaint)
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