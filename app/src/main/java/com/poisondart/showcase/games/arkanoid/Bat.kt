package com.poisondart.showcase.games.arkanoid

import android.graphics.Rect

class Bat(private val screenWidth: Int, private val screenHeight: Int) {
    private val width = screenWidth / 6
    private val height = width / 8

    private var x = screenWidth / 2 - width / 2
    private var y = screenHeight - (height * 4)

    val hitBox = Rect(x, y, width, height)

    init {
        update()
    }

    fun move(xAcceleration: Float) {
        x -= (xAcceleration * 10).toInt()
        if (x > screenWidth - width) x = screenWidth - width
        if (x < 0) x = 0
    }


    fun reset() {
        x = screenWidth / 2 - width / 2
        y = screenHeight - (height * 4)
        update()
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + width
        hitBox.bottom = hitBox.top + height
    }
}