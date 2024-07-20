package com.example.minesweeper.game_logic

class Game (private var height: Int, private var width:Int, private var mines:Int) {
    private var grid = Grid(height, width, mines)
    private var explosion = false;
    private var demining = false;
    private var marks = mines;


    fun getGrid (): Grid {return grid}
    fun getMarks (): Int {return marks}
    fun isExploded (): Boolean {return explosion}
    fun isDemined (): Boolean {return demining}

    fun clickCell(cell: Cell, openMode: Boolean) {
        if (!isExploded() && !isDemined()) {
            if (openMode) {
                if (!cell.isMarked()) {
                    grid.openCell(cell)

                    if (grid.getOpenedMines() > 0) {
                        explosion = true
                        grid.openAllBombs()
                    }
                    else if (grid.getOpenedCells() + mines == width * height) {
                        demining = true
                        grid.demineAllBombs()
                        grid.openAllBombs()

                    }
                }

            }
            else {
                marks += grid.markCell(cell)
            }
        }
    }
}