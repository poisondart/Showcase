package com.poisondart.showcase.games.flappy_clone

import android.graphics.Rect

class Column(private val screenWidth: Int, private val screenHeight: Int, private val birdSize: Int) {
    var parts = mutableListOf<ColumnPart>()

    init {
        build()
    }

    private fun build() {
        val columnPartTop = ColumnPart(
                screenWidth,
                screenHeight,
                birdSize,
                (screenHeight * 0.5 - birdSize * 1.5).toInt(),
                screenWidth,
                0
        )
        parts.add(columnPartTop)

        val columnPartBottom = ColumnPart(
                screenWidth,
                screenHeight,
                birdSize,
                (screenHeight * 0.5 - birdSize * 1.5).toInt(),
                screenWidth,
                (screenHeight * 0.5 + birdSize * 1.5).toInt()
        )
        parts.add(columnPartBottom)
    }

    fun move() {
        parts.forEach {
            it.move()
        }
    }

    fun update() {
        parts.forEach {
            it.update()
        }
    }

    fun intersect(hitBox: Rect): Boolean {
        parts.forEach {
            if (it.hitBox.intersect(hitBox)) return true
        }
        return false
    }

    fun reset() {
        parts.clear()
        build()
    }

}