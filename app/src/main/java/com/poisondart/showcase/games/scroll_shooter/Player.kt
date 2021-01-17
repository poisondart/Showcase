package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect

class Player(private val screenWidth: Int, screenHeight: Int) {
    private val size = screenWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight - (size * 4)

    val hitBox = Rect(x, y, size, size)

    val cannon = Cannon(size)

    init {
        update()
    }

    fun move(xAcceleration: Float) {
        x -= (xAcceleration * 10).toInt()
        if (x > screenWidth - size) x = screenWidth - size
        if (x < 0) x = 0
    }

    fun shot() {
        cannon.shot(x + (size / 2), y)
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}