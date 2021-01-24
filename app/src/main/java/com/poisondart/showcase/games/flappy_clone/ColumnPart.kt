package com.poisondart.showcase.games.flappy_clone

import android.graphics.Rect

class ColumnPart(
        private val width: Int,
        private val height: Int,
        var x: Int,
        private val y: Int
) {

    val hitBox = Rect(x, y, width, height)

    init {
        update()
    }

    fun move(x: Int) {
        this.x = x
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + width
        hitBox.bottom = hitBox.top + height
    }
}