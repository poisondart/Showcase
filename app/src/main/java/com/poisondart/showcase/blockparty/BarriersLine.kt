package com.poisondart.showcase.blockparty

import android.graphics.Rect
import java.util.*

class BarriersLine(private val screenWidth: Int, private val screenHeight: Int, private val playerSize: Int) {

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
        const val CENTER = 2

        private const val BARRIERS_TYPES_COUNT = 3
    }

    val barriers = mutableListOf<Barrier>()

    private val generator = Random()

    var wallsPassed = 0
    private var speed = playerSize / 16

    fun getWallsPassedString() = wallsPassed.toString()

    init {
        val barrier = Barrier(screenWidth, screenHeight, playerSize)
        barrier.buildWalls(generator.nextInt(BARRIERS_TYPES_COUNT))
        barriers.add(barrier)
    }

    fun update() {
        if (barriers.size < 3 && barriers.last().walls[0].y > screenHeight / 3) {
            val barrier = Barrier(screenWidth, screenHeight, playerSize)
            barrier.buildWalls(generator.nextInt(BARRIERS_TYPES_COUNT))
            barriers.add(barrier)
        }

        barriers.forEach {
            if (it.walls[0].y > screenHeight) {
                wallsPassed++
                if (wallsPassed % 5 == 0) {
                    speed = (speed * 1.25).toInt()
                }
                it.buildWalls(generator.nextInt(BARRIERS_TYPES_COUNT))
            } else {
                it.walls.forEach { wall ->
                    wall.y += speed
                    wall.update()
                }
            }
        }
    }

    fun intersect(hitBox: Rect): Boolean {
        barriers.forEach { barrier: Barrier ->
            barrier.walls.forEach { wall: Wall ->
                if (hitBox.intersect(wall.hitBox)) return true
            }
        }
        return false
    }

    fun reset() {
        barriers.clear()
        val barrier = Barrier(screenWidth, screenHeight, playerSize)
        barrier.buildWalls(generator.nextInt(BARRIERS_TYPES_COUNT))
        barriers.add(barrier)
        wallsPassed = 0
        speed = playerSize / 16
    }

}