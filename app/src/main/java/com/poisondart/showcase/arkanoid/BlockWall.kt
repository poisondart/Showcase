package com.poisondart.showcase.arkanoid

import android.graphics.Rect

class BlockWall(private val screenWidth: Int) {

    val blocks = mutableListOf<Block>()

    init {
        generateWall()
    }

    private fun generateWall() {
        val count = 8
        val width = screenWidth / count
        val height = width / count

        var x = 0
        var y = height * 4

        for (j in 0..(count / 2)) {
            for (i in 0..count) {
                val block = Block(width, height, x, y)
                blocks.add(block)
                x += width
            }
            x = 0
            y += height
        }
    }

    fun reset() {
        blocks.clear()
        generateWall()
    }

    fun intersect(hitBox: Rect): Boolean {
        var index = -1
        var intersect = false
        blocks.forEachIndexed { i, block ->
            if (hitBox.intersect(block.hitBox)) {
                index = i
                intersect = true
                return@forEachIndexed
            }
        }
        if (index >= 0) blocks.removeAt(index)
        return intersect
    }
}