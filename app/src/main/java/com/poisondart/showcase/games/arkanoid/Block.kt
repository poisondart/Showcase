package com.poisondart.showcase.games.arkanoid

import android.graphics.Rect

class Block(width: Int, height: Int, x: Int, y: Int) {

    val hitBox = Rect(x, y, width, height)

    init {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = x + width
        hitBox.bottom = y + height
    }
}