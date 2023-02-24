package cafe.ferret.musi.extension

import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import ktx.tiled.propertyOrNull

val TiledMapTileMapObject.tileType: String?
    get() = tile.propertyOrNull<String>("type")
