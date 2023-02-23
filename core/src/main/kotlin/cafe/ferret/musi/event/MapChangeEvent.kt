package cafe.ferret.musi.event

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.scenes.scene2d.Event

/**
 * An event that is fired when the map changes, containing the new map as a property.
 */
data class MapChangeEvent(val map: TiledMap) : Event()
