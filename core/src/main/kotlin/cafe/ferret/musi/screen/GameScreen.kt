package cafe.ferret.musi.screen

import cafe.ferret.musi.component.ImageComponent
import cafe.ferret.musi.event.MapChangeEvent
import cafe.ferret.musi.extension.fire
import cafe.ferret.musi.system.AnimationSystem
import cafe.ferret.musi.system.EntitySpawnSystem
import cafe.ferret.musi.system.RenderSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.quillraven.fleks.world
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.log.logger


class GameScreen : KtxScreen {
    private val stage = Stage(
        FitViewport(
            20f, 15f,
            OrthographicCamera(20f, 15f)
        )
    )
    private val devTexturesAtlas = TextureAtlas("atlas/devTextures.atlas")
    private val gameWorld = world {
        injectables {
            add(stage)
            add(devTexturesAtlas)
        }

        components {
            onAdd(ImageComponent, ImageComponent.onAdd)
            onRemove(ImageComponent, ImageComponent.onRemove)
        }

        systems {
            add(EntitySpawnSystem())
            add(AnimationSystem())
            add(RenderSystem())
        }
    }

    private var currentMap: TiledMap? = null

    override fun show() {
        log.debug { "Loading" }

        // Add listeners to the world
        gameWorld.systems.forEach { system ->
            if (system is EventListener) {
                stage.addListener(system)
            }
        }

        currentMap = TmxMapLoader().load("map/devMap.tmx")
        stage.fire(MapChangeEvent(currentMap!!))
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        gameWorld.update(delta)
    }

    override fun dispose() {
        log.debug { "Disposing" }
        stage.disposeSafely()
        devTexturesAtlas.disposeSafely()
        gameWorld.dispose()
        currentMap.disposeSafely()
    }

    companion object {
        private val log = logger<GameScreen>()
    }
}
