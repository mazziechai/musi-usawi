package cafe.ferret.musi.component

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import ktx.math.vec2

data class EntitySpawnConfiguration(val model: AnimationModel)

/**
 * Holds map object spawn data.
 *
 * @param type The type the map object, as a string.
 * @param location The x and y of the map object.
 */
data class EntitySpawnComponent(
    var type: String = "",
    var location: Vector2 = vec2()
) : Component<EntitySpawnComponent> {
    override fun type(): ComponentType<EntitySpawnComponent> = EntitySpawnComponent

    companion object : ComponentType<EntitySpawnComponent>()
}
