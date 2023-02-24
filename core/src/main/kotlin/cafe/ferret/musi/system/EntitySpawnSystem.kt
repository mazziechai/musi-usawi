package cafe.ferret.musi.system

import cafe.ferret.musi.Game.Companion.UNIT_SCALE
import cafe.ferret.musi.component.*
import cafe.ferret.musi.event.MapChangeEvent
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Scaling
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import ktx.tiled.layer
import ktx.tiled.property

class EntitySpawnSystem : EventListener, IteratingSystem(family { all(EntitySpawnComponent) }) {
    private val cachedConfigs = mutableMapOf<String, EntitySpawnConfiguration>()

    override fun onTickEntity(entity: Entity) {
        with(entity[EntitySpawnComponent]) {
            val config = spawnConfiguration(type)

            // Animation test
            world.entity {
                it += ImageComponent(
                    Image().apply {
                        setPosition(location.x, location.y)
                        setSize(1f, 1f)
                        setScaling(Scaling.fill)
                    }
                )
                it += AnimationComponent(playMode = Animation.PlayMode.LOOP_PINGPONG).apply {
                    nextAnimation(
                        config.model,
                        AnimationType.IDLE
                    )
                }
            }
        }
        entity.remove()
    }

    override fun handle(event: Event): Boolean {
        when (event) {
            is MapChangeEvent -> {
                val entityLayer = event.map.layer("entities")
                entityLayer.objects.forEach { mapObject ->
                    val tiledMapObject = mapObject as TiledMapTileMapObject
                    val type = tiledMapObject.tile.property<String>("type")
                    world.entity {
                        it += EntitySpawnComponent(type, Vector2(mapObject.x * UNIT_SCALE, mapObject.y * UNIT_SCALE))
                    }
                }
                return true
            }
        }
        return false
    }

    private fun spawnConfiguration(type: String) = cachedConfigs.getOrPut(type) {
        when (type) {
            "player" -> TODO("Not implemented")
            "animationtest" -> EntitySpawnConfiguration(AnimationModel.ANIMATIONTEST)
        }
        EntitySpawnConfiguration(AnimationModel.ANIMATIONTEST)
    }
}
