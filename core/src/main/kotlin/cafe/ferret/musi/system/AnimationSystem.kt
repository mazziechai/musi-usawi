package cafe.ferret.musi.system

import cafe.ferret.musi.component.AnimationComponent
import cafe.ferret.musi.component.AnimationComponent.Companion.NO_ANIMATION
import cafe.ferret.musi.component.ImageComponent
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import ktx.collections.map
import ktx.log.logger

class AnimationSystem(private val atlas: TextureAtlas = inject()) :
    IteratingSystem(family { all(AnimationComponent, ImageComponent) }) {
    private val cachedAnimations = mutableMapOf<String, Animation<TextureRegionDrawable>>()

    /**
     * Updates the state for each [cafe.ferret.musi.component.AnimationComponent].
     *
     * First, the [cafe.ferret.musi.component.ImageComponent] Drawable is updated with the next animation.
     * If there is no animation, the animation's state time is incremented by the delta time, and the key frame is set
     * to the state time.
     */
    override fun onTickEntity(entity: Entity) {
        val animationComponent = entity[AnimationComponent]

        if (animationComponent.nextAnimation == NO_ANIMATION) {
            animationComponent.stateTime += deltaTime
        } else {
            animationComponent.animation = animation(animationComponent.nextAnimation)
            animationComponent.stateTime = 0f
            animationComponent.nextAnimation = NO_ANIMATION
        }

        animationComponent.animation.playMode = animationComponent.playMode
        entity[ImageComponent].image.drawable = animationComponent.animation.getKeyFrame(animationComponent.stateTime)
    }

    private fun animation(atlasKey: String): Animation<TextureRegionDrawable> {
        return cachedAnimations.getOrPut(atlasKey) {
            LOG.debug { "Creating new animation $atlasKey" }
            Animation(DEFAULT_FRAME_DURATION, atlas.findRegions(atlasKey).map { TextureRegionDrawable(it) })
        }
    }

    companion object {
        private val LOG = logger<AnimationSystem>()
        const val DEFAULT_FRAME_DURATION = 1f / 8f
    }
}
