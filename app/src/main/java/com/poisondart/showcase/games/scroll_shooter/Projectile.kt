package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect

class Projectile(
    playerSize: Int
) {
    private val size = playerSize / 8
    private var x = -size
    private var y = -size

    private val speed = size * 3

    var isShootOut = false

    val hitBox = Rect(x, y, size, size)

    fun fire(x: Int, y: Int) {
        isShootOut = true
        this.x = x
        this.y = y
    }

    fun move() {
        if (isShootOut) {
            y -= speed
            if (y < -size) {
                isShootOut = false
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