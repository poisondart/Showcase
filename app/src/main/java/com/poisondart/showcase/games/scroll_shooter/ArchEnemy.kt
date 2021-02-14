package com.poisondart.showcase.games.scroll_shooter

import android.graphics.Rect
import kotlin.random.Random

class ArchEnemy(private val screenWidth: Int, private val screenHeight: Int) {

    private val size = screenWidth / 10
    private var x = 0
    private var y = 0
    private var gravity = 0

    val enemies = mutableListOf<Enemy>()

    init {
        respawn()
    }

    fun respawn() {
        enemies.clear()
        y = -size * 10
        gravity = Random.nextInt(2)
        x = if (gravity == 0) 0 else screenWidth - size
        for (i in 0..10) {
            val enemy = Enemy(size, x, y)
            enemies.add(enemy)
            y += (size + 10)
        }
    }

    fun move() {
        enemies.forEach {
            it.y += 10
            if (it.y > screenHeight / 2) {
                it.x = if (gravity == 0) it.x + 10 else it.x -10
            }
        }
        if (enemies.isNotEmpty() && enemies.first().y > screenHeight) respawn()
    }

    fun update() {
        enemies.forEach { it.update() }
    }

    fun intersectProjectile(projectiles: Array<Projectile>): Boolean {
        enemies.forEach {
            projectiles.forEach { projectile ->
                if (projectile.hitBox.intersect(it.hitBox) && projectile.isShootOut) {
                    projectile.isShootOut = false
                    enemies.remove(it)
                    return true
                }
            }
        }
        return false
    }

    fun intersectPlayer(playerHitBox: Rect): Boolean {
        enemies.forEach {
            if (it.hitBox.intersect(playerHitBox)) return true
        }
        return false
    }

    inner class Enemy(size: Int, var x: Int, var y: Int) {

        val hitBox = Rect(x, y, x + size, y + size)

        fun update() {
            hitBox.left = x
            hitBox.top = y
            hitBox.right = hitBox.left + size
            hitBox.bottom = hitBox.top + size
        }
    }
}