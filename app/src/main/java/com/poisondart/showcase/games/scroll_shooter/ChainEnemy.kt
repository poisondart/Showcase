package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect
import kotlin.math.abs

class ChainEnemy(playerSize: Int, private val screenWidth: Int, private val screenHeight: Int) {

    val enemies = mutableListOf<Enemy>()
    private var verticalSpeed = 10
    var horizontalSpeed = 10
    private var x = 0

    init {
        for (i in 0..10) {
            enemies.add(Enemy(playerSize, verticalSpeed, x, 500))
            verticalSpeed *= (-1)
            x += playerSize / 2
        }
    }

    fun update() {
        enemies.forEach { it.update() }
    }

    fun move() {
        enemies.forEach {
            it.move(horizontalSpeed)
        }

        if ((horizontalSpeed > 0 && enemies.first().x > screenWidth) || (horizontalSpeed < 0 && enemies.last().x < -enemies.last().size)) {
            horizontalSpeed *= (-1)
        }
    }

    inner class Enemy(playerSize: Int, private var verticalSpeed: Int, var x: Int, var y: Int) {
        val size = playerSize / 2
        private var moveRange = size
        val hitBox = Rect(x, y, x + size, y + size)

        fun update() {
            hitBox.left = x
            hitBox.top = y
            hitBox.right = hitBox.left + size
            hitBox.bottom = hitBox.top + size
        }

        fun move(horizontalSpeed: Int) {
            y += verticalSpeed
            x += horizontalSpeed
            moveRange += abs(verticalSpeed)
            if (moveRange >= size * 2) {
                verticalSpeed *= (-1)
                moveRange = 0
            }
        }
    }
}