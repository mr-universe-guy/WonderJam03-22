import com.jme3.app.SimpleApplication
import com.jme3.system.AppSettings

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
        flyCam.isEnabled=false
    }
}