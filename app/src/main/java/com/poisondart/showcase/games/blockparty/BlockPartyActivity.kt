package com.poisondart.showcase.games.blockparty

import android.content.Context
import com.poisondart.showcase.core.GameActivity

class BlockPartyActivity : GameActivity() {
    override fun createGameView(context: Context, x: Int, y: Int) = BlockPartyGameView(context, x, y)
}