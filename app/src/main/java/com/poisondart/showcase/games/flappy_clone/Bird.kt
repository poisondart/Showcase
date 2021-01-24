package com.poisondart.showcase.games.flappy_clone

import android.graphics.Rect
import kotlin.math.abs

class Bird(private val screenWidth: Int, private val screenHeight: Int) {
    val size = screenWidth / 8
    private var x = size
    private var y = screenHeight / 2 - size / 2

    private var speed = 10
    private var acceleration = 1

    companion object {
        private const val MAX_SPEED = 20
    }

    val hitBox = Rect(x, y, size, size)

    init {
        update()
    }

    fun move() {
        y += speed
        if (abs(speed) <= MAX_SPEED) {
            speed += acceleration
        }
    }

    fun isOut() = y > screenHeight

    fun reset() {
        x = size
        y = screenHeight / 2 - size / 2
        update()
    }

    fun jump() {
        speed = -MAX_SPEED
    }

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = hitBox.left + size
        hitBox.bottom = hitBox.top + size
    }
}