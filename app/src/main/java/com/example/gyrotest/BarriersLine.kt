package com.example.gyrotest

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
                it.buildWalls(generator.nextInt(BARRIERS_TYPES_COUNT))
            } else {
                it.walls.forEach { wall ->
                    wall.y += playerSize
                    wall.update()
                }
            }
        }
    }

}