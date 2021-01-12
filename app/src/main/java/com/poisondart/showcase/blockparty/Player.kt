package com.poisondart.showcase.blockparty

import android.graphics.Rect
import kotlin.math.abs

class Player(private val screenWidth: Int, private val screenHeight: Int) {
    val size = screenWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight / 2 - size / 2
    val hitBox = Rect(x, y, size, size)

    fun move(lastX: Float, lastY: Float) {
        if (abs(lastX) > 0.4) {
            if (lastX < 0) {
                x += (size / 4)
                if (x > screenWidth - hitBox.width()) x = screenWidth - hitBox.width()
            } else {
                x -= (size / 4)
                if (x < 0) x = 0
            }
        }
        /*if (abs(lastY) > 0.8) {
            if (lastY < 0) {
                y += 15
                if (y > screenHeight - hitBox.height()) y = screenHeight - hitBox.height()
            } else {
                y -= 15
                if (y < 0) y = 0
            }
        }*/
    }

    fun pushDownByWall() {
        y += (size / 8)
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}