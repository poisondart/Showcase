package com.poisondart.showcase.games.blockparty

import android.graphics.Rect

class Wall(val width: Int, val height: Int, var x: Int = 0, var y: Int = 0) {

    val hitBox = Rect(x, y, width, height)

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + width
        hitBox.bottom = hitBox.top + height
    }

}