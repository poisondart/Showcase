package com.example.gyrotest

class Barrier(private val screenWidth: Int, private val screenHeight: Int, private val playerSize: Int) {

    val walls = mutableListOf<Wall>()

    fun buildWalls(barrierType: Int) {
        walls.clear()

        when(barrierType) {
            BarrierSpawner.LEFT -> {
                val wallLeft = Wall(playerSize, playerSize, 0,0)
                walls.add(wallLeft)
                val wallRight = Wall(screenWidth, playerSize ,wallLeft.width + (playerSize + playerSize / 2), 0)
                walls.add(wallRight)
            }
        }
    }

}