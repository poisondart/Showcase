package com.example.gyrotest

class BarrierSpawner(private val screenWidth: Int, private val screenHeight: Int, private val playerSize: Int) {

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
        const val CENTER = 2
    }

    val barrier = Barrier(screenWidth, screenHeight, playerSize)

    init {
        barrier.buildWalls(LEFT)
    }

    fun update() {
        if (barrier.walls[0].y > screenHeight) {
            barrier.buildWalls(LEFT)
        } else {
            barrier.walls.forEach {
                it.y += playerSize
                it.update()
            }
        }
    }

}