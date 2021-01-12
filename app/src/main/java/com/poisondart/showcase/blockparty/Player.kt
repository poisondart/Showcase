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
    val hitBoxTop = Rect(x, y, size, 1)

    fun move(lastX: Float, lastY: Float, isPushedLeft: Boolean, isPushedRight: Boolean, isPushedTop: Boolean) {
        if (abs(lastX) > 0.4) {
            if (lastX < 0) {
                if (!isPushedRight) x += (size / 4)
                if (x > screenWidth - body.width()) x = screenWidth - body.width()
            } else {
                if (!isPushedLeft) x -= (size / 4)
                if (x < 0) x = 0
            }
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

        hitBoxTop.left = x
        hitBoxTop.top = y
        hitBoxTop.right = hitBoxTop.left + size
        hitBoxTop.bottom = hitBoxTop.top + 1
    }
}