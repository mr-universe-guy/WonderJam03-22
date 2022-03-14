package `fun`.familyfunforce.wj

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.PhysicsTickListener
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape
import com.jme3.bullet.control.CharacterControl
import com.simsilica.es.Entity
import com.simsilica.es.EntityData
import com.simsilica.es.EntityId
import com.simsilica.sim.AbstractGameSystem

/**
 * Moves entities based on the entity speed and driver direction
 */
class MobSystem(private val data: EntityData, private val phys:PhysicsSpace):AbstractGameSystem(), PhysicsTickListener {
    private val mobs = HashMap<EntityId, Mob>()
    private val mobSet = data.getEntities(Position::class.java, Radius::class.java, Driver::class.java)

    override fun initialize() {
        phys.addTickListener(this)
        mobSet.applyChanges()
        mobSet.forEach {e-> addMob(e)}
    }

    override fun terminate() {
        mobSet.release()
        phys.removeTickListener(this)
    }

    override fun prePhysicsTick(space: PhysicsSpace?, timeStep: Float) {
        //update the position for all mobs
        mobs.forEach { (id, mob) ->
            data.setComponent(id, Position(mob.control.physicsLocation))
        }
        if(!mobSet.applyChanges())return
        mobSet.removedEntities.forEach {e->
            val m = mobs.remove(e.id)!!
            phys.removeCollisionObject(m.control.character)
        }
        mobSet.addedEntities.forEach {e ->addMob(e)}
        //The only changes we respond to are driver changes
        mobSet.changedEntities.forEach { e->
            val driver = e.get(Driver::class.java).dir
            mobs[e.id]!!.control.setWalkDirection(driver.mult(e.get(Speed::class.java).speed))
        }
    }

    override fun physicsTick(space: PhysicsSpace?, timeStep: Float) {

    }

    private fun addMob(e:Entity){
        val m = Mob(e)
        mobs[e.id] = m
        phys.addCollisionObject(m.control.character)
    }

    private class Mob(e:Entity){
        val control:CharacterControl
        init{
            val shape = CapsuleCollisionShape(e.get(Radius::class.java).radius, 1f, 1)
            control = CharacterControl(shape, 0.1f)
            control.setGravity(1f)
            control.physicsLocation = e.get(Position::class.java).pos
        }
    }
}