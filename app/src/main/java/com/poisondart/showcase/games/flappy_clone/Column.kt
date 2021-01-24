package com.poisondart.showcase.games.flappy_clone

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
                (screenHeight / 2 - birdSize * 1.5).toInt(),
                screenWidth,
                0
        )
        parts.add(columnPartTop)

        val columnPartBottom = ColumnPart(
                screenWidth,
                screenHeight,
                birdSize,
                (screenHeight / 2 - birdSize * 1.5).toInt(),
                screenWidth,
                (screenHeight / 2 + birdSize * 1.5).toInt()
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
}