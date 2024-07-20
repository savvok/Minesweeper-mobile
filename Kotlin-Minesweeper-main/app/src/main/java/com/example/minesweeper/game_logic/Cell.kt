package com.example.minesweeper.game_logic

class Cell(private var value:Int) {
    private var isMarked:Boolean = false
    private var isOpened:Boolean = false

    fun isMine(): Boolean {return value == -1}
    fun isDemined(): Boolean {return value == -2}
    fun isOpened(): Boolean {return isOpened}
    fun isMarked(): Boolean {return isMarked}
    fun getValue(): Int {return value}

    fun makeMine(){value = -1}
    fun makeDemined(){value = -2}
    fun makeMarked(){isMarked = true}
    fun makeUnmarked(){isMarked = false}
    fun makeOpened(): Int {
        isOpened = true
        return value
    }
    fun setValue(value: Int) {
        this.value = value
    }
}