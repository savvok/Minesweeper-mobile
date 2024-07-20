package com.example.minesweeper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DifficultyActivity : AppCompatActivity() {
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)
        pref = getSharedPreferences("Scores", Context.MODE_PRIVATE)
        updateScores()

        var easyButton = findViewById<Button>(R.id.easyButton)
        easyButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("width", 8);
            intent.putExtra("height", 8);
            intent.putExtra("mines", 7);
            startActivity(intent);
        }
        var mediumButton = findViewById<Button>(R.id.mediumButton)
        mediumButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("width", 10);
            intent.putExtra("height", 12);
            intent.putExtra("mines", 12);
            startActivity(intent);
        }
       var hardButton = findViewById<Button>(R.id.hardButton)
        hardButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("width", 10);
            intent.putExtra("height", 14);
            intent.putExtra("mines", 18);
            startActivity(intent);
        }
    }

    override fun onResume() {
        super.onResume()
        updateScores()
    }

    private fun updateScores()
    {
        var easyScore = findViewById<TextView>(R.id.easyScore)
        if (pref!!.getInt("easy", -1) == -1)
            { easyScore.text = getString(R.string.score) + " -" + " сек" }
        else
            { easyScore.text = getString(R.string.score) + " " + pref!!.getInt("easy", -1).toString() + " сек" }

        var mediumScore = findViewById<TextView>(R.id.mediumScore)
        if (pref!!.getInt("medium", -1) == -1)
            { mediumScore.text = getString(R.string.score) + " -" + " сек" }
        else
            { mediumScore.text = getString(R.string.score) + " " + pref!!.getInt("medium", -1).toString() + " сек" }

        var hardScore = findViewById<TextView>(R.id.hardScore)
        if (pref!!.getInt("hard", -1) == -1)
            { hardScore.text = getString(R.string.score) + " -" + " сек" }
        else
            { hardScore.text = getString(R.string.score) + " " + pref!!.getInt("hard", -1).toString() + " сек" }

    }
}