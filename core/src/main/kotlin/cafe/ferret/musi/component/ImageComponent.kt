package cafe.ferret.musi.component

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentHook
import com.github.quillraven.fleks.ComponentType

/**
 * Holds a [com.badlogic.gdx.scenes.scene2d.ui.Image].
 * When this component is added to an entity, it is added to the injected [com.badlogic.gdx.scenes.scene2d.Stage].
 *
 * @param image The [com.badlogic.gdx.scenes.scene2d.ui.Image] that should be stored by the component.
 */
data class ImageComponent(var image: Image) : Component<ImageComponent>, Comparable<ImageComponent> {

    override fun type(): ComponentType<ImageComponent> = ImageComponent

    /**
     * Compares the y of the image to the other ImageComponent.
     * If there is no y difference, then the x is compared instead.
     *
     * @param other The other ImageComponent.
     * @return The difference as an Int.
     */
    override fun compareTo(other: ImageComponent): Int {
        val yDiff = other.image.y.compareTo(image.y)

        return if (yDiff != 0) {
            yDiff
        } else {
            other.image.x.compareTo(image.x)
        }
    }

    companion object : ComponentType<ImageComponent>() {
        /**
         * When this component is added to an entity, add it to the [com.badlogic.gdx.scenes.scene2d.Stage] as an actor.
         */
        val onAdd: ComponentHook<ImageComponent> = { entity, component ->
            inject<Stage>().addActor(component.image)
        }

        /**
         * When this component is removed from an entity, remove its actor from the
         * [com.badlogic.gdx.scenes.scene2d.Stage].
         */
        val onRemove: ComponentHook<ImageComponent> = { entity, component ->
            inject<Stage>().root.removeActor(component.image)
        }
    }
}
