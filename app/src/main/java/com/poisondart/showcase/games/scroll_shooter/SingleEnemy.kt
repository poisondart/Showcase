package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect
import kotlin.random.Random

class SingleEnemy(playerSize: Int, private val screenWidth: Int, private val screenHeight: Int) {
    private val size = playerSize * 1.5

    private var x: Int = 0
    private var y: Int = 0

    private var lives = 9

    private var gravity = 0

    val hitBox = Rect(x, y, (x + size).toInt(), (y + size).toInt())

    init {
        respawn()
    }

    fun respawn() {
        gravity = Random.nextInt(2)
        lives = 9
        x = Random.nextInt((screenWidth - size).toInt())
        y = if (gravity == 0) {
            (-size).toInt()
        } else {
            (screenHeight + size).toInt()
        }
        update()
    }

    fun move() {
        if (gravity == 0) {
            y += (size / 10).toInt()
            if (y > screenHeight) {
                respawn()
            }
        } else {
            y -= (size / 10).toInt()
            if (y < - size) {
                respawn()
            }
        }
    }

    fun intersectPlayer(playerHitBox: Rect): Boolean {
        return hitBox.intersect(playerHitBox)
    }

    fun intersectProjectiles(projectiles: Array<Projectile>): Boolean {
        projectiles.forEach {
            if (hitBox.intersect(it.hitBox)) {
                lives--
                it.isShootOut = false
                return true
            }
        }
        return false
    }

    fun isDead() = lives == 0

    fun update() {
        hitBox.left = x
        hitBox.top = y
        hitBox.right = (hitBox.left + size).toInt()
        hitBox.bottom = (hitBox.top + size).toInt()
    }
}