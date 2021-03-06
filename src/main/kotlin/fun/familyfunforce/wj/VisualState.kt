package `fun`.familyfunforce.wj

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Cylinder
import com.simsilica.es.Entity
import com.simsilica.es.EntityData
import com.simsilica.es.EntityId

class VisualState(data:EntityData):BaseAppState() {
    private val visMap = HashMap<EntityId, Spatial>()
    private val visNode = Node("Visuals")
    private val visualSet = data.getEntities(Position::class.java, Radius::class.java)
    private lateinit var debugMat: Material

    override fun initialize(app: Application) {
        debugMat = Material(app.assetManager,"Common/MatDefs/Misc/Unshaded.j3md")
        debugMat.setColor("Color", ColorRGBA.White)
    }

    override fun cleanup(app: Application?) {
        visualSet.release()
    }

    override fun onEnable() {
        val app = application as WonderJamApp
        app.rootNode.attachChild(visNode)
        visualSet.forEach {e-> addVisual(e) }
    }

    override fun onDisable() {
        visNode.removeFromParent()
    }

    override fun update(tpf: Float) {
        if(!visualSet.applyChanges()) return
        visualSet.removedEntities.forEach { e ->
            val spat = visMap.remove(e.id)!!
            spat.removeFromParent()
        }
        visualSet.addedEntities.forEach { e -> addVisual(e) }
        visualSet.changedEntities.forEach { e ->
            val spat = visMap[e.id]!!
            spat.localTranslation = e.get(Position::class.java).pos
        }
    }

    fun getSpatialFromId(id:EntityId): Spatial?{
        return visMap[id]
    }

    private fun addVisual(e: Entity) {
        val radius = e.get(Radius::class.java).radius
        val capsule = Cylinder(16,16,radius,1f,true)
        //val capsule = Box(0.5f,0.5f,0.5f)
        val geo = Geometry("Entity ${e.id}",capsule)
        geo.localTranslation = e.get(Position::class.java).pos
        geo.material=debugMat
        visNode.attachChild(geo)
        println("$e has been added to the visual node")
        visMap[e.id] = geo
    }
}