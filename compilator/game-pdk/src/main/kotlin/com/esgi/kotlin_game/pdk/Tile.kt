package com.esgi.kotlin_game.pdk

data class Tile(val coordinates: Coordinates, val type: TileType,
                val containt: MutableList<TileContaint> = emptyList<TileContaint>().toMutableList()
) {

    fun isFree(): Boolean {
        return (
                this.type == TileType.GRASS
                )
    }

    fun asMeal(): Boolean {
        return this.containt.any { contain -> contain == TileContaint.MEAL}
    }

    fun removeContain(containtType: TileContaint) {
        this.containt.removeAll { containt -> containt == containtType }
    }

    fun isCrossable(): Boolean {
        return (type == TileType.GRASS || type == TileType.PIT)
    }
}
