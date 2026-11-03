package dev.thebjoredcraft.safeBlockPlace.client.cache

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.SoundType
import java.util.concurrent.ConcurrentHashMap

object PlacementCache {
    private val cache = ConcurrentHashMap<BlockPos, Pair<SoundType, Long>>()

    fun cache(pos: BlockPos, soundType: SoundType) {
        cache[pos] = Pair(soundType, System.currentTimeMillis())
    }

    fun consume(pos: BlockPos): SoundType? {
        val now = System.currentTimeMillis()

        cache.values.removeIf { (_, timestamp) -> now - timestamp > 2000 }
        return cache.remove(pos)?.first
    }
}