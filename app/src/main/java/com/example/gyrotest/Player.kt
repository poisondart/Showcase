package com.example.gyrotest

import android.graphics.Rect

class Player(screenWidth: Int, screenHeight: Int) {
    val size = screenWidth / 8
    val x = screenWidth / 2 - size / 2
    val y = screenHeight / 2 - size / 2
    val hitBox = Rect(x, y, size, size)

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}