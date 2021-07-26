package com.esgi.kotlin_game.pdk

data class Coordinates(val x: Int, val y: Int) {
    fun getAround(): MutableList<Coordinates> {
        return mutableListOf(
            Coordinates(x + 1, y - 1),
            Coordinates(x + 1, y),
            Coordinates(x + 1, y + 1),
            Coordinates(x, y - 1),
            Coordinates(x, y + 1),
            Coordinates(x - 1, y - 1),
            Coordinates(x - 1, y),
            Coordinates(x - 1, y + 1),
        )
    }
}
