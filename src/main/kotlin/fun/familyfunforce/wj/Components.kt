package `fun`.familyfunforce.wj

import com.jme3.math.Vector3f
import com.simsilica.es.EntityComponent
import com.simsilica.es.EntityData
import com.simsilica.es.EntityId

class Position(val pos:Vector3f): EntityComponent

class Direction(val dir:Float): EntityComponent

class Radius(val radius:Float): EntityComponent

class Speed(val speed:Float): EntityComponent

class Driver(val dir: Vector3f): EntityComponent

class HitPoints(val hp:Int): EntityComponent

//Factories
fun wormFactory(data:EntityData,pos:Vector3f): EntityId{
    println("Spawning worm at $pos")
    val id = data.createEntity()
    data.setComponents(id, Position(pos),Radius(0.25f))
    return id
}