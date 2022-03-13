package `fun`.familyfunforce.wj

import com.simsilica.es.base.DefaultEntityData
import com.simsilica.sim.AbstractGameSystem

class GameLoopSystem: AbstractGameSystem() {
    val data = DefaultEntityData()

    override fun initialize() {
        println("Starting a new game!")

    }

    override fun terminate() {
        println("Stopping game")
    }
}