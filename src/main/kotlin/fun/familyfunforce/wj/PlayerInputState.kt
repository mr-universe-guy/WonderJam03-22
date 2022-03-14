package `fun`.familyfunforce.wj

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.input.KeyInput
import com.jme3.math.Vector3f
import com.simsilica.es.EntityData
import com.simsilica.es.EntityId
import com.simsilica.lemur.GuiGlobals
import com.simsilica.lemur.input.AnalogFunctionListener
import com.simsilica.lemur.input.FunctionId
import com.simsilica.lemur.input.InputState

class PlayerInputState(private val data:EntityData, private val playerId:EntityId):BaseAppState(), AnalogFunctionListener{
    private val playerInput = "Player Input"
    private val pMoveHoriz = FunctionId(playerInput, "PMoveX")
    private val pMoveVert = FunctionId(playerInput, "PMoveY")
    private val playerMovement = Vector3f(0f,0f,0f)

    override fun initialize(_app: Application) {
        //grab the lemur input mapper to use
        val mapper = GuiGlobals.getInstance().inputMapper
        mapper.map(pMoveHoriz, InputState.Negative, KeyInput.KEY_A)
        mapper.map(pMoveHoriz, InputState.Positive, KeyInput.KEY_D)
        mapper.map(pMoveVert, InputState.Negative, KeyInput.KEY_W)
        mapper.map(pMoveVert, InputState.Positive, KeyInput.KEY_S)
        mapper.addAnalogListener(this, pMoveHoriz, pMoveVert)
    }

    override fun cleanup(app: Application?) {
        val mapper = GuiGlobals.getInstance().inputMapper

        mapper.removeAnalogListener(this, pMoveHoriz, pMoveVert)
    }

    override fun onEnable() {
        GuiGlobals.getInstance().inputMapper.activateGroup(playerInput)
    }

    override fun onDisable() {
        GuiGlobals.getInstance().inputMapper.deactivateGroup(playerInput)
    }

    override fun update(tpf: Float) {
        data.setComponent(playerId, Driver(playerMovement))
    }

    override fun valueActive(func: FunctionId?, value: Double, tpf: Double) {
        if(pMoveHoriz == func){
            playerMovement.x=value.toFloat()
        }
        if(pMoveVert == func){
            playerMovement.z=value.toFloat()
        }
    }
}