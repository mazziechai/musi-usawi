package cafe.ferret.musi.screen

import cafe.ferret.musi.component.ImageComponent
import cafe.ferret.musi.system.RenderSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.quillraven.fleks.world
import ktx.app.KtxScreen
import ktx.log.logger

class GameScreen : KtxScreen {
    private val stage = Stage(FitViewport(4f, 3f))
    private val devTextures = Texture("assets/texture.png")
    private val gameWorld = world {
        injectables { add(stage) }

        components {
            onAdd(ImageComponent, ImageComponent.onAdd)
            onRemove(ImageComponent, ImageComponent.onRemove)
        }

        systems {
            add(RenderSystem())
        }
    }

    override fun show() {
        log.debug { "Loading" }

        gameWorld.entity {
            it += ImageComponent(Image(devTextures).apply {
                setSize(2f, 2f)
                setPosition(1f, 1f)
            })
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        gameWorld.update(delta)
    }

    override fun dispose() {
        log.debug { "Disposing" }
        stage.dispose()
        devTextures.dispose()
        gameWorld.dispose()
    }

    companion object {
        private val log = logger<GameScreen>()
    }
}
