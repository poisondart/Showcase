package com.poisondart.showcase.arkanoid

import android.graphics.Rect

class Bat(private val screenWidth: Int, screenHeight: Int) {
    private val width = screenWidth / 8
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

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + width
        hitBox.bottom = hitBox.top + height
    }
}