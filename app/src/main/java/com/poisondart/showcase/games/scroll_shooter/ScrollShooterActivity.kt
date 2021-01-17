package com.poisondart.showcase.games.scroll_shooter

import android.content.Context
import com.poisondart.showcase.core.GameActivity

class ScrollShooterActivity : GameActivity() {
    override fun createGameView(context: Context, x: Int, y: Int) = ScrollShooterGameView(context, x, y)
}