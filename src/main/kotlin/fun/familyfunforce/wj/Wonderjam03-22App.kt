package `fun`.familyfunforce.wj

import com.jme3.app.SimpleApplication
import com.jme3.system.AppSettings
import com.simsilica.lemur.GuiGlobals
import com.simsilica.state.GameSystemsState

fun main(vararg args:String) {
    println("Hello Wonderjam 03-22 Building: Light/Decay :)")
    val app = WonderJamApp()
    app.isShowSettings=false
    val settings = AppSettings(true)
    settings.setResolution(800,600)
    app.start()
}

class WonderJamApp: SimpleApplication(){
    override fun simpleInitApp() {
        //start gui
        flyCam.isEnabled=false
        GuiGlobals.initialize(this)
        //launch the game system thread tied to jme render loop for convenience
        val gss = GameSystemsState(false)
        //we'll keep all non-render tasks on the gss, all render tasks will be in appstates
        stateManager.attach(gss)
        //go straight into game loop, in the future main menu?
        gss.register(GameLoopSystem::class.java, GameLoopSystem(this))
    }
}