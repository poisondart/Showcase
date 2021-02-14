package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect
import kotlin.random.Random

class SingleEnemy(playerSize: Int, private val screenWidth: Int, private val screenHeight: Int) {
    private val size = playerSize * 1.5

    private var x: Int = 0
    private var y: Int = 0

    private var lives = 9

    val hitBox = Rect(x, y, (x + size).toInt(), (y + size).toInt())

    init {
        respawn()
    }

    fun respawn() {
        lives = 9
        x = Random.nextInt((screenWidth - size).toInt())
        y = (-size).toInt()
        update()
    }

    fun move() {
        y += (size / 10).toInt()
        if (y > screenHeight) {
            respawn()
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