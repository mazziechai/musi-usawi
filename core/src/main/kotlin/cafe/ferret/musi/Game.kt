package cafe.ferret.musi

import cafe.ferret.musi.screen.GameScreen
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import ktx.app.KtxGame
import ktx.app.KtxScreen

/**
 * The main game class.
 */
class Game : KtxGame<KtxScreen>() {
    override fun create() {
        if (System.getenv("MUSI_DEBUG") == "true") {
            Gdx.app.logLevel = Application.LOG_DEBUG
        }

        addScreen(GameScreen())
        setScreen<GameScreen>()
    }
}
