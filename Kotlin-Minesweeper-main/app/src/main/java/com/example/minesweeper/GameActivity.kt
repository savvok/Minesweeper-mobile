package com.example.minesweeper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.game_logic.Cell
import com.example.minesweeper.game_logic.Game
import org.w3c.dom.Text
import java.lang.Integer.min


class GameActivity : AppCompatActivity(), OnCellClickListener {
    private var gridRecyclerAdapter: GridRecyclerAdapter? = null
    private lateinit var gridRecyclerView: RecyclerView
    private lateinit var game: Game
    private var time = 0
    private var mines: Int = 0
    private var timerOn = true
    private lateinit var face: ImageView
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        pref = getSharedPreferences("Scores", Context.MODE_PRIVATE)

        //get difficulty
        val height = (intent.getIntExtra("height", 10))
        val width = (intent.getIntExtra("width", 10))
        mines = (intent.getIntExtra("mines", 10))

        //game and view
        gridRecyclerView = findViewById(R.id.grid)
        gridRecyclerView.layoutManager = GridLayoutManager(this, width)
        game = Game(height, width, mines)
        gridRecyclerAdapter = GridRecyclerAdapter(game.getGrid().getCells(), this)
        gridRecyclerView.adapter = gridRecyclerAdapter

        //restart button
        face = findViewById<ImageView>(R.id.face)
        face.setOnClickListener{
            game = Game(height, width, mines)
            time = 0
            timerOn = true
            face.setImageResource(R.drawable.idle)
            updateTimerCounter()
            updateMarksCounter()
            gridRecyclerAdapter?.setCells(game.getGrid().getCells())
        }

        //timer
        updateMarksCounter()
        val timer = object: CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (timerOn) { time+=1 }
                updateTimerCounter()
            }
            override fun onFinish() {}
        }
        timer.start()


    }

    override fun onCellClick(cell: Cell) {
        game.clickCell(cell, true)
        if (game.isExploded())
        {
            face.setImageResource(R.drawable.lose)
            timerOn = false
        }
        else if (game.isDemined())
        {
            face.setImageResource(R.drawable.win)
            timerOn = false
            updateScores(time, mines)
        }
        //findViewById<TextView>(R.id.marks).text = game.getGrid().getOpenedCells().toString()
        gridRecyclerAdapter?.setCells(game.getGrid().getCells())
    }

    override fun onCellHold(cell: Cell) {
        game.clickCell(cell, false)
        updateMarksCounter()
        gridRecyclerAdapter?.setCells(game.getGrid().getCells())
    }
    private fun updateMarksCounter()
    {
        var marks = findViewById<TextView>(R.id.marks)
        marks.text = game.getMarks().toString() + getString(R.string.marks)
    }
    private fun updateTimerCounter()
    {
        var timer = findViewById<TextView>(R.id.timer)
        timer.text = getString(R.string.timer) + min(time, 999).toString()
    }
    private fun updateScores(score: Int, mines: Int)
    {
        val editor = pref?.edit()
        if (mines == 7 && (pref!!.getInt("easy", -1) == -1 || score < pref!!.getInt("easy", -1)))
        { editor?.putInt("easy", score) }
        if (mines == 12 && (pref!!.getInt("medium", -1) == -1 || score < pref!!.getInt("medium", -1)))
        { editor?.putInt("medium", score) }
        if (mines == 18 && (pref!!.getInt("hard", -1) == -1 || score < pref!!.getInt("hard", -1)))
        { editor?.putInt("hard", score) }
        editor?.apply()
    }
}