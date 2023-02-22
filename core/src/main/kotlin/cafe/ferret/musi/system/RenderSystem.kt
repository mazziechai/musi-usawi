package cafe.ferret.musi.system

import cafe.ferret.musi.component.ImageComponent
import cafe.ferret.musi.event.MapChangeEvent
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntityBy
import ktx.graphics.use
import ktx.tiled.forEachLayer

/**
 * Renders [com.badlogic.gdx.scenes.scene2d.ui.Image].
 */
class RenderSystem(private val stage: Stage = inject()) :
    IteratingSystem(
        family { all(ImageComponent) },
        comparator = compareEntityBy(ImageComponent)

    ), EventListener {

    private val backgroundLayers = mutableListOf<TiledMapTileLayer>()
    private val foregroundLayers = mutableListOf<TiledMapTileLayer>()
    private val mapRenderer = OrthogonalTiledMapRenderer(null, 1 / 8f, stage.batch)
    private val orthographicCamera = stage.camera as OrthographicCamera

    /**
     * Gets the [com.badlogic.gdx.scenes.scene2d.Stage] and renders every [com.badlogic.gdx.scenes.scene2d.Actor].
     */
    override fun onTick() {
        super.onTick()

        with(stage) {
            viewport.apply()

            AnimatedTiledMapTile.updateAnimationBaseTime()
            mapRenderer.setView(orthographicCamera)

            if (backgroundLayers.isNotEmpty()) {
                stage.batch.use(orthographicCamera.combined) {
                    backgroundLayers.forEach { mapRenderer.renderTileLayer(it) }
                }
            }

            if (foregroundLayers.isNotEmpty()) {
                stage.batch.use(orthographicCamera.combined) {
                    foregroundLayers.forEach { mapRenderer.renderTileLayer(it) }
                }
            }

            act(deltaTime)
            draw()
        }
    }

    override fun onTickEntity(entity: Entity) {
    }

    /**
     * Handles events, specifically the [cafe.ferret.musi.event.MapChangeEvent].
     * The handling of this event checks if the map layer is a foreground layer, otherwise it is a background.
     */
    override fun handle(event: Event): Boolean {
        when (event) {
            is MapChangeEvent -> {
                event.map.forEachLayer<TiledMapTileLayer> { layer ->
                    if (layer.name.startsWith("fgd_")) {
                        foregroundLayers.add(layer)
                    } else {
                        backgroundLayers.add(layer)
                    }
                }
                return true
            }
        }
        return false
    }
}



