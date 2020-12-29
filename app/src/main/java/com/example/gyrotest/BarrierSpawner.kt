package com.example.gyrotest

import java.util.*

class BarrierSpawner(private val screenWidth: Int, private val screenHeight: Int, private val playerSize: Int) {

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
        const val CENTER = 2
    }

    val barrier = Barrier(screenWidth, screenHeight, playerSize)

    private val generator = Random()

    init {
        barrier.buildWalls(generator.nextInt(2))
    }

    fun update() {
        if (barrier.walls[0].y > screenHeight) {
            barrier.buildWalls(generator.nextInt(2))
        } else {
            barrier.walls.forEach {
                it.y += playerSize
                it.update()
            }
        }
    }

}