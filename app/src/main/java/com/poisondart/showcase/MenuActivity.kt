package com.poisondart.showcase

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MenuActivity: ListActivity() {
    private val games = arrayOf("Block Party", "Arkanoid", "Scroll Shooter")
    private val activities = arrayOf("blockparty.BlockPartyActivity", "arkanoid.ArkanoidActivity", "scroll_shooter.ScrollShooterActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, games)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        try {
            val clazz = Class.forName("com.poisondart.showcase.games.${activities[position]}")
            startActivity(Intent(this, clazz))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this, "Game Not Found!", Toast.LENGTH_SHORT).show()
        }
    }
}