package cafe.ferret.musi.system

import cafe.ferret.musi.component.ImageComponent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import com.github.quillraven.fleks.collection.compareEntityBy

/**
 * Renders [com.badlogic.gdx.scenes.scene2d.ui.Image].
 */
class RenderSystem(private val stage: Stage = inject()) :
    IteratingSystem(
        family { all(ImageComponent) },
        comparator = compareEntityBy(ImageComponent)

    ) {

    /**
     * Gets the [com.badlogic.gdx.scenes.scene2d.Stage] and renders every [com.badlogic.gdx.scenes.scene2d.Actor].
     */
    override fun onTick() {
        super.onTick()

        with(stage) {
            viewport.apply()
            act(deltaTime)
            draw()
        }
    }

    override fun onTickEntity(entity: Entity) {
    }
}



