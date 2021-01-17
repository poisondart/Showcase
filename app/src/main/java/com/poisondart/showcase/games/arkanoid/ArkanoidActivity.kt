package com.poisondart.showcase.games.arkanoid

import android.content.Context
import com.poisondart.showcase.core.GameActivity

class ArkanoidActivity: GameActivity() {
    override fun createGameView(context: Context, x: Int, y: Int) = ArkanoidGameView(context, x, y)
}