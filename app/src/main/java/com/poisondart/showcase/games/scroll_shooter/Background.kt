package com.poisondart.showcase.games.scroll_shooter

import java.util.*

class Background(
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val playerSize: Int
) {

    val stars = mutableListOf<Star>()

    fun update() {
        stars.forEach { it.update() }
    }

    init {
        for (i in 0..40) {
            stars.add(Star())
        }
    }

    inner class Star {
        var x: Int = 0
        var y: Int = 0

        init {
            val generator = Random()
            x = generator.nextInt(screenWidth)
            y = generator.nextInt(screenHeight)
        }

        fun update() {
            y += playerSize / 4
            if (y > screenHeight) {
                y = 0
                val generator = Random()
                x = generator.nextInt(screenWidth)
            }
        }
    }
}