package com.poisondart.showcase.blockparty

import android.graphics.Rect
import kotlin.math.abs

class Player(private val screenWidth: Int, private val screenHeight: Int) {
    val size = screenWidth / 8
    private var x = screenWidth / 2 - size / 2
    private var y = screenHeight / 2 - size / 2
    val body = Rect(x, y, size, size)
    val hitBoxLeft = Rect(x, y, 1, size)
    val hitBoxRight = Rect(x + size - 1, y, 1, size)
    val hitBoxTop = Rect(x + 1, y, size - 1, 1)

    fun move(xAcceleration: Float, yAcceleration: Float, isPushedLeft: Boolean, isPushedRight: Boolean, isPushedTop: Boolean) {

        if (xAcceleration < 0) {
            if (!isPushedRight) x += (abs(xAcceleration) * 10).toInt()
            if (x > screenWidth - body.width()) x = screenWidth - body.width()
        } else {
            if (!isPushedLeft) x -= (abs(xAcceleration) * 10).toInt()
            if (x < 0) x = 0
        }

        if (isPushedTop) pushDownByWall()
        //if (isPushed) {
        //    pushDownByWall()
        //} else {
        /*if (abs(lastY) > 0.8) {
            if (lastY > 0) {
                y += 15
                if (y > screenHeight - hitBox.height()) y = screenHeight - hitBox.height()
            } else {
                y -= 15
                if (y < 0) y = 0
            }
        }*/
        //}
    }

    private fun pushDownByWall() {
        y += (size / 8)
    }

    fun isOut() = y > screenHeight

    fun respawn() {
        x = screenWidth / 2 - size / 2
        y = screenHeight / 2 - size / 2
        update()
    }

    fun update() {
        body.left = x
        body.top = y
        body.right = body.left + size
        body.bottom = body.top + size

        hitBoxLeft.left = x
        hitBoxLeft.top = y
        hitBoxLeft.right = hitBoxLeft.left + 1
        hitBoxLeft.bottom = hitBoxLeft.top + size

        hitBoxRight.left = x + size - 1
        hitBoxRight.top = y
        hitBoxRight.right = hitBoxRight.left + 1
        hitBoxRight.bottom = hitBoxRight.top + size

        hitBoxTop.left = x + 1
        hitBoxTop.top = y
        hitBoxTop.right = hitBoxTop.left + size - 1
        hitBoxTop.bottom = hitBoxTop.top + 1
    }
}