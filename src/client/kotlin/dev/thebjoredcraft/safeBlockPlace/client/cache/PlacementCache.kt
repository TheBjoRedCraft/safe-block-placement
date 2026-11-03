package dev.thebjoredcraft.safeBlockPlace.client.cache

import net.minecraft.core.BlockPos
import java.util.concurrent.ConcurrentHashMap

object PlacementCache {
    private val cache = ConcurrentHashMap<BlockPos, Long>()

    fun cache(pos: BlockPos) = cache.put(pos, System.currentTimeMillis())

    fun consume(pos: BlockPos): Boolean {
        val now = System.currentTimeMillis()
        cache.values.removeIf { now - it > 2000 }

        return cache.remove(pos) != null
    }
}