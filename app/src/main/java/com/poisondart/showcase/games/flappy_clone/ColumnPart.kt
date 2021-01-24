package com.poisondart.showcase.games.flappy_clone

import android.graphics.Rect

class ColumnPart(
        private val screenWidth: Int,
        private val screenHeight: Int,
        private val width: Int,
        private val height: Int,
        private var x: Int,
        private val y: Int
) {

    val hitBox = Rect(x, y, width, height)

    init {
        update()
    }

    fun move() {
        x -= width / 8
        if (x < 0 - width) x = screenWidth
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + width
        hitBox.bottom = hitBox.top + height
    }
}