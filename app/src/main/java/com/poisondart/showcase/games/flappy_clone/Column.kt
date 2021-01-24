package com.poisondart.showcase.games.flappy_clone

import android.graphics.Rect
import java.util.*

class Column(private val screenWidth: Int, private val screenHeight: Int, private val birdSize: Int) {

    private var x = screenWidth

    var parts = mutableListOf<ColumnPart>()

    private val generator = Random()

    init {
        build()
    }

    private fun build() {
        when (generator.nextInt(3)) {
            0 -> buildCenterHole()
            1 -> buildTopHole()
            2 -> buildBottomHole()
        }
    }

    private fun buildCenterHole() {
        val columnPartTop = ColumnPart(
                birdSize,
                (screenHeight * 0.5 - birdSize * 1.5).toInt(),
                screenWidth,
                0
        )
        parts.add(columnPartTop)

        val columnPartBottom = ColumnPart(
                birdSize,
                (screenHeight * 0.5 - birdSize * 1.5).toInt(),
                screenWidth,
                (screenHeight * 0.5 + birdSize * 1.5).toInt()
        )
        parts.add(columnPartBottom)
    }

    private fun buildTopHole() {
        val columnPartTop = ColumnPart(
                birdSize,
                (screenHeight * 0.33 - birdSize * 1.5).toInt(),
                screenWidth,
                0
        )
        parts.add(columnPartTop)

        val columnPartBottom = ColumnPart(
                birdSize,
                screenHeight,
                screenWidth,
                (screenHeight * 0.33 + birdSize * 1.5).toInt()
        )
        parts.add(columnPartBottom)
    }

    private fun buildBottomHole() {
        val columnPartTop = ColumnPart(
                birdSize,
                (screenHeight * 0.66 - birdSize * 1.5).toInt(),
                screenWidth,
                0
        )
        parts.add(columnPartTop)

        val columnPartBottom = ColumnPart(
                birdSize,
                screenHeight,
                screenWidth,
                (screenHeight * 0.66 + birdSize * 1.5).toInt()
        )
        parts.add(columnPartBottom)
    }

    fun move() {
        x -= birdSize / 10
        if (x < 0 - birdSize) {
            reset()
            return
        }
        parts.forEach {
            it.move(x)
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
        x = screenWidth
        parts.clear()
        build()
    }

}