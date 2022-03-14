package `fun`.familyfunforce.wj

import com.jme3.math.Vector3f
import com.simsilica.es.EntityComponent

class Position(val pos:Vector3f): EntityComponent

class Direction(val dir:Float): EntityComponent

class Radius(val radius:Float): EntityComponent

class Speed(val speed:Float): EntityComponent

class Driver(val dir: Vector3f): EntityComponent

class HitPoints(val hp:Int): EntityComponent