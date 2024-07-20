package com.example.minesweeper.game_logic


class Grid(private var height: Int, private var width:Int, private var mines: Int) {
    private var cells = arrayListOf<Cell>()
    private var openedCells = 0
    private var openedMines = 0
    private var markedCells = 0

    init {
        for (i in 0 until  height*width)
            cells.add(Cell(0))
        generateMines()
        generateNumbers()
    }

    fun openCell(cell: Cell) {
        if (!cell.isOpened())
        {
            openedCells+=1
            cell.makeOpened()
            if (cell.isMine()) { openedMines += 1 }
            if (cell.getValue() == 0) {
                for (neighbour in getNeighbours(cells.indexOf(cell))) {
                    if (!neighbour.isOpened() && !neighbour.isMarked() && !neighbour.isMine()){ openCell(neighbour) }
                }
            }
        }
        else if (cell.isOpened())
        {
            var markedNeighbours = 0
            for (neighbour in getNeighbours(cells.indexOf(cell))) {
                if (neighbour.isMarked()) {
                    markedNeighbours+=1
                    println("c"+cells.indexOf(cell).toString()+ "n "+cells.indexOf(neighbour).toString())
                }
            }
            println(markedNeighbours)
            println(cell.getValue())
            if (markedNeighbours == cell.getValue())
            {
                for (neighbour in getNeighbours(cells.indexOf(cell))) {
                    if (!neighbour.isOpened() && !neighbour.isMarked()) {
                        openCell(neighbour)
                        if (neighbour.isMine()){
                            openedMines += 1
                        }
                    }
                }
            }
        }
    }

    fun markCell(cell: Cell): Int {
        if (!cell.isOpened())
        {
            if (cell.isMarked()) {
                cell.makeUnmarked()
                markedCells-=1
                return +1
            }
            else {
                cell.makeMarked()
                markedCells+=1
                return -1
            }
        }
        else {
            return 0
        }

    }

    fun demineAllBombs()
    {
        for (cell in getCells())
            if (cell.isMine()){ cell.makeDemined() }
    }

    fun openAllBombs()
    {
        for (cell in getCells())
            if (cell.isMine() || cell.isDemined()){ openCell(cell) }
    }

    private fun generateMines()
    {
        var mined = 0
        while (mined < mines)
        {
            val minePosition = (0 until height*width).random()
            if (!getCells()[minePosition].isMine())
            {
                getCells()[minePosition].makeMine()
                mined+=1
            }
        }
    }
    private fun generateNumbers()
    {
        for (cell in cells)
        {
            if (!cell.isMine())
            {
                cell.setValue(getNumberByNeighbours(getNeighbours(cells.indexOf(cell))))
            }
        }
    }

    private fun getNumberByNeighbours(neighbours: ArrayList<Cell>): Int {
        var minedNeighbours = 0
        for (cell in neighbours)
        {
            if (cell.isMine())
            {
                minedNeighbours+=1
            }
        }
        return minedNeighbours
    }

    private fun getNeighbours(index: Int): ArrayList<Cell> {
        val neighbours = arrayListOf<Cell>()

        safeCellByIndex(index+width)?.let { neighbours.add(it) }
        safeCellByIndex(index-width)?.let { neighbours.add(it) }
        if ((index+1) % width != 1)
        {
            safeCellByIndex(index-1)?.let { neighbours.add(it) }
            safeCellByIndex(index-width-1)?.let { neighbours.add(it) }
            safeCellByIndex(index+width-1)?.let { neighbours.add(it) }
        }
        if ((index+1) % width != 0)
        {
            safeCellByIndex(index+1)?.let { neighbours.add(it) }
            safeCellByIndex(index-width+1)?.let { neighbours.add(it) }
            safeCellByIndex(index+width+1)?.let { neighbours.add(it) }
        }
        return neighbours

    }

    private fun safeCellByIndex(index: Int): Cell? {
        return if (index < 0 || index >= (height*width))
            null
        else
            cells[index]
    }

    fun getCells(): List<Cell> {
        return cells
    }

    fun getOpenedCells(): Int {
        return openedCells
    }
    fun getOpenedMines(): Int {
        return openedMines
    }
}