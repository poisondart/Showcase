package com.poisondart.showcase.games.flappy_clone

import android.content.Context
import com.poisondart.showcase.core.GameActivity

class FlappyCloneActivity: GameActivity() {
    override fun createGameView(context: Context, x: Int, y: Int) = FlappyCloneGameView(context, x, y)
}