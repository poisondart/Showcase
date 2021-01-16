package com.poisondart.showcase.arkanoid

import android.graphics.Rect

class Projectile(private val screenWidth: Int, private val screenHeight: Int, playerWidth: Int) {
    private val size = playerWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight / 2 - size / 2
    private var xSpeed = 0
    private var ySpeed = playerWidth / 16

    val hitBox = Rect(x, y, size, size)

    init {
        update()
    }

    fun move(playerHitBox: Rect, playerAcceleration: Float) {
        if (hitBox.intersect(playerHitBox)) {
            ySpeed = -ySpeed
            xSpeed -= (playerAcceleration * 10).toInt()
        }

        x += xSpeed
        y += ySpeed

        if (x > screenWidth - size) {
            x = screenWidth - size
            xSpeed = -xSpeed
        }

        if (x < 0) {
            x = 0
            xSpeed = -xSpeed
        }

        if (y < 0) {
            y = 0
            ySpeed = -ySpeed
        }
    }

    fun isOut() = y > screenHeight

    fun reset() {
        xSpeed = 0
        x = screenWidth / 2 - size / 2
        y = screenHeight / 2 - size / 2
        update()
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}