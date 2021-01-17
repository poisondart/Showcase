package com.poisondart.showcase.games.scroll_shooter

class Cannon(
    playerSize: Int
) {
    val projectiles = arrayOf(
        Projectile(playerSize),
        Projectile(playerSize),
        Projectile(playerSize)
    )

    fun shot(x: Int, y: Int) {
        projectiles.forEach {
            if (!it.isShootOut) {
                it.fire(x, y)
                return
            }
        }
    }

    fun update() {
        projectiles.forEach {
            it.move()
            it.update()
        }
    }
}