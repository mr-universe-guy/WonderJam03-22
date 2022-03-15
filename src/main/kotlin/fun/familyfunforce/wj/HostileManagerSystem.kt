package `fun`.familyfunforce.wj

import com.jme3.math.FastMath
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.simsilica.es.EntityData
import com.simsilica.sim.AbstractGameSystem
import com.simsilica.sim.SimTime

/**
 * Manages waves of hostiles, how many to spawn, when to spawn and ensuring they are all tracked until dead
 */
class HostileManagerSystem(private val data:EntityData) : AbstractGameSystem(){
    private var nextEvaluation: Long = Long.MIN_VALUE

    override fun initialize() {
        nextEvaluation = manager.stepTime.getFutureTime(3.0)
    }

    override fun terminate() {
    }

    override fun update(time: SimTime) {
        if(time.time > nextEvaluation){
            evaluateCurrentWave()
        }
    }

    private fun evaluateCurrentWave() {
        println("Evaluation!")
        //for now we're just gonna spawn 1 hostile mob 10 units from the center
        val pos = getRandomSpawnLocation(10f)
        wormFactory(data, pos)
        nextEvaluation = Long.MAX_VALUE
    }

    private fun getRandomSpawnLocation(distance:Float): Vector3f{
        val angle = FastMath.nextRandomFloat()*FastMath.TWO_PI
        val pos = Vector2f(0f,distance)
        pos.rotateAroundOrigin(angle, true)
        return Vector3f(pos.x, 1f, pos.y)
    }
}