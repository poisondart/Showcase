package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect
import kotlin.math.abs

class Player(private val screenWidth: Int, private val screenHeight: Int) {
    private val size = screenWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight - (size * 4)

    val hitBox = Rect(x, y, size, size)

    val cannon = Cannon(size)

    init {
        update()
    }

    fun move(xAcceleration: Float, yAcceleration: Int) {
        x -= (xAcceleration * 10).toInt()
        if (yAcceleration in 0..5) {
            val ac = 5 - yAcceleration
            y -= ac * 10
        }
        if (yAcceleration in 5..10) {
            val ac = abs(5 - yAcceleration)
            y += ac * 10
        }
        if (x > screenWidth - size) x = screenWidth - size
        if (x < 0) x = 0

        if (y < 0) y = 0
        if (y > screenHeight - size) y = screenHeight - size
    }

    fun shot() {
        cannon.shot(x + (size / 2), y)
    }

    fun respawn() {
        x = screenWidth / 2 - size / 2
        y = screenHeight - (size * 4)
        update()
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}