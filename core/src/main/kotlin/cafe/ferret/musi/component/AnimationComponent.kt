package cafe.ferret.musi.component

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

enum class AnimationModel {
    ANIMATIONTEST; // PLAYER, UNDEFINED;

    val atlasKey: String = this.toString().lowercase()
}

enum class AnimationType {
    IDLE;

    val atlasKey: String = this.toString().lowercase()
}

/**
 * Holds [Animation] state.
 *
 * @param nextAnimation The next animation's atlas key.
 * @param stateTime The time spent in the current animation state.
 * @param playMode The [Animation.PlayMode].
 */
data class AnimationComponent(
    var nextAnimation: String = NO_ANIMATION,
    var stateTime: Float = 0f,
    var playMode: Animation.PlayMode = Animation.PlayMode.LOOP
) : Component<AnimationComponent> {
    /**
     * The current animation.
     */
    var animation: Animation<TextureRegionDrawable> = EMPTY_ANIMATION

    fun nextAnimation(model: AnimationModel, type: AnimationType) {
        nextAnimation = "${model.atlasKey}/${type.atlasKey}"
    }

    override fun type(): ComponentType<AnimationComponent> = AnimationComponent

    companion object : ComponentType<AnimationComponent>() {
        private val EMPTY_ANIMATION = Animation<TextureRegionDrawable>(0f)
        const val NO_ANIMATION = ""
    }
}
