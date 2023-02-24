package cafe.ferret.musi.event

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.scenes.scene2d.Event

/**
 * Fired when the map changes, containing the new map.
 */
data class MapChangeEvent(val map: TiledMap) : Event()
