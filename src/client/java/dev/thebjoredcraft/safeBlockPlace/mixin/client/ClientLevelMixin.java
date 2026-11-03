package dev.thebjoredcraft.safeBlockPlace.mixin.client;

import dev.thebjoredcraft.safeBlockPlace.client.cache.PlacementCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

  @Inject(method = "setServerVerifiedBlockState", at = @At("TAIL"))
  private void fakeBlockSound(BlockPos pos, BlockState blockState, int updateFlag, CallbackInfo ci) {
    if(blockState.isAir() || !PlacementCache.INSTANCE.consume(pos)) {
      return;
    }

    SoundType type = blockState.getSoundType();
    ((ClientLevel) (Object) this).playLocalSound(
        pos, type.getPlaceSound(), SoundSource.BLOCKS,
        (type.getVolume() + 1.0F) / 2.0F, type.getPitch() * 0.8F, false);
  }
}
