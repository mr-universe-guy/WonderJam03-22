package `fun`.familyfunforce.wj

import CameraState
import com.jme3.bullet.BulletAppState
import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.collision.shapes.PlaneCollisionShape
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.bullet.objects.PhysicsBody
import com.jme3.math.Plane
import com.jme3.math.Vector3f
import com.simsilica.es.EntityId
import com.simsilica.es.base.DefaultEntityData
import com.simsilica.sim.AbstractGameSystem

class GameLoopSystem(private val app:WonderJamApp): AbstractGameSystem() {
    private val data = DefaultEntityData()
    private lateinit var phys: PhysicsSpace
    private lateinit var playerId: EntityId

    override fun initialize() {
        println("Starting a new game!")
        //create physics
        val bullet = BulletAppState()
        app.stateManager.attach(bullet)
        phys = bullet.physicsSpace
        phys.getGravity(Vector3f(0f,-10f,0f))
        //create floor
        val floorPlane = PlaneCollisionShape(Plane(Vector3f(0f,1f,0f),0f))
        val floor = RigidBodyControl(floorPlane, PhysicsBody.massForStatic)
        phys.addCollisionObject(floor)
        //player
        playerId = data.createEntity()
        data.setComponents(playerId,
            Position(Vector3f(0f,3f,0f)),
            Direction(0f),
            Radius(0.5f),
            Speed(1f),
            Driver(Vector3f(0f,0f,0f))
        )
        println("Player id set to $playerId")

        //systems
        manager.register(MobSystem::class.java, MobSystem(data, phys))
        manager.register(HostileManagerSystem::class.java, HostileManagerSystem(data))
        //states
        val stateManager = app.stateManager
        stateManager.attach(VisualState(data))
        stateManager.attach(CameraState(app.camera, playerId))
        stateManager.attach(PlayerInputState(data, playerId))
    }

    override fun terminate() {
        println("Stopping game")
    }
}