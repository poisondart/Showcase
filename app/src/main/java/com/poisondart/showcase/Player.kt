package com.poisondart.showcase

import android.graphics.Rect
import kotlin.math.abs

class Player(private val screenWidth: Int, screenHeight: Int) {
    val size = screenWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight / 2 - size / 2
    val hitBox = Rect(x, y, size, size)

    fun move(lastX: Float, lastY: Float) {
        if (abs(lastX) > 1.5) {
            if (lastX < 0) {
                x += 15
                if (x > screenWidth - hitBox.width() / 2) x = 0 - hitBox.width()
            } else {
                x -= 15
                if (x < 0 - hitBox.width() / 2) x = screenWidth + hitBox.width()
            }
        }
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}