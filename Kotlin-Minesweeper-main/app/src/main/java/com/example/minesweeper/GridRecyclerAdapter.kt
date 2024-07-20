package com.example.minesweeper

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.minesweeper.game_logic.Cell
import kotlin.Int
import com.example.minesweeper.R.color

class GridRecyclerAdapter(
    private var cells: List<Cell>,
    private val listener: OnCellClickListener
) :
    RecyclerView.Adapter<GridRecyclerAdapter.MineTileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    fun setCells(cells: List<Cell>) {
        this.cells = cells
        notifyDataSetChanged()
    }

    inner class MineTileViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var textView: TextView
        private var imageView: ImageView
        private var mineView: ImageView

        init {
            textView = itemView.findViewById(R.id.cellText)
            imageView = itemView.findViewById(R.id.cellImg)
            mineView = itemView.findViewById(R.id.mineImg)
        }

        fun bind(cell: Cell) {
            itemView.setBackgroundColor(Color.LTGRAY)
            itemView.setOnClickListener { listener.onCellClick(cell) }
            itemView.setOnLongClickListener {
                listener.onCellHold(cell)
                true
            }
            if (cell.isOpened()) {
                if (cell.isMine()) {
                    textView.text = ""
                    imageView.setImageResource(R.drawable.opened_cell)
                    mineView.setImageResource(R.drawable.mine)
                    mineView.visibility = View.VISIBLE
                    mineView.setBackgroundColor(Color.RED)

                } else if (cell.isDemined()) {
                    textView.text = ""
                    imageView.setImageResource(R.drawable.opened_cell)
                    mineView.setImageResource(R.drawable.mine)
                    mineView.visibility = View.VISIBLE
                }
                else {
                    if (cell.getValue()==0)
                    {
                        textView.text = ""
                        imageView.setImageResource(R.drawable.opened_cell)
                    }
                    else
                    {
                        textView.text = cell.getValue().toString()
                        imageView.setImageResource(R.drawable.opened_cell)
                        if (cell.getValue()==1) { textView.setTextColor(Color.BLUE) }
                        else if (cell.getValue()==2) { textView.setTextColor(Color.parseColor("#204916")) }
                        else { textView.setTextColor(Color.parseColor("#AF0000")) }

                    }
                }
            } else if (cell.isMarked()) {
                mineView.setImageResource(R.drawable.mark)
                mineView.visibility = View.VISIBLE
            }
        }
    }
}