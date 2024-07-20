package com.example.minesweeper

import com.example.minesweeper.game_logic.Cell

interface OnCellClickListener {
    fun onCellClick(cell: Cell)
    fun onCellHold(cell: Cell)
}