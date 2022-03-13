package `fun`.familyfunforce.wj

import com.jme3.math.Vector3f
import com.simsilica.es.EntityId
import com.simsilica.es.base.DefaultEntityData
import com.simsilica.sim.AbstractGameSystem

class GameLoopSystem(val app:WonderJamApp): AbstractGameSystem() {
    val data = DefaultEntityData()
    lateinit var playerId: EntityId

    override fun initialize() {
        println("Starting a new game!")
        playerId = data.createEntity()
        data.setComponents(playerId,
            Position(Vector3f(0f,0f,0f)),
            Direction(0f),
            Radius(0.5f),
            Speed(1f)
        )
        println("Player id set to $playerId")

        //states
        val stateManager = app.stateManager
        stateManager.attach(VisualState(data))
    }

    override fun terminate() {
        println("Stopping game")
    }
}