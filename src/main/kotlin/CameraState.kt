import `fun`.familyfunforce.wj.VisualState
import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import com.jme3.scene.Spatial
import com.simsilica.es.EntityId

class CameraState(private val cam:Camera, private val playerId:EntityId): BaseAppState() {
    private var camTarget: Spatial? = null
    private val camOffset = Vector3f(0f, 5f, 15f)

    override fun initialize(app: Application) {
    }

    override fun cleanup(app: Application?) {
    }

    override fun onEnable() {
        val visState = getState(VisualState::class.java)!!
        camTarget = visState.getSpatialFromId(playerId)
    }

    override fun onDisable() {
        camTarget=null
    }

    override fun update(tpf: Float) {
        if(camTarget == null) return
        cam.location = camTarget!!.worldTranslation.add(camOffset)
        cam.lookAt(camTarget!!.worldTranslation, Vector3f.UNIT_Y)
    }
}