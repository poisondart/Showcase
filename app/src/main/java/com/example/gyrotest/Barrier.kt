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
            BarrierSpawner.RIGHT -> {
                val wallLeft = Wall(playerSize, playerSize, screenWidth - playerSize,0)
                walls.add(wallLeft)
                val wallRight = Wall(screenWidth - playerSize - (playerSize + playerSize / 2), playerSize ,0, 0)
                walls.add(wallRight)
            }
            BarrierSpawner.CENTER -> {
                val wallLeft = Wall(screenWidth / 2 - playerSize / 2 - playerSize / 4, playerSize, 0,0)
                walls.add(wallLeft)
                val wallRight = Wall(screenWidth, playerSize ,screenWidth / 2 + playerSize / 2 + playerSize / 4, 0)
                walls.add(wallRight)
            }
        }
    }

}