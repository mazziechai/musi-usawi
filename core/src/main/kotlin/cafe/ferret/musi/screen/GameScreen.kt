package cafe.ferret.musi.screen

import cafe.ferret.musi.component.ImageComponent
import cafe.ferret.musi.event.MapChangeEvent
import cafe.ferret.musi.extension.fire
import cafe.ferret.musi.system.RenderSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.quillraven.fleks.world
import ktx.app.KtxScreen
import ktx.log.logger


class GameScreen : KtxScreen {
    private val stage = Stage(
        FitViewport(
            20f, 15f,
            OrthographicCamera(20f, 15f)
        )
    )
    private val devTexturesAtlas = TextureAtlas("graphics/devTextures.atlas")
    private val gameWorld = world {
        injectables {
            add(stage)
        }

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

        gameWorld.systems.forEach { system ->
            if (system is EventListener) {
                stage.addListener(system)
            }
        }

        val tiledMap = TmxMapLoader().load("map/devMap.tmx")
        stage.fire(MapChangeEvent(tiledMap))

        gameWorld.entity {
            it += ImageComponent(Image(TextureRegion(devTexturesAtlas.findRegion("texture"), 8 * 9, 0, 8, 8)).apply {
                setSize(1f, 1f)
                setPosition(4f, 4f)
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
        devTexturesAtlas.dispose()
        gameWorld.dispose()
    }

    companion object {
        private val log = logger<GameScreen>()
    }
}
