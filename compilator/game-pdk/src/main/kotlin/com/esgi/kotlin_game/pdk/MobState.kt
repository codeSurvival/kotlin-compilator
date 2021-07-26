package com.esgi.kotlin_game.pdk

import kotlin.random.Random

data class MobState(var needs: HashMap<MobNeeds, Int>, var position: Coordinates, val tileMemory: HashMap<Coordinates, Tile>) {

    var memory: HashMap<String, String> = HashMap()
    get() {
        return field
    }
    private set

    fun isAlive(): Boolean {
        return needs[MobNeeds.BLOOD]!! > 0
    }

    fun retain(memento: String, information: String) {
        if (memory.size > 50) {
            memory.remove(memory.keys.elementAt(Random.nextInt(0, memory.keys.size)))
        }

        memory[memento] = information
    }
}